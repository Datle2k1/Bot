package manager;

import task.MyBot;
import common.model.Request;
import common.model.Response;
import common.model.WebsiteLink;
import common.util.JsonFileReader;
import common.util.ThreadStatus;
import common.util.Utility;
import task.SendRequest;
import task.api.request.RequestMethod;

import java.util.concurrent.*;

public class Manager {
    private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static MyBot bot = new MyBot("6640509759:AAHj0Rmrq6lhczZNQiXfOypvsaWiqRfBVfA");
    public static ThreadStatus status;
    private final int period;
    public Manager(int period) {
        this.period = period;
    }

    public void run() {
        scheduler.scheduleAtFixedRate(() -> {
            status = ThreadStatus.Running;
            try {
                System.out.println("--- Scheduler ---");
                //Lấy thông tin Website từ file Json
                for (WebsiteLink w: JsonFileReader.readFIle()){
                    if(Utility.isUrlValidFormat(w.getUrl())){
                        //Tạo Request
                        Request request = new Request(RequestMethod.POST,w.getUrl());
                        new SendRequest("TEST", request, new SendRequest.Callback() {
                            @Override
                            public void success(StringBuilder data) {
                                int index = Response.list.size();
                                int code = Response.list.get(index - 1).getCode();
                                StringBuilder add = new StringBuilder(w.getName());
                                add.append(data);
                                //Kiểm tra Response Code
                                if (code / 100 == 4 ||code / 100 == 5){
                                    System.out.println("\n");
                                    bot.sendMessage(String.valueOf(data));
                                } else{
                                    System.out.println(data);
                                }
                            }
                            @Override
                            public void fail(String msg) {
                                bot.sendMessage(msg);
                                status = ThreadStatus.Complete;
                            }
                        });
                    } else {
                        bot.sendMessage("Website : " + w.getName() + "Error URL : " + w.getUrl());
                    }
                    while (true){
                        if (status == ThreadStatus.Running || SendRequest.status == ThreadStatus.Running){
                            java.lang.Thread.sleep(1000);
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }, 0, period, TimeUnit.SECONDS);
    }
}
