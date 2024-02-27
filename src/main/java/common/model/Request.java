package common.model;

public class Request {
    public String method;
    public String url;
    public String bodyType;

    public Request(String method, String url, String bodyType) {
        this.method = method;
        this.url = url;
        this.bodyType = bodyType;
    }
}
