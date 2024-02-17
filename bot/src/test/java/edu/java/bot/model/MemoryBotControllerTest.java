package edu.java.bot.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MemoryBotControllerTest {

    private MemoryBotController controller;

    @BeforeEach
    public void setUp() {
        controller = new MemoryBotController();
    }

    @Test
    @DisplayName("User registered")
    public void isUserRegisteredTrueTest() {
        controller.registerUser(12L);
        assertTrue(controller.isUserRegistered(12L));
    }

    @Test
    @DisplayName("User doesn't registered")
    public void isUserRegisteredFalseTest() {
        assertFalse(controller.isUserRegistered(12L));
    }

    @Test
    @DisplayName("User has link isTrack()")
    public void isTrackUserLinkTrueTest() {
        controller.registerUser(1L);
        controller.trackUserLink(1L, new Link("google.com"));
        assertTrue(controller.isUserLinkTracked(1L, new Link("google.com")));
    }

    @Test
    @DisplayName("User hasn't link isTrack()")
    public void isTrackUserLinkFalseTest() {
        controller.registerUser(1L);
        assertFalse(controller.isUserLinkTracked(1L, new Link("google.com")));
    }

    @Test
    @DisplayName("ПUser hasn't link unTrack()")
    public void unTrackUserLinkTrueTest() {
        controller.registerUser(1L);
        assertFalse(controller.unTrackUserLink(1L, new Link("google.com")));
    }

    @Test
    @DisplayName("User has link unTrack()")
    public void unTrackUserLinkFalseTest() {
        controller.registerUser(1L);
        controller.trackUserLink(1L, new Link("google.com"));
        assertTrue(controller.unTrackUserLink(1L, new Link("google.com")));
    }

    @Test
    @DisplayName("List of links")
    public void userLinksTest() {
        controller.registerUser(1L);
        controller.trackUserLink(1L, new Link("google.com"));
        assertThat(controller.userLinks(1L)).hasSize(1).element(0)
            .isEqualTo(new Link("google.com"));
    }
}
