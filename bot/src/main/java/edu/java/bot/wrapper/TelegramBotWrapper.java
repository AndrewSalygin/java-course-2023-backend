package edu.java.bot.wrapper;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.model.NotifyBot;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TelegramBotWrapper {

    TelegramBot telegramBot;

    public TelegramBotWrapper(String token) {
        telegramBot = new TelegramBot(token);
    }

    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> baseRequest) {
        telegramBot.execute(baseRequest);
    }

    public void setUpdatesListener(NotifyBot notifyBot) {
        telegramBot.setUpdatesListener((UpdatesListener) notifyBot);
    }

    public void shutdown() {
        telegramBot.shutdown();
    }
}
