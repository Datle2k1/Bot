package common.model;

import task.api.request.RequestBody;
import task.api.request.RequestMethod;

public class Request {
    public RequestMethod method;
    public String url;
    public RequestBody requestBody;

    public Request(String url) {
        this(RequestMethod.GET, url);
    }

    public Request(RequestMethod method, String url) {
        this(method, url, RequestBody.JSON);
    }

    public Request(RequestMethod method, String url, RequestBody requestBody) {
        this.method = method;
        this.url = url;
        this.requestBody = requestBody;
    }
}
