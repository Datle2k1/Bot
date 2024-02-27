package common.model;

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
    public Request(String method, String url, String bodyType) {
        this.method = method;
        this.url = url;
        this.bodyType = bodyType;
    }
}
