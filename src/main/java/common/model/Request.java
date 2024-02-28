package common.model;

import task.api.request.RequestBody;

import static task.api.request.RequestBody.JSON;

public class Request {
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    public String method;
    public String url;
    public String bodyType;
    /* **********************************************************************
     * Area : Constructor
     ********************************************************************** */
    public Request(String method, String url) {
        this(method, url, "JSON");
    }
    public Request(String method, String url, String bodyType) {
        this.method = method;
        this.url = url;
        this.bodyType = bodyType;
    }
}
