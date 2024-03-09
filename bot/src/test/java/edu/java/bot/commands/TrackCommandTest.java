package edu.java.bot.commands;

import edu.java.bot.service.BotService;
import edu.java.bot.util.TextHandler;
import edu.java.bot.wrapper.Message;
import edu.java.bot.wrapper.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrackCommandTest {

    static TrackCommand trackCommand;

    static BotService botService;

    static TextHandler textHandler;

    @BeforeEach
    public void setUp() {
        textHandler = Mockito.mock(TextHandler.class);
        botService = Mockito.mock(BotService.class);
        trackCommand = new TrackCommand(textHandler, botService);
    }

    @Test
    @DisplayName("Successful tracking")
    public void handleSuccessfulTrackLinkTest() {
        Mockito.when(textHandler.handle("command.track.successful_track"))
            .thenReturn("The link: %s is now being tracked");

        Mockito.when(botService.isUserLinkTracked(1L, "https://example.com")).thenReturn(false);
        Message message = new Message(1L, "/track https://example.com");
        MessageResponse sendMessage = trackCommand.handle(message);
        assertEquals("The link: https://example.com is now being tracked", sendMessage.text());
    }

    @Test
    @DisplayName("The link is already being tracked")
    public void handleAlreadyTrackLinkTest() {
        Mockito.when(textHandler.handle("command.track.already_tracked"))
            .thenReturn("The link: %s is already being tracked");

        Mockito.when(botService.isUserLinkTracked(1L, "https://example.com")).thenReturn(true);
        Message message = new Message(1L, "/track https://example.com");
        MessageResponse sendMessage = trackCommand.handle(message);
        assertEquals("The link: https://example.com is already being tracked", sendMessage.text());
    }

    @Test
    @DisplayName("Wrong argument")
    public void handleInvalidArgumentCommandTest() {
        Mockito.when(textHandler.handle("message.invalid_argument"))
            .thenReturn("Invalid argument: %s");

        Message message = new Message(1L, "/track wefwe");
        MessageResponse sendMessage = trackCommand.handle(message);
        assertEquals("Invalid argument: wefwe", sendMessage.text());
    }

    @Test
    @DisplayName("Empty argument")
    public void handleEmptyArgumentCommandTest() {
        Mockito.when(textHandler.handle("message.empty_argument"))
            .thenReturn("Empty argument");

        Message message = new Message(1L, "/track");
        MessageResponse sendMessage = trackCommand.handle(message);
        assertEquals("Empty argument", sendMessage.text());
    }
}
