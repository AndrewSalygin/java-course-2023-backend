package edu.java.bot.commands;

import edu.java.bot.model.BotController;
import edu.java.bot.util.TextHandler;
import edu.java.bot.wrapper.SendMessageWrapper;
import edu.java.bot.wrapper.UpdateWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static edu.java.bot.Utils.createMockUpdateWrapper;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest {

    static HelpCommand helpCommand;

    static BotController botController;

    static TextHandler textHandler;

    @BeforeEach
    public void setUp() {
        textHandler = Mockito.mock(TextHandler.class);
        botController = Mockito.mock(BotController.class);
        helpCommand = new HelpCommand(textHandler, botController);
    }

    @Test
    public void handleReturnHelpMessageTest() {
        Mockito.when(textHandler.handle("command.help.list_of_commands")).thenReturn("Help");

        UpdateWrapper update = createMockUpdateWrapper("/help", 1L);
        SendMessageWrapper sendMessage = helpCommand.handle(update);
        assertEquals("Help", sendMessage.getParameters().get("text"));
    }
}
