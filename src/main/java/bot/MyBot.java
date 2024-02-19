package bot;

//import bot.tool.common.util.CheckURL;
import common.base.BaseWorker;
import common.model.Request;
import common.util.Utility;
import manager.Manager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import task.api.request.RequestMethod;

import java.net.URL;

public class MyBot extends TelegramLongPollingBot {
    public static String url;
    Request request;
    Manager manager;
    BotStatus status = BotStatus.notRunning;
    @Override
    public void onUpdateReceived(Update update) {
        //Get Post
        if (getPostChannel(update)){
            if (status == BotStatus.notRunning){
                createRequestSchedule(update);
                status = BotStatus.Running;
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "JAVA_Favorite_Bot";
    }

    public MyBot(String botToken) {
        super(botToken);
    }

    public void sendMessage(Update update, String msg){
        if (msg != null){
            SendMessage response = new SendMessage();
            response.setChatId(update.getChannelPost().getChatId().toString());
            response.setText(msg);
            try {
                execute(response);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Boolean getPostChannel(Update update) {
        url = update.getChannelPost().getText().trim();
        if (status == BotStatus.Running){
            Manager.cancel();
            status = BotStatus.notRunning;
        }
        if (!Utility.isUrlValidFormat(url)){
            sendMessage(update,"Error URL");
        } else {
            return true;
        }
        return false;
    }

    public void createRequestSchedule(Update update){
        //Khởi tạo.
        request = new Request(RequestMethod.POST,url);
        manager = new Manager(20);
        manager.run(request,update);
    }

    public static void createBot(){
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyBot("6640509759:AAHj0Rmrq6lhczZNQiXfOypvsaWiqRfBVfA"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
