package org.example;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {
    public static void main(String[] args) {
        //https://jsonplaceholder.typicode.com/user
        //https://docs.oracle.com/favicon.ico
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input URL : ");
        String inputURL = scanner.nextLine();

        List<ScheduledFuture> list = new ArrayList<ScheduledFuture>();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

        list.add(scheduler.scheduleAtFixedRate(new ThreadRequestHttp("GET",inputURL,"GET"), 1, 10, SECONDS));
        list.add(scheduler.scheduleAtFixedRate(new ThreadRequestHttp("POST",inputURL,"POST"), 1, 10, SECONDS));
        list.add(scheduler.scheduleAtFixedRate(new ThreadRequestHttp("PUT",inputURL,"PUT"), 1, 10, SECONDS));
        list.add(scheduler.scheduleAtFixedRate(new ThreadRequestHttp("DELETE",inputURL,"DELETE"), 1, 10, SECONDS));
        scheduler.schedule(new EndWorker(scheduler, list), 30, SECONDS);

//        ScheduledFuture handle_1 = scheduler.scheduleAtFixedRate(new ThreadRequestHttp("GET",inputURL,"GET"), 1, 10, SECONDS);
//        ScheduledFuture handle_2 = scheduler.scheduleAtFixedRate(new ThreadRequestHttp("POST",inputURL,"POST"), 1, 10, SECONDS);
//        ScheduledFuture handle_3 = scheduler.scheduleAtFixedRate(new ThreadRequestHttp("PUT",inputURL,"PUT"), 1, 10, SECONDS);
//        ScheduledFuture handle_4 = scheduler.scheduleAtFixedRate(new ThreadRequestHttp("DELETE",inputURL,"DELETE"), 1, 10, SECONDS);
//        scheduler.schedule(new EndWorker(scheduler,handle_1), 30, SECONDS);
//        scheduler.schedule(new EndWorker(scheduler,handle_2), 30, SECONDS);
//        scheduler.schedule(new EndWorker(scheduler,handle_3), 30, SECONDS);
//        scheduler.schedule(new EndWorker(scheduler,handle_4), 30, SECONDS);
    }
}

class ThreadRequestHttp implements Runnable {
    private String name;
    private String inputURL;
    private String inputMethod;
    static int count = 0,n = 0;
    public ThreadRequestHttp(String name, String inputURL, String inputMethod) {
        this.name = name;
        this.inputURL = inputURL;
        this.inputMethod = inputMethod;
    }

    @Override
    public void run() {
        httpRequest(inputURL,inputMethod);
        count++;
    }

    private static void timeRequest(int count){
        if (count == 0){
            System.out.println("--- Send request --- Time : " + ++count);
        }
        if (count != 0 && count%4 == 0){
            System.out.println("--- Send request --- Time : " + (count - ((count/2) + n)));
            n++;
        }
    }

    private synchronized static void  httpRequest(String inputURL, String method) {
        try {
            URL url = new URI(inputURL).toURL();
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod(method);
            timeRequest(count);
            System.out.println("--- Start Request ---");
            System.out.println("Request Method : " + httpsURLConnection.getRequestMethod());
            System.out.println("Response Code : " + httpsURLConnection.getResponseCode());
            System.out.println("Response Message : " + httpsURLConnection.getResponseMessage());
            System.out.println("Connect Time Out : " + httpsURLConnection.getConnectTimeout());
            System.out.println("Error Stream : " + httpsURLConnection.getErrorStream());
            System.out.println("--- End Request ---\n");
        } catch (UnknownHostException e){
            System.out.println(e + " : !!! Not determined IP address of a host");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            System.out.println(e + " : !!! This may not be a url");
        } catch (IllegalArgumentException e){
            System.out.println(e + " : !!! This may not be a url");
        }
    }
}

//class EndWorker implements Runnable {
//    private ScheduledExecutorService scheduler;
//    private ScheduledFuture scheduledFuture;
//
//    public EndWorker(ScheduledExecutorService scheduler, ScheduledFuture scheduledFuture) {
//        this.scheduler = scheduler;
//        this.scheduledFuture = scheduledFuture;
//    }
//
//    @Override
//    public void run() {
//        scheduledFuture.cancel(true);
//        scheduler.shutdown();
//    }
//}

class EndWorker implements Runnable {
    private List<ScheduledFuture> list;
    private ScheduledExecutorService scheduler;

    public EndWorker(ScheduledExecutorService scheduler, List<ScheduledFuture> list) {
        this.scheduler = scheduler;
        this.list = list;
    }

    @Override
    public void run() {
        for (int i = 0; i < list.size() ; i++) {
            list.get(i).cancel(true);
        }
        scheduler.shutdown();
    }
}

