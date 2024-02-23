package edu.java.bot.commands;

import edu.java.bot.model.BotController;
import edu.java.bot.model.Link;
import edu.java.bot.util.TextHandler;
import edu.java.bot.wrapper.SendMessageWrapper;
import edu.java.bot.wrapper.UpdateWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static edu.java.bot.Utils.createMockUpdateWrapper;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UntrackCommandTest {

    static UntrackCommand untrackCommand;

    static BotController botController;

    static TextHandler textHandler;

    @BeforeEach
    public void setUp() {
        textHandler = Mockito.mock(TextHandler.class);
        botController = Mockito.mock(BotController.class);
        untrackCommand = new UntrackCommand(textHandler, botController);
    }

    @Test
    @DisplayName("Successful termination of tracking")
    public void handleSuccessfulUntrackedLinkTest() {
        Mockito.when(textHandler.handle("command.untrack.successful_untrack"))
            .thenReturn("The link: %s is now untraceable");

        Mockito.when(botController.unTrackUserLink(1L, new Link("https://example.com"))).thenReturn(true);
        UpdateWrapper update = createMockUpdateWrapper("/untrack https://example.com", 1L);
        SendMessageWrapper sendMessage = untrackCommand.handle(update);
        assertEquals("The link: https://example.com is now untraceable", sendMessage.getParameters()
            .get("text"));
    }

    @Test
    @DisplayName("The link is not tracked")
    public void handleUntrackedLinkNotTrackedTest() {
        Mockito.when(textHandler.handle("command.untrack.not_tracked"))
            .thenReturn("The link: %s is not tracked");

        Mockito.when(botController.unTrackUserLink(1L, new Link("https://example.com"))).thenReturn(false);
        UpdateWrapper update = createMockUpdateWrapper("/untrack https://example.com", 1L);
        SendMessageWrapper sendMessage = untrackCommand.handle(update);
        assertEquals("The link: https://example.com is not tracked", sendMessage.getParameters().get("text"));
    }

    @Test
    @DisplayName("Wrong argument")
    public void handleInvalidArgumentCommandTest() {
        Mockito.when(textHandler.handle("message.invalid_argument"))
            .thenReturn("Invalid argument: %s");

        UpdateWrapper update = createMockUpdateWrapper("/untrack wefwe", 1L);
        SendMessageWrapper sendMessage = untrackCommand.handle(update);
        assertEquals("Invalid argument: wefwe", sendMessage.getParameters().get("text"));
    }

    @Test
    @DisplayName("Empty argument")
    public void handleEmptyArgumentCommandTest() {
        Mockito.when(textHandler.handle("message.empty_argument"))
            .thenReturn("Empty argument");

        UpdateWrapper update = createMockUpdateWrapper("/untrack", 1L);
        SendMessageWrapper sendMessage = untrackCommand.handle(update);
        assertEquals("Empty argument", sendMessage.getParameters().get("text"));
    }
}
