package edu.java.bot.processors;

import edu.java.bot.commands.Command;
import edu.java.bot.util.TextHandler;
import edu.java.bot.wrapper.SendMessageWrapper;
import edu.java.bot.wrapper.UpdateWrapper;
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
    public SendMessageWrapper process(UpdateWrapper update) {
        if (update.message() != null && update.message().text() != null && update.message().chat() != null) {
            String inputCommand =
                GetCommandByUpdate.getInputCommandFromInputMessageText(update.message().text());

            Command command = GetCommandByUpdate.getCommandFromInputCommand(inputCommand, commands());

            if (command == null) {
                return new SendMessageWrapper(
                    update.message().chat().id(),
                    String.format(handler.handle("message.unknown_command"), update.message().text())
                );
            }
            return command.handle(update);
        }
        return null;
    }
}
