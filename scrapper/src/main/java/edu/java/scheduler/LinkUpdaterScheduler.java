package edu.java.scheduler;

import edu.java.client.bot.BotClient;
import edu.java.client.bot.dto.request.LinkUpdate;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.Chat;
import edu.java.service.LinkService;
import edu.java.supplier.InfoSuppliers;
import edu.java.supplier.api.InfoSupplier;
import edu.java.supplier.api.LinkInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {

    private final LinkService linkService;

    private final ApplicationConfig applicationConfig;

    private final InfoSuppliers infoSuppliers;

    private final BotClient botClient;

    @Scheduled(fixedDelayString = "#{@'app-edu.java.configuration.ApplicationConfig'.scheduler.interval}")
    public void update() {
        log.info("Updating links start");
        linkService.getOldLinks(
                applicationConfig.scheduler().forceCheckDelay(),
                applicationConfig.scheduler().maxLinksPerCheck()
            )
            .forEach(link -> {
                log.info("Updating link {}", link);
                String host = link.url().getHost();
                String domain = host.replaceAll("^(www\\.)?|\\.com$", "");
                InfoSupplier supplier = infoSuppliers.getSupplierByTypeHost(domain);
                LinkInfo linkInfo = supplier.fetchInfo(link.url());

                if (linkInfo.lastUpdate().isAfter(link.lastUpdate())) {
                    linkService.update(link.linkId(), linkInfo.lastUpdate());
                    botClient.handleUpdate(new LinkUpdate(
                        link.linkId(),
                        link.url(),
                        linkInfo.title(),
                        linkService.getLinkSubscribers(link.url()).chats().stream()
                            .map(Chat::chatId)
                            .toList()
                    ));
                }
            });
        log.info("Updating links ends");
    }

}

