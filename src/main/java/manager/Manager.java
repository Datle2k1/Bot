package manager;

import bot.MyBot;
import common.model.Request;
import common.model.Response;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import task.SendRequest;

import java.util.concurrent.*;

public class Manager {
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private static ScheduledFuture<?> handle;
    private static MyBot bot = new MyBot("6640509759:AAHj0Rmrq6lhczZNQiXfOypvsaWiqRfBVfA");
    private final int period;

    public Manager(int period) {
        this.period = period;
    }

    public void run(Request request,Update update) {
        handle = scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Scheduler");
                    new SendRequest("TEST", request, new SendRequest.Callback() {
                        @Override
                        public void success(StringBuilder data) {
                            bot.sendMessage(update, String.valueOf(data));
                        }
                        @Override
                        public void fail(String msg) {
                            System.out.println(msg);
                        }
                    });
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        },0, period, TimeUnit.SECONDS);
    }

    public static void cancel(){
        handle.cancel(true);
//        scheduler.shutdown();
        //Xóa danh sách các Response với URL trước.
        Response.list.clear();
    }
}
