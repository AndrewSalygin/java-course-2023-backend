package edu.java.bot.commands;

import edu.java.bot.model.BotService;
import edu.java.bot.model.Link;
import edu.java.bot.util.TextHandler;
import edu.java.bot.util.URLChecker;
import edu.java.bot.wrapper.Message;
import edu.java.bot.wrapper.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand extends AbstractCommand {

    @Autowired
    public TrackCommand(TextHandler handler, BotService botService) {
        super(handler, botService);
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
    public MessageResponse handle(Message message) {
        Long chatId = message.chatId();
        botService.registerUserIfNew(chatId);
        String[] elements = message.text().split(" ");

        if (isEmptyArgument(elements)) {
            return new MessageResponse(
                chatId,
                handler.handle("message.empty_argument")
            );
        }

        Link link;
        StringBuilder answerString = new StringBuilder();
        for (int i = 1; i < elements.length; i++) {
            if (!elements[i].isEmpty()) {
                link = new Link(elements[i]);
                answerString.append(getAnswerForLink(link, chatId)).append('\n');
            }
        }
        answerString.deleteCharAt(answerString.length() - 1);

        return new MessageResponse(chatId, answerString.toString());
    }

    private String getAnswerForLink(Link link, Long chatId) {
        if (!URLChecker.isURL(link.url())) {
            if (!link.url().isEmpty()) {
                return String.format(handler.handle("message.invalid_argument"), link.url());
            }
        } else if (botService.trackUserLink(chatId, link)) {
            return String.format(handler.handle("command.track.successful_track"), link.url());
        }
        return String.format(handler.handle("command.track.already_tracked"), link.url());
    }
}
