package org.example;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;


public class Main {
    private static String inputURL;
    public static void main(String[] args) throws InterruptedException {
        //https://jsonplaceholder.typicode.com/user
        //https://docs.oracle.com/favicon.ico
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input URL 1: ");
        inputURL = scanner.nextLine();

        Thread thread_1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                httpRequest(inputURL,"GET");
                httpRequest(inputURL,"POST");
                httpRequest(inputURL,"PUT");
                httpRequest(inputURL,"DELETE");
            }
        });

        sendRequest(thread_1);

    }
    private static void sendRequest(Thread thread) throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        final ScheduledFuture<?> handle_1 = scheduledExecutorService.scheduleAtFixedRate(thread, 5,10, SECONDS);
        handle_1.wait(10000);
        scheduledExecutorService.schedule(new Runnable() {
            public void run() {
                handle_1.cancel(true);
                scheduledExecutorService.shutdown();
            }
        }, 1, TimeUnit.MINUTES);
    }

    private synchronized static void  httpRequest(String inputURL, String method) {
//        Boolean sendReq = true;
//        while (sendReq){
            try {
                URL url = new URI(inputURL).toURL();
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                    httpsURLConnection.setRequestMethod(method);
                    System.out.println("--- Start Request --- ");
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
//        }
    }
}