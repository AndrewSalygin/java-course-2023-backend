package edu.java.bot.wrapper;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import java.util.Arrays;
import lombok.Getter;

@Getter
public class SetMyCommandsWrapper {

    private final SetMyCommands setMyCommands;

    public SetMyCommandsWrapper(BotCommandWrapper[] botCommandsWrapper) {
        setMyCommands = new SetMyCommands(Arrays.stream(botCommandsWrapper).map(BotCommandWrapper::getBotCommand)
            .toArray(BotCommand[]::new));
    }

}
