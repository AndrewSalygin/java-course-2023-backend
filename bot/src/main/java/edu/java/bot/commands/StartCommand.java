package edu.java.bot.commands;

import edu.java.bot.dto.OptionalAnswer;
import edu.java.bot.service.BotService;
import edu.java.bot.util.TextHandler;
import edu.java.bot.wrapper.Message;
import edu.java.bot.wrapper.MessageResponse;
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
    public MessageResponse handle(Message message) {
        Long chatId = message.chatId();
        OptionalAnswer<Void> answer = botService.registerUserIfNew(chatId);
        if (answer != null && answer.isError()) {
            return new MessageResponse(chatId, handler.handle("command.start.messages.success.hello_message"));
        } else {
            return new MessageResponse(chatId, handler.handle("command.start.messages.success.first_hello_message"));
        }
    }
}
