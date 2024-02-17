package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.model.BotController;
import edu.java.bot.model.Link;
import edu.java.bot.util.TextHandler;
import edu.java.bot.util.URLChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand extends AbstractCommand {

    @Autowired
    public TrackCommand(TextHandler handler, BotController botController) {
        super(handler, botController);
    }

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return handler.handle("command.track.description");
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        if (!botController.isUserRegistered(chatId)) {
            botController.registerUser(chatId);
        }
        String argument = update.message().text().replace("/track ", "");
        argument = argument.replace(command(), "");
        Link link;
        SendMessage sendMessage;
        if (argument.isEmpty()) {
            sendMessage = new SendMessage(
                chatId,
                String.format(handler.handle("message.empty_argument"), argument)
            );
        } else if (URLChecker.isURL(argument)) {
            link = new Link(argument);
            if (botController.trackUserLink(chatId, link)) {
                sendMessage = new SendMessage(
                    chatId,
                    String.format(handler.handle("command.track.successful_track"), link.url())
                );
            } else {
                sendMessage = new SendMessage(
                    chatId,
                    String.format(handler.handle("command.track.already_tracked"), link.url())
                );
            }
        } else {
            sendMessage = new SendMessage(
                chatId,
                String.format(handler.handle("message.invalid_argument"), argument)
            );
        }
        return sendMessage;
    }
}
