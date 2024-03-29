package edu.java.bot.commands;

import edu.java.bot.client.scrapper.dto.response.LinkResponse;
import edu.java.bot.service.BotService;
import edu.java.bot.util.TextHandler;
import edu.java.bot.util.URLChecker;
import edu.java.bot.wrapper.Message;
import edu.java.bot.wrapper.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand extends AbstractCommand {

    @Autowired
    public UntrackCommand(TextHandler handler, BotService botService) {
        super(handler, botService);
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

        LinkResponse link;
        StringBuilder answerString = new StringBuilder();
        for (int i = 1; i < elements.length; i++) {
            if (!elements[i].isEmpty()) {
                answerString.append(getAnswerForLink(elements[i], chatId)).append('\n');
            }
        }
        answerString.deleteCharAt(answerString.length() - 1);

        return new MessageResponse(chatId, answerString.toString());
    }

    private String getAnswerForLink(String link, Long chatId) {
        if (!URLChecker.isURL(link)) {
            if (!link.isEmpty()) {
                return String.format(handler.handle("message.invalid_argument"), link);
            }
        } else if (botService.isUserLinkTracked(chatId, link)) {
            botService.unTrackUserLink(chatId, link);
            return String.format(handler.handle("command.untrack.successful_untrack"), link);
        }
        return String.format(handler.handle("command.untrack.not_tracked"), link);
    }
}
