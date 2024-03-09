package edu.java.bot.processors;

import edu.java.bot.commands.Command;
import edu.java.bot.util.TextHandler;
import java.util.List;
import edu.java.bot.wrapper.SendMessageWrapper;
import edu.java.bot.wrapper.UpdateWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static edu.java.bot.Utils.createMockUpdateWrapper;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotifyBotUserMessageProcessorTest {

    private static UserMessageProcessor processor;

    @BeforeEach
    public void setUp() {
        Long chatId = 1L;

        TextHandler textHandler = Mockito.mock(TextHandler.class);
        Mockito.when(textHandler.handle(Mockito.anyString())).thenReturn("Unknown command");

        Command command = Mockito.mock(Command.class);
        Mockito.when(command.command()).thenReturn("/command");
        Mockito.when(command.handle(Mockito.any())).thenReturn(new SendMessageWrapper(chatId, "Command message"));

        processor = new NotifyBotUserMessageProcessor(List.of(command), textHandler);
    }

    @Test
    @DisplayName("Message null")
    public void processMessageNullTest() {
        UpdateWrapper updateWrapper = Mockito.mock(UpdateWrapper.class);
        assertNull(processor.process(updateWrapper));
    }

    @Test
    @DisplayName("Message text null")
    public void processMessageTextNullTest() {
        assertNull(processor.process(createMockUpdateWrapper()));
    }

    @Test
    @DisplayName("commandsTest")
    public void commandsTest() {
        assertTrue(processor.commands().stream().allMatch(command -> command.command().equals("/command")));
    }

    @Test
    @DisplayName("Common command with right parameter")
    public void commonCommandWithRightParameterTest() {
        assertThat(processor.process(createMockUpdateWrapper("/command", 1L)).getParameters().get("text")).isEqualTo(
            "Command message");
    }

    @Test
    @DisplayName("Null update")
    public void updateNullTest() {
        assertNull(processor.process(Mockito.mock(UpdateWrapper.class)));
    }

    @Test
    @DisplayName("Update with null message")
    public void updateWithNullMessageTest() {
        assertNull(processor.process(createMockUpdateWrapper(null, 1L)));
    }

    @Test
    @DisplayName("Update with unknown command")
    public void updateWithUnknownMessageTest() {
        assertEquals("Unknown command", processor.process(createMockUpdateWrapper("/unknownCommand", 1L)).getParameters().get("text"));
    }
}
