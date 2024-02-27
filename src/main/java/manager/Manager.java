package manager;

import common.model.Request;
import common.model.Response;
import common.model.ConfigFileJson;
import common.util.CheckCodeResponse;
import common.util.RequestStatus;
import common.util.Utility;
import task.MyBot;
import task.SendRequest;
import task.api.request.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static common.util.RequestStatus.Complete;
import static common.util.RequestStatus.notComplete;

public class Manager {
    private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static MyBot bot = new MyBot("6640509759:AAHj0Rmrq6lhczZNQiXfOypvsaWiqRfBVfA");
    public static RequestStatus status;
    //Khai báo list lưu trữ response
    private List<Response> list = new ArrayList<>();
    //Khai báo biến đếm số lần Request 1 lượt các link trong file Json
    private int count = 0;
    //Khai báo đối tượng WebsiteLinks, idChannel ,period, timeout, bodyType từ File Json
    private List<ConfigFileJson.WebsiteLink> listWebLinks;
    private String idChannel ;
    private int period;
    private int timeout;

    //Chạy các task của Bot
    public void run() throws IOException {
        //Chạy Bot
        System.out.println("--- Create Bot ---");
        bot.createBot();
        status = Complete;
        //Lấy và kiểm tra thông tin từ file Config configFileJson, listWebLinks, idChannel, Schedule Period,Timeout.
        System.out.println("--- Get Information From File Json ---");
        getInformationFromFileJson();
        //Xếp lịch
        System.out.println("--- Scheduler ---");
        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("--- Request Time : " + (++count) + "\n");
                bot.sendMessage("--- Request Time : " + count, idChannel);
                //Lấy thông tin Website từ listWebLinks
                for (ConfigFileJson.WebsiteLink w :listWebLinks ) {
                    status = notComplete;
                    //Kiem tra bodyType,method
                    if(!Utility.checkBodyType(w) || !Utility.checkMethod(w)) {
                        bot.sendMessage(Utility.outputError(w),idChannel);
                        System.out.println(Utility.outputError(w));
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
    private void getInformationFromFileJson() throws IOException {
        //Tạo đối tượng ConfigFileJSon
        ConfigFileJson configFileJson = Utility.readFromJsonFile();
        Utility.checkInformationFromFIleJson(configFileJson);
        listWebLinks = configFileJson.getListWebLink();
        idChannel = configFileJson.getIdChannel();
        period = configFileJson.getRequest().getSchedule().getPeriod();
        timeout = configFileJson.getRequest().getTimeout();
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

    //New Callback
    public SendRequest.Callback newCallback(ConfigFileJson.WebsiteLink w){
        return new SendRequest.Callback() {
            @Override
            public void success(Response response) {
                list.add(response);
                //Kiểm tra Response Code, lấy message code tương ứng
                String messageCode = CheckCodeResponse.checkCodeResponse(response.getCode());
                if (response.getCode()/100 != 2) {
                    bot.sendMessage(Utility.getResponseString(response, w, messageCode),idChannel);
                    System.out.println(Utility.getResponseString(response,w,messageCode));
                } else {
                    System.out.println("\n" + Utility.getResponseString(response, w, messageCode) + "\n");
                }
                status = Complete;
            }

            @Override
            public void fail(String msg) {
                System.out.println(msg);
                if (idChannel != null){
                    bot.sendMessage(msg,idChannel);
                }
                status = Complete;
            }
        };
    }
}
