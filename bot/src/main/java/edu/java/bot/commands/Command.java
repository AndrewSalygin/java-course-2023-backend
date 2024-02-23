package edu.java.bot.commands;

import edu.java.bot.wrapper.BotCommandWrapper;
import edu.java.bot.wrapper.SendMessageWrapper;
import edu.java.bot.wrapper.UpdateWrapper;

public interface Command {

    String command();

    String description();

    SendMessageWrapper handle(UpdateWrapper update);

    default BotCommandWrapper toApiCommand() {
        return new BotCommandWrapper(command(), description());
    }
}
