package edu.java.bot.wrapper;

import com.pengrad.telegrambot.model.Update;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateWrapper {

    private Update update;

    public MessageWrapper message() {
        return new MessageWrapper(update.message());
    }

}
