package manager;

import common.model.Request;
import common.model.Response;
import common.model.TelegramChannel;
import common.util.CheckCodeResponse;
import common.util.JsonFileReader;
import common.util.RequestStatus;
import task.MyBot;
import task.SendRequest;
import task.api.request.RequestMethod;

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
    private List<Response> list;
    //Khai báo  StringBuilder lấy kết quả từ list response
    private StringBuilder respString;
    //Khai báo biến đếm số lần Request 1 lượt các link trong file Json
    private int count = 0;
    //Khai báo đối tượng WebsiteLinks,idChannel ,periodtừ File Json
    private List<TelegramChannel.WebsiteLink> listWebLinks;
    private String idChannel ;
    private int period;

    //Chạy các task của Bot
    public void run() {
        //Chạy Bot
        bot.createBot();
        //Khởi tạo listWebLinks, idChannel, Schedule Period,respString, list resopnse
        listWebLinks = JsonFileReader.readFromJsonFile().getListWebLink();
        idChannel = JsonFileReader.readFromJsonFile().getIdChannel();
        period = JsonFileReader.readFromJsonFile().getScheduleRequest().getPeriod();
        respString = new StringBuilder();
        list = new ArrayList<>();
        //Xếp lịch
        System.out.println("--- Scheduler ---");
        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("--- Request Time : " + (++count) + "\n");
                bot.sendMessage("--- Request Time : " + count, idChannel);
                //Lấy thông tin Website từ file Json
                for (TelegramChannel.WebsiteLink w :listWebLinks ) {
                    status = notComplete;
                    //Khởi tọa Request
                    Request request = new Request(RequestMethod.POST, w.getUrl());
                    new SendRequest("TEST", request, new SendRequest.Callback() {
                        @Override
                        public void success(Response response) {
                            list.add(response);
                            //Lấy Response mới nhất từ list resopnse
                            int index = list.size();
                            //Kiểm tra Response Code, lấy message code tương ứng
                            int code = list.get(index - 1).getCode();
                            String messageCode = CheckCodeResponse.checkCodeResponse(code);
                            if (code/100 != 2) {
                                bot.sendMessage(getResponseFromListResponse(list, index, w, messageCode),idChannel);
//                                System.out.println("\n" + respString + "\n");
                            } else {
                                System.out.println("\n" + getResponseFromListResponse(list, index, w, messageCode) + "\n");
                            }
                            respString.setLength(0);
                            status = Complete;
                        }

                        @Override
                        public void fail(String msg) {
                            System.out.println("--- Error ---" + "\n");
                            bot.sendMessage(msg,idChannel);
                            status = Complete;
                        }
                    });
                    waitUntilComplete(1000);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }, 0, period, TimeUnit.SECONDS);
    }

    //Method lấy response từ list response
    public String getResponseFromListResponse(List<Response> list, int index, TelegramChannel.WebsiteLink w,String messageCode) {
        respString.append("--- Result Response ---" + "\n");
        respString.append("Websites : " + w.getName() + "\n");
        respString.append("URL Request : " + list.get(index - 1).getUrl() + "\n");
        respString.append("Request Method : " + list.get(index - 1).getMethod() + "\n");
        respString.append("Response Code : " + list.get(index - 1).getCode() + "\n");
        respString.append("Message Code : " + messageCode + "\n");
        if (!list.get(index - 1).getMessage().isEmpty()) {
            respString.append("Response Message : " + list.get(index - 1).getMessage() + "\n");
        }
        respString.append("--- End Response ---");
        return String.valueOf(respString);
    }

    //Delay giữa các Request trong lượt Request
    public void waitUntilComplete(int time) {
        while (true) {
            if (status == Complete) {
                System.out.println("--- Request Complete <---" + "\n");
                break;
            }
            System.out.println("--- Request not Complete ---");
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
