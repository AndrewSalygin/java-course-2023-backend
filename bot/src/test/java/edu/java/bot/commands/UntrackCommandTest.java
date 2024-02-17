package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.model.BotController;
import edu.java.bot.model.Link;
import edu.java.bot.util.TextHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static edu.java.bot.Utils.createMockUpdate;
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
        Update update = createMockUpdate("/untrack https://example.com", 1L);
        SendMessage sendMessage = untrackCommand.handle(update);
        assertEquals("The link: https://example.com is now untraceable", sendMessage.getParameters()
            .get("text"));
    }

    @Test
    @DisplayName("The link is not tracked")
    public void handleUntrackedLinkNotTrackedTest() {
        Mockito.when(textHandler.handle("command.untrack.not_tracked"))
            .thenReturn("The link: %s is not tracked");

        Mockito.when(botController.unTrackUserLink(1L, new Link("https://example.com"))).thenReturn(false);
        Update update = createMockUpdate("/untrack https://example.com", 1L);
        SendMessage sendMessage = untrackCommand.handle(update);
        assertEquals("The link: https://example.com is not tracked", sendMessage.getParameters().get("text"));
    }

    @Test
    @DisplayName("Wrong argument")
    public void handleInvalidArgumentCommandTest() {
        Mockito.when(textHandler.handle("message.invalid_argument"))
            .thenReturn("Invalid argument: %s");

        Update update = createMockUpdate("/untrack wefwe", 1L);
        SendMessage sendMessage = untrackCommand.handle(update);
        assertEquals("Invalid argument: wefwe", sendMessage.getParameters().get("text"));
    }

    @Test
    @DisplayName("Empty argument")
    public void handleEmptyArgumentCommandTest() {
        Mockito.when(textHandler.handle("message.empty_argument"))
            .thenReturn("Empty argument");

        Update update = createMockUpdate("/untrack", 1L);
        SendMessage sendMessage = untrackCommand.handle(update);
        assertEquals("Empty argument", sendMessage.getParameters().get("text"));
    }
}
