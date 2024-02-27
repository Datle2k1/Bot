package task;

import common.base.BaseWorker;
import common.model.ConfigFileJson;
import common.model.Request;
import common.model.Response;
import common.util.Utility;
import task.api.request.RequestBody;
import task.api.request.RequestCallback;
import task.api.request.RequestMethod;
import task.api.request.RequestParameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import static task.api.request.RequestMethod.*;

public class SendRequest extends BaseWorker {
    private final Callback callback;
    private final Request request;
    private final ConfigFileJson.WebsiteLink w;

    public SendRequest(String name, Request request, int timeout, ConfigFileJson.WebsiteLink w,Callback callback) {
        super(name,timeout);
        this.request = request;
        this.callback = callback;
        this.w = w;
    }

    @Override
    public void run() {
        System.out.println("--- RUN Request ---");
        enableRunning();
        try {
            //Khoi tao doi tuong requestParameter
            RequestParameter requestParameter = new RequestParameter();
            //Lay list parameters tu doi tuong webLink w
            RequestMethod method = RequestMethod.valueOf(request.method); //must
            RequestBody bodyType = RequestBody.valueOf(request.bodyType); //POST, PATCH, PUT
            String url = request.url; //must
            Map<String,String> parameters = Utility.getParameters(w);  //(GET, HEAD, DELETE: trong url), (POST, PATCH, PUT: trong body)
            Map<String,String> headers = Utility.getHeader(w);
            //Nhap cac tham so cua doi tuong requestParemeter : bodyType, url, parameters.
            switch (method){
                case GET :
                    requestParameter.get(url, method, parameters, headers);
                    break;
//                case PUT : requestParameter.post(requestParameter.url, PUT, requestParameter.bodyType, requestParameter.parameters, requestParameter.headers); break;
//                case HEAD : requestParameter.get(requestParameter.url, HEAD, requestParameter.parameters, requestParameter.headers); break;
                case POST :
                    requestParameter.post(url, method, bodyType, parameters, headers);
                    break;
//                case PATCH : requestParameter.post(requestParameter.url, PATCH, requestParameter.bodyType, requestParameter.parameters, requestParameter.headers); break;
//                case DELETE : requestParameter.get(requestParameter.url, DELETE, requestParameter.parameters, requestParameter.headers); break;
            }
            requestCore.request(requestParameter, new RequestCallback() {
                @Override
                public void success(Response response) {
                    callback.success(response);
                }

                @Override
                public void fail(String msg) {
                    callback.fail(msg);
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public interface Callback {
        void success(Response response);
        void fail(String msg);
    }
}
