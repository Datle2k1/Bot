package task;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class MyBot extends TelegramLongPollingBot {
    public static String token = "6640509759:AAHj0Rmrq6lhczZNQiXfOypvsaWiqRfBVfA";
    @Override
    public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update update) {
        //Gửi lên Channel khi websites bị lỗi
    }
    @Override
    public String getBotUsername() {
        return "JAVA_Favorite_Bot";
    }

    public MyBot(String botToken) {
        super(botToken);
    }

    public void sendMessage(String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId("@Test_Bot_Java");
        sendMessage.setText(text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createBot(){
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            MyBot myBot = new MyBot(MyBot.token);
            botsApi.registerBot(myBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}