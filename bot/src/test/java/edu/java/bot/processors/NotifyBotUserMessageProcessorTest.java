package edu.java.bot.processors;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.util.TextHandler;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static edu.java.bot.Utils.createMockUpdate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotifyBotUserMessageProcessorTest {

    private static UserMessageProcessor processor;

    @BeforeEach
    public void setUp() {
        int chatId = 1;

        TextHandler textHandler = Mockito.mock(TextHandler.class);
        Mockito.when(textHandler.handle(Mockito.anyString())).thenReturn("Unknown command");

        Command command = Mockito.mock(Command.class);
        Mockito.when(command.command()).thenReturn("/command");
        Mockito.when(command.handle(Mockito.any())).thenReturn(new SendMessage(chatId, "Command message"));

        processor = new NotifyBotUserMessageProcessor(List.of(command), textHandler);
    }

    @Test
    @DisplayName("Message null")
    public void processMessageNullTest() {
        assertNull(processor.process(Mockito.mock(Update.class)));
    }

    @Test
    @DisplayName("Message text null")
    public void processMessageTextNullTest() {
        assertNull(processor.process(createMockUpdate()));
    }

    @Test
    @DisplayName("commandsTest")
    public void commandsTest() {
        assertTrue(processor.commands().stream().allMatch(command -> command.command().equals("/command")));
    }

    @Test
    @DisplayName("Common command with right parameter")
    public void commonCommandWithRightParameterTest() {
        assertThat(processor.process(createMockUpdate("/command")).getParameters().get("text")).isEqualTo(
            "Command message");
    }

    @Test
    @DisplayName("Null update")
    public void updateNullTest() {
        assertNull(processor.process(new Update()));
    }

    @Test
    @DisplayName("Update with null message")
    public void updateWithNullMessageTest() {
        assertEquals("Unknown command", processor.process(createMockUpdate(null, 1L)).getParameters().get("text"));
    }

    @Test
    @DisplayName("Update with unknown command")
    public void updateWithUnknownMessageTest() {
        assertEquals("Unknown command", processor.process(createMockUpdate("/unknownCommand", 1L)).getParameters().get("text"));
    }
}
