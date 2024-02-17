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
public class UntrackCommand extends AbstractCommand {

    @Autowired
    public UntrackCommand(TextHandler handler, BotController botController) {
        super(handler, botController);
    }

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return handler.handle("command.untrack.description");
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        if (!botController.isUserRegistered(chatId)) {
            botController.registerUser(chatId);
        }
        String argument = update.message().text().replace("/untrack ", "");
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
            if (botController.unTrackUserLink(chatId, link)) {
                sendMessage = new SendMessage(
                    chatId,
                    String.format(handler.handle("command.untrack.successful_untrack"), link.url())
                );
            } else {
                sendMessage = new SendMessage(
                    chatId,
                    String.format(handler.handle("command.untrack.not_tracked"), link.url())
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
