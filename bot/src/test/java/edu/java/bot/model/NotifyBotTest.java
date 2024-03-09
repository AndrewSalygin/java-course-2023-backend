package edu.java.bot.model;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.processors.UserMessageProcessor;
import edu.java.bot.wrapper.TelegramService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class NotifyBotTest {

    @Test
    public void startTest() {
        TelegramBot bot = Mockito.mock(TelegramBot.class);
        UserMessageProcessor processor = Mockito.mock(UserMessageProcessor.class);
        TelegramService service = new TelegramService(bot, processor);
        NotifyBot notifyBot = new NotifyBot(service);
        notifyBot.start();
        Mockito.verify(bot, Mockito.times(1)).setUpdatesListener(Mockito.eq(notifyBot));
    }

    @Test
    public void closeTest() {
        TelegramBot bot = Mockito.mock(TelegramBot.class);
        UserMessageProcessor processor = Mockito.mock(UserMessageProcessor.class);
        TelegramService service = new TelegramService(bot, processor);
        NotifyBot notifyBot = new NotifyBot(service);
        notifyBot.start();
        notifyBot.close();
        Mockito.verify(bot, Mockito.times(1)).shutdown();
    }

    @Test
    public void executeTelegramBotNullTest() {
        NotifyBot notifyBot = new NotifyBot(null);
        assertThatThrownBy(notifyBot::start).isInstanceOf(RuntimeException.class);
    }
}
