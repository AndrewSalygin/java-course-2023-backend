package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.model.BotController;
import edu.java.bot.model.Link;
import edu.java.bot.util.TextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommand extends AbstractCommand {

    @Autowired
    public ListCommand(TextHandler handler, BotController botController) {
        super(handler, botController);
    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return handler.handle("command.list.description");
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        if (!botController.isUserRegistered(chatId)) {
            botController.registerUser(chatId);
        }
        if (botController.userLinks(chatId).isEmpty()) {
            return new SendMessage(chatId, handler.handle("command.list.empty"));
        }
        StringBuilder linksString = new StringBuilder();
        for (Link link : botController.userLinks(chatId)) {
            linksString.append(link.url()).append("\n");
        }
        return new SendMessage(chatId, String.format(handler.handle("command.list.show"), linksString));
    }
}
