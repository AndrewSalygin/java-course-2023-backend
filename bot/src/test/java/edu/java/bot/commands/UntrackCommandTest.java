package edu.java.bot.commands;

import edu.java.bot.client.scrapper.dto.response.LinkResponse;
import edu.java.bot.dto.OptionalAnswer;
import edu.java.bot.dto.response.ApiErrorResponse;
import edu.java.bot.service.BotService;
import edu.java.bot.util.TextHandler;
import edu.java.bot.util.URLCreator;
import edu.java.bot.wrapper.Message;
import edu.java.bot.wrapper.MessageResponse;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UntrackCommandTest {

    static UntrackCommand untrackCommand;

    static BotService botService;

    static TextHandler textHandler;

    @BeforeEach
    public void setUp() {
        textHandler = Mockito.mock(TextHandler.class);
        botService = Mockito.mock(BotService.class);
        untrackCommand = new UntrackCommand(textHandler, botService);
    }

    @Test
    @DisplayName("Successful termination of tracking")
    public void handleSuccessfulUntrackedLinkTest() {
        Mockito.when(textHandler.handle("command.untrack.messages.successful_untrack", Map.of("link", "https://example.com")))
            .thenReturn("The link: https://example.com is now untraceable!");

        Mockito.when(botService.unTrackUserLink(1L, "https://example.com"))
            .thenReturn(OptionalAnswer.of(new LinkResponse(1L, URLCreator.createURL("https://example.com"))));
        Message message = new Message(1L, "/untrack https://example.com");
        MessageResponse sendMessage = untrackCommand.handle(message);
        assertEquals("The link: https://example.com is now untraceable!", sendMessage.text());
    }

    @Test
    @DisplayName("The link is not tracked")
    public void handleUntrackedLinkNotTrackedTest() {
        Mockito.when(textHandler.handle("command.untrack.messages.errors.not_tracked", Map.of("link", "https://example.com")))
            .thenReturn("The link: https://example.com is not tracked");

        Mockito.when(botService.unTrackUserLink(1L, "https://example.com"))
            .thenReturn(OptionalAnswer.error(new ApiErrorResponse("The link: https://example.com is not tracked.",
                null,
                null,
                null,
                null
            )));
        Message message = new Message(1L, "/untrack https://example.com");
        MessageResponse sendMessage = untrackCommand.handle(message);
        assertEquals("The link: https://example.com is not tracked.", sendMessage.text());
    }

    @Test
    @DisplayName("Wrong argument")
    public void handleInvalidArgumentCommandTest() {
        Mockito.when(textHandler.handle("message.invalid_argument", Map.of("argument", "wefwe")))
            .thenReturn("Invalid argument: wefwe");

        Message message = new Message(1L, "/untrack wefwe");
        MessageResponse sendMessage = untrackCommand.handle(message);
        assertEquals("Invalid argument: wefwe", sendMessage.text());
    }

    @Test
    @DisplayName("Empty argument")
    public void handleEmptyArgumentCommandTest() {
        Mockito.when(textHandler.handle("message.empty_argument"))
            .thenReturn("Empty argument");

        Message message = new Message(1L, "/untrack");
        MessageResponse sendMessage = untrackCommand.handle(message);
        assertEquals("Empty argument", sendMessage.text());
    }
}
