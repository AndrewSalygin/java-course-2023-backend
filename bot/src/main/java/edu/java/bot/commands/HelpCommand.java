package edu.java.bot.commands;

import edu.java.bot.service.BotService;
import edu.java.bot.util.ReplacerValiables;
import edu.java.bot.util.TextHandler;
import edu.java.bot.wrapper.Message;
import edu.java.bot.wrapper.MessageResponse;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends AbstractCommand {

    private final List<Command> commandList;

    @Autowired
    public HelpCommand(TextHandler handler, BotService botService, List<Command> commandList) {
        super(handler, botService);
        this.commandList = commandList;
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return handler.handle("command.help.description");
    }

    @Override
    public MessageResponse handle(Message message) {
        StringBuilder responseMessage = new StringBuilder();
        responseMessage.append(handler.handle("command.help.util.title"));
        String format = handler.handle("command.help.util.help_command_format");
        commandList.forEach(command -> responseMessage.append(
            ReplacerValiables.replaceVariables(format,
                Map.of(
                    "command", command.command(),
                    "description", command.description()
                )
            )
        ));
        return new MessageResponse(message.chatId(), String.valueOf(responseMessage));
    }
}
