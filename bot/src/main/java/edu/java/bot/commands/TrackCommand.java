package edu.java.bot.commands;

import edu.java.bot.client.scrapper.dto.response.LinkResponse;
import edu.java.bot.dto.OptionalAnswer;
import edu.java.bot.service.BotService;
import edu.java.bot.util.TextHandler;
import edu.java.bot.util.URLChecker;
import edu.java.bot.wrapper.Message;
import edu.java.bot.wrapper.MessageResponse;
import java.util.Map;
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
        }
        OptionalAnswer<LinkResponse> answer = botService.trackUserLink(chatId, link);
        if (answer != null) {
            if (!answer.isError()) {
                return handler.handle("command.track.messages.successful_track", Map.of("link", link));
            } else {
                return answer.apiErrorResponse().description();
            }
        } else {
            return handler.handle("message.unknown_command");
        }

    }
}
