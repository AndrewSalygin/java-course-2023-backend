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

public class TrackCommandTest {

    static TrackCommand trackCommand;

    static BotController botController;

    static TextHandler textHandler;

    @BeforeEach
    public void setUp() {
        textHandler = Mockito.mock(TextHandler.class);
        botController = Mockito.mock(BotController.class);
        trackCommand = new TrackCommand(textHandler, botController);
    }

    @Test
    @DisplayName("Successful tracking")
    public void handleSuccessfulTrackLinkTest() {
        Mockito.when(textHandler.handle("command.track.successful_track"))
            .thenReturn("The link: %s is now being tracked");

        Mockito.when(botController.trackUserLink(1L, new Link("https://example.com"))).thenReturn(true);
        Update update = createMockUpdate("/track https://example.com", 1L);
        SendMessage sendMessage = trackCommand.handle(update);
        assertEquals("The link: https://example.com is now being tracked", sendMessage.getParameters()
            .get("text"));
    }

    @Test
    @DisplayName("The link is already being tracked")
    public void handleAlreadyTrackLinkTest() {
        Mockito.when(textHandler.handle("command.track.already_tracked"))
            .thenReturn("The link: %s is already being tracked");

        Mockito.when(botController.trackUserLink(1L, new Link("https://example.com"))).thenReturn(false);
        Update update = createMockUpdate("/track https://example.com", 1L);
        SendMessage sendMessage = trackCommand.handle(update);
        assertEquals("The link: https://example.com is already being tracked", sendMessage.getParameters()
            .get("text"));
    }

    @Test
    @DisplayName("Wrong argument")
    public void handleInvalidArgumentCommandTest() {
        Mockito.when(textHandler.handle("message.invalid_argument"))
            .thenReturn("Invalid argument: %s");

        Update update = createMockUpdate("/track wefwe", 1L);
        SendMessage sendMessage = trackCommand.handle(update);
        assertEquals("Invalid argument: wefwe", sendMessage.getParameters().get("text"));
    }

    @Test
    @DisplayName("Empty argument")
    public void handleEmptyArgumentCommandTest() {
        Mockito.when(textHandler.handle("message.empty_argument"))
            .thenReturn("Empty argument");

        Update update = createMockUpdate("/track", 1L);
        SendMessage sendMessage = trackCommand.handle(update);
        assertEquals("Empty argument", sendMessage.getParameters().get("text"));
    }
}
