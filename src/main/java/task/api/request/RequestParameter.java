package task.api.request;


import java.util.Map;

public class RequestParameter {
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    public RequestMethod requestType;
    public RequestBody bodyType;
    public String url;
    public Map<String, String> headers;
    public Map<String, String> parameters;
    public String json;

    /* **********************************************************************
     * Area : Constructor
     ********************************************************************** */
    public RequestParameter() {
    }

    /* **********************************************************************
     * Area : Function - Public - Get
     ********************************************************************** */
    public void get(String url) {
        get(url, null, null);
    }

    public void get(String url, Map<String, String> parameters) {
        get(url, null, parameters);
    }

    public void get(String url, Map<String, String> headers, Map<String, String> parameters) {
        requestType = RequestMethod.GET;
        this.url = url;
        this.headers = headers;
        this.parameters = parameters;
    }

    /* **********************************************************************
     * Area : Function - Public - Post
     ********************************************************************** */
    public void post(RequestBody bodyType, String url) {
        post(bodyType, url, null, null);
    }

    public void post(RequestBody bodyType, String url, Map<String, String> parameters) {
        post(bodyType, url, null, parameters);
    }

    public void post(RequestBody bodyType, String url, Map<String, String> headers, Map<String, String> parameters) {
        this.requestType = RequestMethod.POST;
        this.bodyType = bodyType;
        this.url = url;
        this.headers = headers;
        this.parameters = parameters;
    }

    /* **********************************************************************
     * Area : Function - Public - Post - Data Form
     ********************************************************************** */
    public void postForm(String url) {
        postForm(url, null, null);
    }

    public void postForm(String url, Map<String, String> parameters) {
        postForm(url, null, parameters);
    }

    public void postForm(String url, Map<String, String> headers, Map<String, String> parameters) {
        this.requestType = RequestMethod.POST;
        this.bodyType = RequestBody.DATA_FORM;
        this.url = url;
        this.headers = headers;
        this.parameters = parameters;
    }

    /* **********************************************************************
     * Area : Function - Public - Post - Json
     ********************************************************************** */
    public void postJson(String url) {
        postJson(url, null, null);
    }

    public void postJson(String url, String json) {
        postJson(url, null, json);
    }

    public void postJson(String url, Map<String, String> headers, String json) {
        this.requestType = RequestMethod.POST;
        this.bodyType = RequestBody.JSON;
        this.url = url;
        this.headers = headers;
        this.json = json;
    }

    /* **********************************************************************
     * Area : Function - Private
     ********************************************************************** */
}