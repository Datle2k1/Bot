package common.base;

import task.api.request.RequestCore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseWorker implements Runnable {
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    public String name;
    //list luu tru thu muc lam viec hien tai
    protected String currentDir;
    protected boolean isRunning;
    //Doi tuong quan ly luong cong viec
    protected ExecutorService executorService;
    //Khai bao doi tuong requestCore
    protected RequestCore requestCore;

    /* **********************************************************************
     * Area : Constructor
     ********************************************************************** */
    public BaseWorker(String name,int timeout) {
        this.name = name;
        this.currentDir = System.getProperty("user.dir");
        this.isRunning = false;
        this.requestCore = new RequestCore(timeout);
        initThreadPool(0);
    }

    /* **********************************************************************
     * Area : Function - Public
     ********************************************************************** */
    //Tao ThreadPool chua cac Thread dmoi khi cac Thread ban dau van dang hoat dong.
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

    /* **********************************************************************
     * Area : Function - Private
     ********************************************************************** */
    protected void enableRunning() {
        isRunning = true;
    }
}
