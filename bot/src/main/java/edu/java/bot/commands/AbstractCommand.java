package edu.java.bot.commands;

import edu.java.bot.model.BotService;
import edu.java.bot.util.TextHandler;

public abstract class AbstractCommand implements Command {
    protected TextHandler handler;

    protected BotService botService;

    public AbstractCommand(TextHandler handler, BotService botService) {
        this.handler = handler;
        this.botService = botService;
    }

    boolean isEmptyArgument(String[] elements) {
        return elements.length == 1;
    }

}
