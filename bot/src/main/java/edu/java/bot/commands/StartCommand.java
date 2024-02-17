package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.model.BotController;
import edu.java.bot.util.TextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCommand extends AbstractCommand {

    @Autowired
    public StartCommand(TextHandler handler, BotController botController) {
        super(handler, botController);
    }

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return handler.handle("command.start.description");
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();

        if (!botController.isUserRegistered(chatId)) {
            botController.registerUser(chatId);
            return new SendMessage(chatId, handler.handle("command.start.first_hello_message"));
        } else {
            return new SendMessage(chatId, handler.handle("command.start.hello_message"));
        }
    }
}
