package edu.java.bot.commands;

import edu.java.bot.model.BotService;
import edu.java.bot.model.Link;
import edu.java.bot.util.TextHandler;
import edu.java.bot.wrapper.Message;
import edu.java.bot.wrapper.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommand extends AbstractCommand {

    @Autowired
    public ListCommand(TextHandler handler, BotService botService) {
        super(handler, botService);
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
    public MessageResponse handle(Message message) {
        Long chatId = message.chatId();
        botService.registerUserIfNew(chatId);
        if (botService.userLinks(chatId).isEmpty()) {
            return new MessageResponse(chatId, handler.handle("command.list.empty"));
        }
        StringBuilder linksString = new StringBuilder();
        for (Link link : botService.userLinks(chatId)) {
            linksString.append(link.url()).append("\n");
        }
        return new MessageResponse(chatId, String.format(handler.handle("command.list.show"), linksString));
    }
}
