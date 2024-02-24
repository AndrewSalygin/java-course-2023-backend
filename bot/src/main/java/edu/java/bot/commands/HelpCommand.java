package edu.java.bot.commands;

import edu.java.bot.model.BotService;
import edu.java.bot.util.TextHandler;
import edu.java.bot.wrapper.SendMessageWrapper;
import edu.java.bot.wrapper.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends AbstractCommand {

    @Autowired
    public HelpCommand(TextHandler handler, BotService botService) {
        super(handler, botService);
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
    public SendMessageWrapper handle(UpdateWrapper update) {
        return new SendMessageWrapper(update.message().chat().id(), handler.handle("command.help.list_of_commands"));
    }
}
