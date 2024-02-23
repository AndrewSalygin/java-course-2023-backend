package edu.java.bot.model;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.processors.UserMessageProcessor;
import edu.java.bot.wrapper.TelegramBotWrapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class NotifyBotTest {

    @Test
    public void startTest() {
        TelegramBotWrapper bot = Mockito.mock(TelegramBotWrapper.class);
        NotifyBot notifyBot =
            new NotifyBot(bot, Mockito.mock(UserMessageProcessor.class));
        notifyBot.start();
        Mockito.verify(bot, Mockito.times(1)).setUpdatesListener(Mockito.eq(notifyBot));
    }

    @Test
    public void closeTest() {
        TelegramBotWrapper bot = Mockito.mock(TelegramBotWrapper.class);
        NotifyBot notifyBot =
            new NotifyBot(bot, Mockito.mock(UserMessageProcessor.class));
        notifyBot.start();
        notifyBot.close();
        Mockito.verify(bot, Mockito.times(1)).shutdown();
    }

    @Test
    public void executeTelegramBotNullTest() {
        NotifyBot notifyBot =
            new NotifyBot(null, Mockito.mock(UserMessageProcessor.class));
        assertThatThrownBy(notifyBot::start).isInstanceOf(RuntimeException.class);
    }
}
