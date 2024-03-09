package edu.java.bot.processors;

import edu.java.bot.commands.Command;
import edu.java.bot.wrapper.SendMessageWrapper;
import edu.java.bot.wrapper.UpdateWrapper;
import java.util.List;

public interface UserMessageProcessor {
    List<? extends Command> commands();

    SendMessageWrapper process(UpdateWrapper update);
}
