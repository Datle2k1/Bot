package task.api.request;

public abstract class RequestCallback {
    /* **********************************************************************
     * Area : Function
     ********************************************************************** */

    /**
     * Function call before make request
     */
    public void prepare() {
    }

    /* **********************************************************************
     * Area : Function - Abstract
     ********************************************************************** */

    /**
     * Function call when request success & get response
     *
     * @param data response
     */
    public abstract void success(String data);

    /**
     * Function call when something wrong though request session
     *
     * @param msg error message
     */
    public abstract void fail(String msg);
}