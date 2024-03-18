package edu.java.bot.service;

import com.pengrad.telegrambot.model.request.ParseMode;
import edu.java.bot.dto.request.LinkUpdate;
import edu.java.bot.util.TextHandler;
import edu.java.bot.wrapper.MessageResponse;
import edu.java.bot.wrapper.TelegramService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class DefaultLinkUpdatesSenderService implements LinkUpdatesSenderService {

    private final TelegramService telegramService;

    private final TextHandler handler;

    @Override
    public void sendLinkUpdate(LinkUpdate linkUpdate) {
        linkUpdate.tgChatIds().forEach(chatId -> telegramService.sendMessage(
            new MessageResponse(
                chatId,
                handler.handle("link.update", Map.of("link", String.valueOf(linkUpdate.url()), "description",
                    handler.handle(
                        linkUpdate.description(),
                        linkUpdate.metaInfo(),
                        "Default update"
                    )))
            ), ParseMode.Markdown));
    }
}
