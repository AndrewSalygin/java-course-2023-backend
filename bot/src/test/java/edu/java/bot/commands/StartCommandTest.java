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

public class StartCommandTest {

    static StartCommand startCommand;

    static BotController botController;

    static TextHandler textHandler;

    @BeforeEach
    public void setUp() {
        textHandler = Mockito.mock(TextHandler.class);
        botController = Mockito.mock(BotController.class);
        startCommand = new StartCommand(textHandler, botController);
    }

    @Test
    public void handleRegisterUserTest() {
        Mockito.when(textHandler.handle("command.start.first_hello_message")).thenReturn("Hello");

        UpdateWrapper update = createMockUpdateWrapper("/start", 1L);
        SendMessageWrapper sendMessage = startCommand.handle(update);
        Mockito.verify(botController, Mockito.times(1)).registerUser(1L);
        assertEquals("Hello", sendMessage.getParameters().get("text"));
    }
}
