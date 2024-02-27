package task.api.request;


import java.util.Map;

public class RequestParameter {
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    public RequestMethod method;
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
//    public void get(String url, RequestMethod method) {
//        get(url,method,null,null);
//    }
//
//    public void get(String url, RequestMethod method, Map<String, String> parameters) {
//        get(url, method ,null, parameters);
//    }
//
//    public void get(String url, Map<String, String> headers, RequestMethod method) {
//        get(url, method ,headers, null);

    public void get(String url, RequestMethod method,Map<String, String> parameters, Map<String, String> headers) {
        this.url = url;
        this.method = method;
        this.parameters = parameters;
        this.headers = headers;
    }

    /* **********************************************************************
     * Area : Function - Public - Post
     ********************************************************************** */
    public void post(String url, RequestMethod method, RequestBody bodyType) {
        post(url, method, bodyType, null,null);
    }

    public void post(String url, RequestMethod method,RequestBody bodyType,  Map<String, String> parameters) {
        post(url, method, bodyType, parameters,null);
    }

    public void post(String url, RequestMethod method, RequestBody bodyType, Map<String, String> parameters, Map<String, String> headers) {
        this.method = method;
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
        this.method = RequestMethod.POST;
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
        this.method = RequestMethod.POST;
        this.bodyType = RequestBody.JSON;
        this.url = url;
        this.headers = headers;
        this.json = json;
    }

    /* **********************************************************************
     * Area : Function - Private
     ********************************************************************** */
}