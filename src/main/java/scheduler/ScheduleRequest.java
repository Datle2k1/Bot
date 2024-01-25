package scheduler;

import request.RequestOKHttp;
import read.ReadHTML;

import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleRequest {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
    RequestOKHttp threadRequest;
    URL url;
    String method;

    public ScheduleRequest(URL url, String method) {
        this.url = url;
        this.method = method;
    }

    public void scheduleThread(){
        scheduler.scheduleAtFixedRate( new Runnable() {
            @Override
            public void run() {
                threadRequest.OkHttp(new RequestOKHttp.Callback() {
                    @Override
                    public URL getUrl() {
                        return url;
                    }
                    @Override
                    public String getMethod() {
                        return method;
                    }
                    @Override
                    public void execute(StringBuilder data) {
                        System.out.println(data);
                    }
                });
            }
        }, 5,10, TimeUnit.SECONDS);
    }
}
