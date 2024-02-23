package edu.java.bot.wrapper;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SendMessageWrapper {

    private final SendMessage sendMessage;

    public SendMessageWrapper(Long chatId, String text) {
        sendMessage = new SendMessage(chatId, text);
    }

    public SendMessageWrapper parseMode(ParseModeEnum parseModeEnum) {
        ParseMode parseMode;
        parseMode = switch (parseModeEnum) {
            case ParseModeEnum.Markdown -> ParseMode.Markdown;
            case ParseModeEnum.MarkdownV2 -> ParseMode.MarkdownV2;
            case ParseModeEnum.HTML -> ParseMode.HTML;
        };
        return new SendMessageWrapper(sendMessage.parseMode(parseMode));
    }

    public Map<String, Object> getParameters() {
        return sendMessage.getParameters();
    }
}
