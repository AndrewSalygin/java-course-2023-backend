package edu.java.bot.model;

import java.util.Collection;

public interface BotService {
    boolean isUserRegistered(Long id);

    void registerUser(Long id);

    boolean isUserLinkTracked(Long id, Link link);

    boolean trackUserLink(Long id, Link link);

    boolean unTrackUserLink(Long id, Link link);

    Collection<Link> userLinks(Long id);
}
