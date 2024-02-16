package common.base;

import task.api.request.RequestCore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseWorker implements Runnable {
    public String name;
    protected String currentDir;
    protected boolean isRunning;
    protected ExecutorService executorService;
    protected RequestCore requestCore;

    public BaseWorker(String name) {
        this.name = name;
        this.currentDir = System.getProperty("user.dir");
        this.isRunning = false;
        this.requestCore = new RequestCore(10);
        initThreadPool(0);
    }

    public void initThreadPool(int number) {
        if (number <= 0) {
            number = 1;
        }
        this.executorService = Executors.newFixedThreadPool(number);
        this.executorService.execute(this);
    }

    @Override
    public void run() {
    }

    public boolean isRunning() {
        return isRunning;
    }

    protected void enableRunning() {
        isRunning = true;
    }

    protected void disableRunning() {
        isRunning = false;
    }
}
