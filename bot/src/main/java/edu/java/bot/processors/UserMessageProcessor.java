package edu.java.bot.processors;

import edu.java.bot.commands.Command;
import edu.java.bot.wrapper.Message;
import edu.java.bot.wrapper.MessageResponse;
import java.util.List;

public interface UserMessageProcessor {
    List<? extends Command> commands();

    MessageResponse process(Message message);
}
