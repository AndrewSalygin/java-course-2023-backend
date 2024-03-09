package edu.java.bot.processors;

import edu.java.bot.commands.Command;
import edu.java.bot.util.TextHandler;
import edu.java.bot.wrapper.Message;
import edu.java.bot.wrapper.MessageResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class NotifyBotUserMessageProcessor implements UserMessageProcessor {
    private final List<Command> commands;
    private final TextHandler handler;

    public NotifyBotUserMessageProcessor(List<Command> commands, TextHandler handler) {
        this.commands = commands;
        this.handler = handler;
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public MessageResponse process(Message message) {
        String inputCommand = GetCommandByUpdate.getInputCommandFromInputMessageText(message.text());
        Command command = GetCommandByUpdate.getCommandFromInputCommand(inputCommand, commands());

        if (command == null) {
            return new MessageResponse(
                message.chatId(),
                String.format(handler.handle("message.unknown_command"), message.text())
            );
        }
        return command.handle(message);
    }
}
