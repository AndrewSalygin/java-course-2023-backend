package edu.java.bot.commands;

import edu.java.bot.model.BotController;
import edu.java.bot.model.Link;
import edu.java.bot.util.TextHandler;
import java.util.List;
import edu.java.bot.wrapper.SendMessageWrapper;
import edu.java.bot.wrapper.UpdateWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static edu.java.bot.Utils.createMockUpdateWrapper;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListCommandTest {

    static ListCommand listCommand;

    static BotController botController;

    static TextHandler textHandler;

    @BeforeEach
    public void setUp() {
        textHandler = Mockito.mock(TextHandler.class);
        botController = Mockito.mock(BotController.class);
        listCommand = new ListCommand(textHandler, botController);
    }

    @Test
    @DisplayName("No tracked links")
    public void handleReturnEmptyListMessageTest() {
        Mockito.when(textHandler.handle("command.list.empty")).thenReturn("You haven't any tracked links");

        UpdateWrapper update = createMockUpdateWrapper("/list", 1L);
        SendMessageWrapper sendMessage = listCommand.handle(update);
        assertEquals("You haven't any tracked links", sendMessage.getParameters().get("text"));
    }

    @Test
    @DisplayName("Displaying tracked links")
    public void handleReturnNotEmptyListMessageTest() {
        Mockito.when(textHandler.handle("command.list.show")).thenReturn("Your tracked links:\n %s");
        Mockito.when(botController.userLinks(1L))
            .thenReturn(List.of(new Link("http://example.com"), new Link("https://example.com")));

        UpdateWrapper update = createMockUpdateWrapper("/list", 1L);
        SendMessageWrapper sendMessage = listCommand.handle(update);

        assertEquals(
            "Your tracked links:\n http://example.com\nhttps://example.com\n",
            sendMessage.getParameters().get("text")
        );
        Mockito.verify(botController, Mockito.times(2)).userLinks(1L);
    }
}
