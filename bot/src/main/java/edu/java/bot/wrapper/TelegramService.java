package edu.java.bot.wrapper;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.commands.Command;
import edu.java.bot.processors.UserMessageProcessor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class TelegramService {

    private final TelegramBot telegramBot;

    private final UserMessageProcessor processor;

    public void sendMessage(MessageResponse message, ParseMode parseMode) {
        SendMessage sendMessage = new SendMessage(message.chatId(), message.text());
        telegramBot.execute(sendMessage.parseMode(parseMode));
    }

    public void start() {
        BotCommand[] botCommands = processor.commands().stream().map(this::toApiCommand)
            .toArray(BotCommand[]::new);
        if (telegramBot == null) {
            throw new RuntimeException("Telegram bot is not working");
        }
        telegramBot.execute(new SetMyCommands(botCommands));
    }

    private BotCommand toApiCommand(Command command) {
        return new BotCommand(command.command(), command.description());
    }

    public MessageResponse processUpdate(Update update) {
        if (update.message() != null && update.message().chat() != null && update.message().text() != null) {
            Message receivedMessage = new Message(update.message().chat().id(), update.message().text());
            return processor.process(receivedMessage);
        }
        return null;
    }

    public void shutdown() {
        telegramBot.shutdown();
    }
}
