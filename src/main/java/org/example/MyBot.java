package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyBot extends TelegramLongPollingBot implements Runnable{
    @Override
    public void onUpdateReceived(Update update) {
        SendTo.txt = update.getMessage().getText().trim();
    }

    @Override
    public String getBotUsername() {
        // TODO
        return "JAVA_Favorite_Bot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "6640509759:AAHj0Rmrq6lhczZNQiXfOypvsaWiqRfBVfA";
    }

    @Override
    public void run() {
    }
}
