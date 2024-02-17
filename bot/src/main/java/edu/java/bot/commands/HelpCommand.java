package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.model.BotController;
import edu.java.bot.util.TextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends AbstractCommand {

    @Autowired
    public HelpCommand(TextHandler handler, BotController botController) {
        super(handler, botController);
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
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), handler.handle("command.help.list_of_commands"));
    }
}
