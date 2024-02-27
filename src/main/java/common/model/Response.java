package common.model;

public class Response {
    private String url;
    private int code;
    private String method;
    private String message;

    public Response(String url, int code, String method, String message) {
        this.url = url;
        this.code = code;
        this.method = method;
        this.message = message;
    }

    public String getUrl() {
        return url;
    }


    public int getCode() {
        return code;
    }


    public String getMethod() {
        return method;
    }


    public String getMessage() {
        return message;
    }
}
