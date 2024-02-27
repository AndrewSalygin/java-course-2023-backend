package edu.java.bot.model;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import edu.java.bot.wrapper.MessageResponse;
import edu.java.bot.wrapper.TelegramService;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class NotifyBot implements Bot {

    private final TelegramService service;

    @Override
    @PostConstruct
    public void start() {
        service.start();
        service.getTelegramBot().setUpdatesListener(this);
        log.info("Telegram bot launched");
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            MessageResponse responseMessage = service.processUpdate(update);
            if (responseMessage != null) {
                service.sendMessage(responseMessage, ParseMode.Markdown);
                log.info(String.format(
                    "The incoming message from @%s: %s has been processed",
                    update.message().chat().username(),
                    update.message().text()
                ));
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void close() {
        service.shutdown();
        log.info("Telegram bot has been stopped");
    }
}
