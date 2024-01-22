package org.example;

import okhttp3.*;
import okhttp3.internal.http.HttpMethod;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;


import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {
    static String inputURL = "";
    public static void main(String[] args) throws URISyntaxException, IOException {
//        String inputURL = "https://www.baeldung.com/java-try-with-resources/";
//        String inputURL = "https://jsonplaceholder.typicode.com/users";
//        String inputURL ="https://www.youtube.com/watch?v=WY_IiSepq2E";
        String inputURL ="https://msh-jfrog.sohatv.vn/ui/native/vcc-viva-ads-gradle-release-local/com/google/auto/auto-common/0.10/auto-common-0.10.pom";
        Scanner scanner = new Scanner(System.in);
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

class ThreadRequestHttp implements Runnable {
    static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String inputURL;
    private String inputMethod;
    static int count = 0,n = 0;
    public ThreadRequestHttp(String inputURL,String inputMethod) {
        this.inputURL = inputURL;
        this.inputMethod = inputMethod;
    }

    @Override
    public void run() {
        try {
            OkHttp(inputURL, inputMethod);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        count++;
    }

    private synchronized static void timeRequest(int count){
        if (count == 0){
            System.out.println("--- Send request --- Time : " + ++count);
        }
        if (count != 0 && count%6 == 0){
            System.out.println("--- Send request --- Time : " + (count - ((count/2) + (2*n+1))));
            n++;
        }
    }

    //
    public synchronized static void OkHttp(String inputURL,String method) throws URISyntaxException, MalformedURLException {
        URL url = new URI(inputURL).toURL();
        String json = null;
        RequestBody body = null;
        switch (method){
            case "PUT" : json = "{\n" +
                    "    \"id\": 10,\n" +
                    "    \"name\": \"Roronoa Zoro\",\n" +
                    "    \"username\": \"Moriah.Stanton\",\n" +
                    "    \"email\": \"Rey.Padberg@karina.biz\",\n" +
                    "    \"address\": {\n" +
                    "      \"street\": \"Kattie Turnpike\",\n" +
                    "      \"suite\": \"Suite 198\",\n" +
                    "      \"city\": \"Lebsackbury\",\n" +
                    "      \"zipcode\": \"31428-2261\",\n" +
                    "      \"geo\": {\n" +
                    "        \"lat\": \"-38.2386\",\n" +
                    "        \"lng\": \"57.2232\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    \"phone\": \"024-648-3804\",\n" +
                    "    \"website\": \"ambrose.net\",\n" +
                    "    \"company\": {\n" +
                    "      \"name\": \"Hoeger LLC\",\n" +
                    "      \"catchPhrase\": \"Centralized empowering task-force\",\n" +
                    "      \"bs\": \"target end-to-end models\"\n" +
                    "    }\n" +
                    "  }" ;
                body = RequestBody.create(json, JSON);
                break;
            case "PATCH" : json = "{ \"name\": \"Roronoa Zororerr\"}" ;
                body = RequestBody.create(json, JSON);
                break;
            case "POST" : json = "{ \"name\": \"Roronoa Zororerr - v2\"}" ;
                body = RequestBody.create(json, JSON);
                break;
            case "HEAD" : json = null; body = null; break;
            case "GET" : json = null; body = null; break;
            case "DELETE" : json = null; body = null; break;
        }
        Request request = new Request.Builder()
                .url(url)
                .method(method,body)
                .build();

        timeRequest(count);
        try (Response response = client.newCall(request).execute()) {
            System.out.println("--- Start Request ---");
            System.out.println("Request Method : " + request.method());
            System.out.println("Request Message : " + response.message());
            System.out.println("Response Code : " + response.code());
            System.out.println("Response is Successful : " + response.isSuccessful());
            System.out.println("Body: " + response.body().string());
//            System.out.println("Header : " + response.headers());
            System.out.println("--- End Request ---\n");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}