package task;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyBot extends TelegramLongPollingBot{
    /* **********************************************************************
     * Area : Function - @Override
     ********************************************************************** */
    @Override
    public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update update) {
        //Gửi lên Channel khi websites bị lỗi
    }

    @Override
    public String getBotUsername() {
        return "JAVA_Favorite_Bot";
    }
    /* **********************************************************************
     * Area : Constructor
     ********************************************************************** */

    public MyBot(String botToken) {
        super(botToken);
    }
    /* **********************************************************************
     * Area : Function - Public
     ********************************************************************** */

    public void sendMessage(String text, String idChannel) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(idChannel);
        sendMessage.setText(text);

        try {
            execute(sendMessage);
        } catch (NullPointerException e) {
            throw new NullPointerException("--- Exceoption : \nError text null : " + e);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
