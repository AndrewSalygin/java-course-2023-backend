package edu.java.bot.processors;

import edu.java.bot.commands.Command;
import edu.java.bot.util.TextHandler;
import java.util.List;
import edu.java.bot.wrapper.Message;
import edu.java.bot.wrapper.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
        Mockito.when(command.handle(Mockito.any())).thenReturn(new MessageResponse(chatId, "Command message"));

        processor = new NotifyBotUserMessageProcessor(List.of(command), textHandler);
    }

    @Test
    @DisplayName("commandsTest")
    public void commandsTest() {
        assertTrue(processor.commands().stream().allMatch(command -> command.command().equals("/command")));
    }

    @Test
    @DisplayName("Common command with right parameter")
    public void commonCommandWithRightParameterTest() {
        assertThat(processor.process(new Message(1L, "/command")).text()).isEqualTo(
            "Command message");
    }

    @Test
    @DisplayName("Update with unknown command")
    public void updateWithUnknownMessageTest() {
        assertEquals("Unknown command", processor.process(new Message(1L, "/unknownCommand")).text());
    }
}
