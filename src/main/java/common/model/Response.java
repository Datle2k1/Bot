package common.model;

import java.net.URL;

public class Response {
    URL url;
    int code;
    String method;
    String message;

    public Response(URL url, int code, String method, String message) {
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
