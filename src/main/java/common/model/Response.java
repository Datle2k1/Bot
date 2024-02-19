package common.model;

import java.util.ArrayList;
import java.util.List;

public class Response {
    String url;
    int code;
    String method;
    String message;
    public static List<Response> list = new ArrayList<>();

    public Response(String url, int code, String method, String message) {
        this.url = url;
        this.code = code;
        this.method = method;
        this.message = message;
    }

    public List<Response> getList() {
        return list;
    }

    public void setList(List<Response> list) {
        this.list = list;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
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
