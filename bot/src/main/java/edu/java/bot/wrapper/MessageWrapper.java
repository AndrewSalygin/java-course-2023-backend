package edu.java.bot.wrapper;

import com.pengrad.telegrambot.model.Message;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageWrapper {

    private Message message;

    public ChatWrapper chat() {
        return new ChatWrapper(message.chat());
    }

    public String text() {
        return message.text();
    }
}
