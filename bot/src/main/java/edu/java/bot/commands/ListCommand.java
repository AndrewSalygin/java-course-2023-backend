package edu.java.bot.commands;

import edu.java.bot.client.scrapper.dto.response.LinkResponse;
import edu.java.bot.service.BotService;
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
        if (botService.userLinks(chatId).answer().links().isEmpty()) {
            return new MessageResponse(chatId, handler.handle("command.list.messages.empty_list_of_links"));
        }
        StringBuilder linksString = new StringBuilder();
        linksString.append(handler.handle("command.list.messages.show_tracked_links"));
        int id = 1;
        for (LinkResponse link : botService.userLinks(chatId).answer().links()) {
            linksString.append(id).append(". ").append(link.url()).append("\n");
            id++;
        }
        return new MessageResponse(chatId, String.valueOf(linksString));
    }
}
