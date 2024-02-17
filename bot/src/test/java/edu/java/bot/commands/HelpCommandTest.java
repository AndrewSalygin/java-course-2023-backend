package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.model.BotController;
import edu.java.bot.util.TextHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static edu.java.bot.Utils.createMockUpdate;
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

        Update update = createMockUpdate("/help", 1L);
        SendMessage sendMessage = helpCommand.handle(update);
        assertEquals("Help", sendMessage.getParameters().get("text"));
    }
}
