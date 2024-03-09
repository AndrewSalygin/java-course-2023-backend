package edu.java.bot.wrapper;

import com.pengrad.telegrambot.model.Chat;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChatWrapper {

    Chat chat;

    public Long id() {
        return chat.id();
    }

    public String username() {
        return chat.username();
    }
}
