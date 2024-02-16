package task;

import common.base.BaseWorker;
import common.model.Request;
import task.api.request.RequestCallback;
import task.api.request.RequestParameter;

public class SendRequest extends BaseWorker {
    private Callback callback;

    private Request request;

    public SendRequest(String name, Request request, Callback callback) {
        super(name);
        this.request = request;
        this.callback = callback;
    }

    @Override
    public void run() {
        System.out.println("RUN");
        enableRunning();
        try {
            RequestParameter requestParameter = new RequestParameter();
            switch (request.method) {
                case GET:
                case HEAD:
                    requestParameter.get(request.url);
                    break;
                case POST:
                case PUT:
                case DELETE:
                case PATCH:
                    requestParameter.post(request.requestBody, request.url);
                    break;
                default:
                    return;
            }

            requestCore.request(requestParameter, new RequestCallback() {
                @Override
                public void success(String data) {
                    //TODO xu ly gi thi tuy
                    callback.success(data);
                }

                @Override
                public void fail(String msg) {
                    //TODO xu ly gi thi tuy
                    callback.fail(msg);

                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public interface Callback {
        void success(String data);

        void fail(String msg);
    }
}