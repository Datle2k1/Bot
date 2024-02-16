package manager;

import common.model.Request;
import task.SendRequest;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Manager {
    private final int period;

    public Manager(int period) {
        this.period = period;
    }

    public void run(Request request) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("Scheduler");
                new SendRequest("TEST", request, new SendRequest.Callback() {
                    @Override
                    public void success(String data) {
                        System.out.println(data);
                    }

                    @Override
                    public void fail(String msg) {
                        System.out.println(msg);
                    }
                });
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }, 0, period, TimeUnit.SECONDS);
    }
}
