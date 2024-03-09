package edu.java.bot.commands;

import edu.java.bot.client.scrapper.dto.response.LinkResponse;
import edu.java.bot.client.scrapper.dto.response.ListLinksResponse;
import edu.java.bot.service.BotService;
import edu.java.bot.util.TextHandler;
import edu.java.bot.wrapper.Message;
import edu.java.bot.wrapper.MessageResponse;
import java.net.URI;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListCommandTest {

    static ListCommand listCommand;

    static BotService botService;

    static TextHandler textHandler;

    @BeforeEach
    public void setUp() {
        textHandler = Mockito.mock(TextHandler.class);
        botService = Mockito.mock(BotService.class);
        listCommand = new ListCommand(textHandler, botService);
    }

    @Test
    @DisplayName("No tracked links")
    public void handleReturnEmptyListMessageTest() {
        Mockito.when(textHandler.handle("command.list.empty")).thenReturn("You haven't any tracked links");
        Message message = new Message(1L, "/list");
        Mockito.when(botService.userLinks(1L)).thenReturn(new ListLinksResponse(List.of(), 1));
        MessageResponse sendMessage = listCommand.handle(message);
        assertEquals("You haven't any tracked links", sendMessage.text());
    }

    @SneakyThrows @Test
    @DisplayName("Displaying tracked links")
    public void handleReturnNotEmptyListMessageTest() {
        Mockito.when(textHandler.handle("command.list.show")).thenReturn("Your tracked links:\n %s");
        Mockito.when(botService.userLinks(1L))
            .thenReturn(new ListLinksResponse(List.of(
                new LinkResponse(1L, URI.create("http://example.com").toURL()),
                new LinkResponse(1L, URI.create("https://example.com").toURL())
            ), 2));

        Message message = new Message(1L, "/list");
        MessageResponse sendMessage = listCommand.handle(message);

        assertEquals(
            "Your tracked links:\n http://example.com\nhttps://example.com\n",
            sendMessage.text()
        );
        Mockito.verify(botService, Mockito.times(2)).userLinks(1L);
    }
}
