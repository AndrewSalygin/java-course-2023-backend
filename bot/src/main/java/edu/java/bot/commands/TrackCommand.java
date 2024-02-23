package edu.java.bot.commands;

import edu.java.bot.model.BotController;
import edu.java.bot.model.Link;
import edu.java.bot.util.TextHandler;
import edu.java.bot.util.URLChecker;
import edu.java.bot.wrapper.SendMessageWrapper;
import edu.java.bot.wrapper.UpdateWrapper;
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
    public SendMessageWrapper handle(UpdateWrapper update) {
        Long chatId = update.message().chat().id();
        botController.registerUser(chatId);
        String[] elements = update.message().text().split(" ");

        if (isEmptyArgument(elements)) {
            return new SendMessageWrapper(
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

        return new SendMessageWrapper(chatId, answerString.toString());
    }

    private String getAnswerForLink(Link link, Long chatId) {
        if (!URLChecker.isURL(link.url())) {
            if (!link.url().isEmpty()) {
                return String.format(handler.handle("message.invalid_argument"), link.url());
            }
        } else if (botController.trackUserLink(chatId, link)) {
            return String.format(handler.handle("command.track.successful_track"), link.url());
        }
        return String.format(handler.handle("command.track.already_tracked"), link.url());
    }
}
