package bot.tool.task;

import bot.tool.bin.Main;
import bot.tool.common.model.ResponseResult;
import bot.tool.common.util.RequestOKHttp;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendRequest implements Runnable {
    RequestOKHttp threadRequest;
    URL url;
    String method;
    static List<ResponseResult> list = new ArrayList<>();

    public SendRequest(URL url, String method) {
        this.url = url;
        this.method = method;
    }

    @Override
    public void run() {
        threadRequest.OkHttp(new RequestOKHttp.Callback() {
            @Override
            public URL getUrl() {
                return url;
            }

            @Override
            public String getMethod() {
                return method;
            }

            @Override
            public void execute(ResponseResult data) {
                System.out.println("--- Start Request ---");
                System.out.println("Code Response : " + data.getCode());
                System.out.println("Method Response : " + data.getMethod());
                System.out.println("Message Response : " + data.getMessage());
                System.out.println("--- End Request ---\n");
                list.add(data);
            }
        });
    }
}
