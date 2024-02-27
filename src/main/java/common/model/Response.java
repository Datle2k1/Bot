package common.model;

public class Response {
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    public String url;
    public int code;
    public String method;
    public String message;

    /* **********************************************************************
     * Area : Constructor
     ********************************************************************** */
    public Response(String url, int code, String method, String message) {
        this.url = url;
        this.code = code;
        this.method = method;
        this.message = message;
    }
}
