package edu.java.bot.wrapper;

import com.pengrad.telegrambot.model.BotCommand;
import lombok.Getter;

@Getter
public class BotCommandWrapper {

    private final BotCommand botCommand;

    public BotCommandWrapper(String command, String description) {
        botCommand = new BotCommand(command, description);
    }
}
