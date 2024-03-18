package edu.java.client.bot;

import edu.java.client.bot.dto.request.LinkUpdate;
import edu.java.dto.OptionalAnswer;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface BotClient {
    @PostExchange("/updates")
    OptionalAnswer<Void> handleUpdate(@RequestBody @Valid LinkUpdate linkUpdate);
}
