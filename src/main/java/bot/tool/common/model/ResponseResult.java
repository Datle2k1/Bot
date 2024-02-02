package bot.tool.common.model;

import bot.tool.bin.Main;
import bot.tool.common.util.RequestOKHttp;
import bot.tool.task.SendRequest;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseResult{
    URL url;
    int code;
    String method;
    String message;

    public ResponseResult(URL url, int code, String method, String message) {
        this.url = url;
        this.code = code;
        this.method = method;
        this.message = message;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
