package manager;

import common.model.Request;
import common.model.Response;
import common.model.ConfigFileJson;
import common.util.CheckCodeResponse;
import common.util.RequestStatus;
import common.util.Utility;
import okhttp3.OkHttpClient;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import task.MyBot;
import task.SendRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static common.util.RequestStatus.Complete;
import static common.util.RequestStatus.notComplete;
import static org.glassfish.grizzly.http.server.Constants.GET;

public class Manager {
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    public static RequestStatus status = Complete;
    //Tao doi tuong bot
    public MyBot bot;
    //Khai báo list lưu trữ response
    private List<Response> list = new ArrayList<>();
    //Khai báo biến đếm số lần Request 1 lượt các link trong file Json
    private int count = 0;
    //Khai báo đối tượng WebsiteLinks, idChannel ,period, timeout, token từ File Json
    private List<ConfigFileJson.WebsiteLink> listWebLinks;
    private String idChannel ;
    private static String token;
    private int period;
    private int timeout;

    /* **********************************************************************
     * Area : Function  - Public
     ********************************************************************** */

    //Chat cac Tasks
    public void run() throws IOException {
        //Kiem tra va lay thong tin tu file Json : configFileJson, listWebLinks, idChannel, period,timeout.
        System.out.println("--- Get Information From File Json ---");
        getInformationFromFileJson();
        //Khoi tao Bot neu Bot chua duoc khoi tao.
        if (!Utility.checkBotStatus(bot)){
            System.out.println("--- Create Bot ---");
            createBot();
        }
        //Xep lich
        System.out.println("--- Scheduler ---");
        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("--- Request Time : " + (++count) + "\n");
                bot.sendMessage("--- Request Time : " + count, idChannel);
                //Lay doi tuong websiteLinks tu listWebLinks
                for (ConfigFileJson.WebsiteLink w :listWebLinks ) {
                    status = notComplete;
                    //Kiem tra bodyType,method
                    if(!Utility.checkBodyType(w) || !Utility.checkMethod(w)) {
                        bot.sendMessage(Utility.outputError(w),idChannel);
                        continue;
                    }
                    //Tao reqeust
                    Request request = new Request(w.getMethod(), w.getUrl(), w.getBodyType());
                    new SendRequest("TEST",request,timeout,w,newCallback(w));
                    waitUntilComplete(1000);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }, 0, period, TimeUnit.SECONDS);
    }

    //Get information from File Json
    public void getInformationFromFileJson() throws IOException {
        //Tạo đối tượng ConfigFileJSon
        ConfigFileJson configFileJson = Utility.readFromJsonFile();
        Utility.checkInformationFromFIleJson(configFileJson);
        listWebLinks = configFileJson.getListWebLink();
        idChannel = configFileJson.getIdChannel();
        period = configFileJson.getRequest().getSchedule().getPeriod();
        timeout = configFileJson.getRequest().getTimeout();
        token = configFileJson.getToken();
    }

    //Tao bot
    public void createBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            bot = new MyBot(token);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //Delay giữa các Request trong lượt Request
    public void waitUntilComplete(int time) {
        while (true) {
            if (status == Complete) {
                System.out.println("---  Complete <---" + "\n");
                break;
            }
            System.out.println("---  not Complete ---");
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Tao callback bat su kien
    public SendRequest.Callback newCallback(ConfigFileJson.WebsiteLink w){
        return new SendRequest.Callback() {
            @Override
            public void success(Response response) {
                list.add(response);
                //Kiểm tra Response Code, lấy message code tương ứng
                String messageCode = CheckCodeResponse.checkCodeResponse(response.code);
                if (response.code/100 != 2) {
                    bot.sendMessage(Utility.getResponseString(response, w, messageCode),idChannel);
                    bot.onClosing();
                }
                status = Complete;
            }

            @Override
            public void fail(String msg) {
                System.out.println(msg);
                bot.sendMessage(msg,idChannel);
                status = Complete;
            }
        };
    }
}
