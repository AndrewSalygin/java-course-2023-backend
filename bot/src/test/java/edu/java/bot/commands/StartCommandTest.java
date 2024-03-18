package edu.java.bot.commands;

import edu.java.bot.service.BotService;
import edu.java.bot.util.TextHandler;
import edu.java.bot.wrapper.Message;
import edu.java.bot.wrapper.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartCommandTest {

    static StartCommand startCommand;

    static BotService botService;

    static TextHandler textHandler;

    @BeforeEach
    public void setUp() {
        textHandler = Mockito.mock(TextHandler.class);
        botService = Mockito.mock(BotService.class);
        startCommand = new StartCommand(textHandler, botService);
    }

    @Test
    public void handleRegisterUserTest() {
        Mockito.when(textHandler.handle("command.start.messages.success.first_hello_message")).thenReturn("Hello");
        Message message = new Message(1L, "/list");
        MessageResponse sendMessage = startCommand.handle(message);
        Mockito.verify(botService, Mockito.times(1)).registerUserIfNew(1L);
        assertEquals("Hello", sendMessage.text());
    }
}
