package task;

import common.base.BaseWorker;
import common.model.Request;
import common.util.ThreadStatus;
import manager.Manager;
import task.api.request.RequestCallback;
import task.api.request.RequestParameter;

public class SendRequest extends BaseWorker {
    public static ThreadStatus status;
    private Callback callback;
    private Request request;

    public SendRequest(String name, Request request, Callback callback) {
        super(name);
        this.request = request;
        this.callback = callback;
    }

    @Override
    public void run() {
        System.out.println("--- RUN Request ---");
        status = ThreadStatus.Running;
        enableRunning();
        try {
            RequestParameter requestParameter = new RequestParameter();
            switch (request.method) {
                case GET:
                case HEAD:
                    requestParameter.get(request.url);
                    break;
                case POST:
                    requestParameter.post(request.requestBody, request.url);
                    break;
                case PUT:
                case DELETE:
                case PATCH:
                default:
                    return;
            }
            //
            requestCore.request(requestParameter, new RequestCallback() {
                @Override
                public void success(StringBuilder data) {
                    callback.success(data);
                }
                @Override
                public void fail(String msg) {
                    callback.fail(msg);
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        status = ThreadStatus.Complete;
    }

    public interface Callback {
        void success(StringBuilder data);
        void fail(String msg);
    }
}
