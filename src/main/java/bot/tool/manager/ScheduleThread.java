package bot.tool.manager;

import bot.tool.common.model.ResponseResult;
import bot.tool.task.SendRequest;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleThread {
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();;
    static List<ResponseResult> listResult = new ArrayList<>();
    SendRequest sendRequest;
    public ScheduleThread(SendRequest sendRequest) {
        this.sendRequest = sendRequest;
    }

    public void scheduleThread(){
        scheduler.scheduleAtFixedRate(sendRequest, 5,20, TimeUnit.SECONDS);
    }
}
