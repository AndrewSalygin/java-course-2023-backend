package edu.java.bot.model;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.Command;
import edu.java.bot.processors.UserMessageProcessor;
import edu.java.bot.wrapper.BotCommandWrapper;
import edu.java.bot.wrapper.ParseModeEnum;
import edu.java.bot.wrapper.SendMessageWrapper;
import edu.java.bot.wrapper.SetMyCommandsWrapper;
import edu.java.bot.wrapper.TelegramBotWrapper;
import edu.java.bot.wrapper.UpdateWrapper;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
@Log4j2
public class NotifyBot implements Bot {

    private final TelegramBotWrapper bot;

    private final UserMessageProcessor processor;

    @Override
    @PostConstruct
    public void start() {
        BotCommandWrapper[] botCommands = processor.commands().stream().map(Command::toApiCommand)
            .toArray(BotCommandWrapper[]::new);
        if (bot == null) {
            throw new RuntimeException("Telegram bot is not working");
        }
        bot.execute(new SetMyCommandsWrapper(botCommands).getSetMyCommands());
        bot.setUpdatesListener(this);
        log.info("Telegram bot launched");
    }

    @Override
    public int process(List<Update> list) {
        List<UpdateWrapper> updateWrappers = list.stream().map(UpdateWrapper::new).toList();
        for (UpdateWrapper update : updateWrappers) {
            SendMessageWrapper sendMessageWrapper = processor.process(update);
            if (sendMessageWrapper != null) {
                bot.execute(sendMessageWrapper.parseMode(ParseModeEnum.Markdown).getSendMessage());
                log.info(String.format(
                    "The incoming message from @%s: %s has been processed",
                    update.message().chat().username(),
                    update.message().text()
                ));
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void close() {
        bot.shutdown();
        log.info("Telegram bot has been stopped");
    }
}
