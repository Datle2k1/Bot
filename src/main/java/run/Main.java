package run;

import control.ThreadRequestHttp;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {
    public static void main(String[] args){
//        String inputURL = "https://www.baeldung.com/java-try-with-resources/";
//        String inputURL = "https://jsonplaceholder.typicode.com/users";
//        String inputURL = "https://minor-api-os.hoyoverse.com/common/h5log/log/batch?topic=plat_h5log-oversea-account-fe";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input URL : ");
        String inputURL = scanner.nextLine();
        System.out.print("Input Method Request : ");
        String method = scanner.nextLine();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
        switch (method){
            case "GET" : scheduler.scheduleAtFixedRate(new ThreadRequestHttp(inputURL,"GET"),1,20, SECONDS);    break;
            case "POST" : scheduler.scheduleAtFixedRate(new ThreadRequestHttp(inputURL,"POST"),1,20, SECONDS);  break;
            case "PUT" : scheduler.scheduleAtFixedRate(new ThreadRequestHttp(inputURL,"PUT"),1,20, SECONDS);    break;
            case "PATCH" : scheduler.scheduleAtFixedRate(new ThreadRequestHttp(inputURL,"PATCH"),1,20, SECONDS);    break;
            case "DELETE" : scheduler.scheduleAtFixedRate(new ThreadRequestHttp(inputURL,"DELETE"),1,20, SECONDS);  break;
            case "HEAD" : scheduler.scheduleAtFixedRate(new ThreadRequestHttp(inputURL,"HEAD"),1,20, SECONDS);  break;
        }
    }
}