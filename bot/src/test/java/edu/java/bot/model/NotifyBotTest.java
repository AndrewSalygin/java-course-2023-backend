package edu.java.bot.model;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.processors.UserMessageProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class NotifyBotTest {

    @Test
    public void startTest() {
        TelegramBot bot = Mockito.mock(TelegramBot.class);
        NotifyBot notifyBot =
            new NotifyBot(bot, Mockito.mock(UserMessageProcessor.class));
        notifyBot.start();
        Mockito.verify(bot, Mockito.times(1)).setUpdatesListener(Mockito.eq(notifyBot));
    }

    @Test
    public void closeTest() {
        TelegramBot bot = Mockito.mock(TelegramBot.class);
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

    @Test
    public void executeSuccessfulTest() {
        TelegramBot bot = Mockito.mock(TelegramBot.class);
        NotifyBot notifyBot =
            new NotifyBot(bot, Mockito.mock(UserMessageProcessor.class));
        SendMessage sendMessage = new SendMessage(1, "Message");
        notifyBot.execute(sendMessage);
        Mockito.verify(bot, Mockito.times(1)).execute(sendMessage);
    }
}
