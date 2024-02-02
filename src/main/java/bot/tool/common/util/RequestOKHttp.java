package bot.tool.common.util;

import bot.tool.common.model.ResponseResult;
import okhttp3.*;
import java.io.IOException;
import java.net.URL;

public class RequestOKHttp {
    private static OkHttpClient client = new OkHttpClient();
//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
public static final MediaType TEXT = MediaType.parse("text/x-markdown");
    static int count = 1;
    public synchronized static void OkHttp(Callback callback) {
        URL url = callback.getUrl();
        String method = callback.getMethod();
        String postbody = "test post request";
        RequestBody requestBody = RequestBody.create(postbody,TEXT);
        Request request ;
        //check method request
        if (method.equals("POST")) {
            request = new Request.Builder().url(url).post(requestBody).build();
        } else {
            request = new Request.Builder().url(url).get().build();
        }

        System.out.println("\n--- Send request --- Time : " + count);
        try (Response response = client.newCall(request).execute()) {
            ResponseResult responseResult = new ResponseResult(url,response.code(),method,response.message());
            callback.execute(responseResult);
            count++;
        } catch (IOException e) {
            System.out.println(e + "!!! request could not be executed");
        }
    }

    public interface Callback {
        URL getUrl();
        String getMethod();
        void execute(ResponseResult data);
    }
}