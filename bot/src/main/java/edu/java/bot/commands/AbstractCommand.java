package edu.java.bot.commands;

import edu.java.bot.model.BotController;
import edu.java.bot.util.TextHandler;

public abstract class AbstractCommand implements Command {
    protected TextHandler handler;

    protected BotController botController;

    public AbstractCommand(TextHandler handler, BotController botController) {
        this.handler = handler;
        this.botController = botController;
    }

    boolean isEmptyArgument(String[] elements) {
        return elements.length == 1;
    }

}
