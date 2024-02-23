package edu.java.bot.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class MemoryBotController implements BotController {

    private final ConcurrentHashMap<Long, HashSet<Link>> usersLinks;

    public MemoryBotController() {
        usersLinks = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isUserRegistered(Long id) {
        return usersLinks.containsKey(id);
    }

    @Override
    public void registerUser(Long id) {
        if (!isUserRegistered(id)) {
            usersLinks.put(id, new HashSet<>());
        }
    }

    @Override
    public boolean isUserLinkTracked(Long id, Link link) {
        if (isUserRegistered(id)) {
            return usersLinks.get(id).contains(link);
        } else {
            return false;
        }
    }

    @Override
    public boolean trackUserLink(Long id, Link link) {
        if (!isUserLinkTracked(id, link)) {
            return usersLinks.get(id).add(link);
        }
        return false;
    }

    @Override
    public boolean unTrackUserLink(Long id, Link link) {
        if (isUserLinkTracked(id, link)) {
            return usersLinks.get(id).remove(link);
        }
        return false;
    }

    @Override
    public Collection<Link> userLinks(Long id) {
        if (isUserRegistered(id)) {
            return usersLinks.get(id);
        }
        return new HashSet<>();
    }
}
