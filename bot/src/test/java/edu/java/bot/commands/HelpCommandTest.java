package edu.java.bot.commands;

import edu.java.bot.service.BotService;
import edu.java.bot.util.TextHandler;
import edu.java.bot.wrapper.Message;
import edu.java.bot.wrapper.MessageResponse;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest {

    static HelpCommand helpCommand;

    static BotService botService;

    static TextHandler textHandler;

    @BeforeEach
    public void setUp() {
        textHandler = Mockito.mock(TextHandler.class);
        botService = Mockito.mock(BotService.class);
        helpCommand = new HelpCommand(textHandler, botService, Collections.emptyList());
    }

    @Test
    public void handleReturnHelpMessageTest() {
        Mockito.when(textHandler.handle("command.help.util.title")).thenReturn("Help");
        Message message = new Message(1L, "/help");
        MessageResponse sendMessage = helpCommand.handle(message);
        assertEquals("Help", sendMessage.text());
    }
}
