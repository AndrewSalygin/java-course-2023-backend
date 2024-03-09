package edu.java.bot.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MemoryBotControllerTest {

    private MemoryBotService service;

    @BeforeEach
    public void setUp() {
        service = new MemoryBotService();
    }

    @Test
    @DisplayName("User registered")
    public void isUserRegisteredTrueTest() {
        service.registerUserIfNew(12L);
        assertTrue(service.isUserRegistered(12L));
    }

    @Test
    @DisplayName("User doesn't registered")
    public void isUserRegisteredFalseTest() {
        assertFalse(service.isUserRegistered(12L));
    }

    @Test
    @DisplayName("User has link isTrack()")
    public void isTrackUserLinkTrueTest() {
        service.registerUserIfNew(1L);
        service.trackUserLink(1L, new Link("google.com"));
        assertTrue(service.isUserLinkTracked(1L, new Link("google.com")));
    }

    @Test
    @DisplayName("User hasn't link isTrack()")
    public void isTrackUserLinkFalseTest() {
        service.registerUserIfNew(1L);
        assertFalse(service.isUserLinkTracked(1L, new Link("google.com")));
    }

    @Test
    @DisplayName("ПUser hasn't link unTrack()")
    public void unTrackUserLinkTrueTest() {
        service.registerUserIfNew(1L);
        assertFalse(service.unTrackUserLink(1L, new Link("google.com")));
    }

    @Test
    @DisplayName("User has link unTrack()")
    public void unTrackUserLinkFalseTest() {
        service.registerUserIfNew(1L);
        service.trackUserLink(1L, new Link("google.com"));
        assertTrue(service.unTrackUserLink(1L, new Link("google.com")));
    }

    @Test
    @DisplayName("List of links")
    public void userLinksTest() {
        service.registerUserIfNew(1L);
        service.trackUserLink(1L, new Link("google.com"));
        assertThat(service.userLinks(1L)).hasSize(1).element(0)
            .isEqualTo(new Link("google.com"));
    }
}
