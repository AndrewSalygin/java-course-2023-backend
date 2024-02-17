package edu.java.bot.processors;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.util.TextHandler;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotifyBotUserMessageProcessor implements UserMessageProcessor {
    private final List<Command> commands;
    private final TextHandler handler;

    @Autowired
    public NotifyBotUserMessageProcessor(List<Command> commands, TextHandler handler) {
        this.commands = commands;
        this.handler = handler;
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        if (update.message() != null && update.message().text() != null) {
            int indexOfSpace = update.message().text().indexOf(' ');
            String inputCommand;
            if (indexOfSpace != -1) {
                inputCommand = update.message().text().substring(0, indexOfSpace + 1).trim();
            } else {
                inputCommand = update.message().text();
            }
            if (inputCommand.charAt(0) == '/') {
                for (Command command : commands()) {
                    if (inputCommand.equals(command.command())) {
                        return command.handle(update);
                    }
                }
            }
        }
        if (update.message() != null) {
            if (update.message().chat() == null) {
                return null;
            } else {
                return new SendMessage(
                    update.message().chat().id(),
                    String.format(handler.handle("message.unknown_command"), update.message().text())
                );
            }
        }
        return null;
    }
}
