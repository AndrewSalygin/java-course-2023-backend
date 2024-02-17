package edu.java.bot.model;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.commands.Command;
import edu.java.bot.processors.UserMessageProcessor;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotifyBot implements Bot {

    private final static Logger LOGGER = LogManager.getLogger();

    private final TelegramBot bot;

    private final UserMessageProcessor processor;

    @Override
    @PostConstruct
    public void start() {
        execute(new SetMyCommands((processor.commands().stream().map(Command::toApiCommand).toList()
            .toArray(new BotCommand[0]))));
        bot.setUpdatesListener(this);
        LOGGER.info("Telegram bot launched");
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        if (bot == null) {
            throw new RuntimeException("Telegram bot is not working");
        }
        if (request != null) {
            bot.execute(request);
        }
    }

    @Override
    public int process(List<Update> list) {
        for (Update update : list) {
            execute(processor.process(update).parseMode(ParseMode.Markdown));
            LOGGER.info(String.format(
                "The incoming message from @%s: %s has been processed",
                update.message().chat().username(),
                update.message().text()
            ));
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void close() {
        bot.shutdown();
        LOGGER.info("Telegram bot has been stopped");
    }
}
