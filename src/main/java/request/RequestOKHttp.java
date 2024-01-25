package request;

import okhttp3.*;
import read.ReadHTML;

import java.io.IOException;
import java.net.URL;

public class RequestOKHttp {
    private static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static StringBuilder stringBuilder = new StringBuilder();
    static int count = 1;

    public synchronized static void OkHttp(Callback callback) {
            URL url = callback.getUrl();
            String method = callback.getMethod();
            String json ;
            RequestBody body = null;
            //check method request
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
                    .method(method, body)
                    .build();

            System.out.println("\n--- Send request --- Time : " + count);
            try (Response response = client.newCall(request).execute()) {
                stringBuilder.append("--- Start Request ---" + "\n");
                stringBuilder.append("Http Protocol : " + response.protocol() + "\n");
                stringBuilder.append("Request Method : " + request.method() + "\n");
                stringBuilder.append("Request Message : " + response.message() + "\n");
                stringBuilder.append("Response Code : " + response.code() + "\n");
                stringBuilder.append("Response is Successful : " + response.isSuccessful() + "\n");
                stringBuilder.append("Web Response : " + ReadHTML.readHTML(url) + "\n");
                stringBuilder.append("--- End Request ---\n");
                callback.execute(stringBuilder);
                stringBuilder.setLength(0);
                count++;
            } catch (IOException e) {
                System.out.println(e + "!!! request could not be executed");
            }
    }

    public interface Callback {
        URL getUrl();
        String getMethod();
        void execute(StringBuilder data);
    }
}