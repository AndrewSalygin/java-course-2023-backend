package edu.java.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.wrapper.ChatWrapper;
import edu.java.bot.wrapper.MessageWrapper;
import edu.java.bot.wrapper.UpdateWrapper;
import org.mockito.Mockito;

public class Utils {

    public static UpdateWrapper createMockUpdateWrapper() {
        UpdateWrapper update = Mockito.mock(UpdateWrapper.class);
        MessageWrapper message = Mockito.mock(MessageWrapper.class);

        Mockito.when(update.message()).thenReturn(message);
        return update;
    }

    public static UpdateWrapper createMockUpdateWrapper(String text) {
        UpdateWrapper update = Mockito.mock(UpdateWrapper.class);
        MessageWrapper message = Mockito.mock(MessageWrapper.class);

        Mockito.when(message.text()).thenReturn(text);
        Mockito.when(update.message()).thenReturn(message);
        return update;
    }

    public static UpdateWrapper createMockUpdateWrapper(String text, Long chatId) {
        UpdateWrapper update = Mockito.mock(UpdateWrapper.class);
        MessageWrapper message = Mockito.mock(MessageWrapper.class);
        ChatWrapper chat = Mockito.mock(ChatWrapper.class);

        Mockito.when(message.text()).thenReturn(text);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.chat().id()).thenReturn(chatId);
        Mockito.when(update.message()).thenReturn(message);
        return update;
    }
}
