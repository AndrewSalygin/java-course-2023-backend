package edu.java.bot.processors;

import edu.java.bot.commands.Command;
import java.util.List;

public class GetCommandByUpdate {

    private GetCommandByUpdate() {}

    public static String getInputCommandFromInputMessageText(String inputMessageText) {
        int indexOfSpace = inputMessageText.indexOf(' ');
        String inputCommand;
        if (indexOfSpace != -1) {
            inputCommand = inputMessageText.substring(0, indexOfSpace);
        } else {
            inputCommand = inputMessageText;
        }

        if (inputCommand.charAt(0) == '/') {
            return inputCommand;
        }
        return null;
    }

    public static Command getCommandFromInputCommand(String inputCommand, List<? extends Command> commandList) {
        for (Command command : commandList) {
            if (inputCommand.equals(command.command())) {
                return command;
            }
        }
        return null;
    }
}
