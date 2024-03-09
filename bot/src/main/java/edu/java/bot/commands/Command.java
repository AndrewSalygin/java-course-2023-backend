package edu.java.bot.commands;

import edu.java.bot.wrapper.Message;
import edu.java.bot.wrapper.MessageResponse;

public interface Command {

    String command();

    String description();

    MessageResponse handle(Message update);
}
