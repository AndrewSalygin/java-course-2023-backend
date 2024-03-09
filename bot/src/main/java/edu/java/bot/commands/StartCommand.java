package edu.java.bot.commands;

import edu.java.bot.model.BotService;
import edu.java.bot.util.TextHandler;
import edu.java.bot.wrapper.SendMessageWrapper;
import edu.java.bot.wrapper.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCommand extends AbstractCommand {

    @Autowired
    public StartCommand(TextHandler handler, BotService botController) {
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
    public SendMessageWrapper handle(UpdateWrapper update) {
        Long chatId = update.message().chat().id();

        if (!botService.isUserRegistered(chatId)) {
            botService.registerUser(chatId);
            return new SendMessageWrapper(chatId, handler.handle("command.start.first_hello_message"));
        } else {
            return new SendMessageWrapper(chatId, handler.handle("command.start.hello_message"));
        }
    }
}
