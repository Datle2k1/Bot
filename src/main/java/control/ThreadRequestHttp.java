package control;

import okhttp3.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ThreadRequestHttp implements Runnable {
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
//        if (count == 0){
//            System.out.println("--- Send request --- Time : " + ++count);
//        }
//        if (count != 0 && count%6 == 0){
//            System.out.println("--- Send request --- Time : " + (count - ((count/2) + (2*n+1))));
//            n++;
//        }
        System.out.println("--- Send request --- Time : " + ++count);
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
            System.out.println("Network Response : " + response.networkResponse());
//            System.out.println("Body: " + response.body().string());
//            System.out.println("Header : " + response.headers());
            System.out.println("--- End Request ---\n");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
