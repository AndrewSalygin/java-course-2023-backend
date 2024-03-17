package edu.java.bot.service;

import com.pengrad.telegrambot.model.request.ParseMode;
import edu.java.bot.dto.request.LinkUpdate;
import edu.java.bot.util.TextHandler;
import edu.java.bot.wrapper.MessageResponse;
import edu.java.bot.wrapper.TelegramService;
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
                String.format(handler.handle("link.update"), linkUpdate.url())
            ), ParseMode.Markdown));
    }
}
