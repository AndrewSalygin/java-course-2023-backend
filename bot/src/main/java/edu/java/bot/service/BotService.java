package edu.java.bot.service;

import edu.java.bot.client.scrapper.dto.response.LinkResponse;
import edu.java.bot.client.scrapper.dto.response.ListLinksResponse;

public interface BotService {
    boolean isUserRegistered(Long id);

    void registerUserIfNew(Long id);

    void deleteUser(Long id);

    boolean isUserLinkTracked(Long id, String url);

    LinkResponse trackUserLink(Long id, String url);

    LinkResponse unTrackUserLink(Long id, String url);

    ListLinksResponse userLinks(Long id);
}
