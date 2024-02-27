package task.api.request;

import common.util.Utility;
import okhttp3.RequestBody;
import okhttp3.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static task.api.request.RequestBody.DATA_FORM;
import static task.api.request.RequestBody.JSON;

public class RequestCore {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private static final int TIMEOUT = 10;
    private final String CHARSET = "utf-8";
    private final String ERROR_INPUT = "Input[%s] invalid";

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private final OkHttpClient client;

    /* **********************************************************************
     * Area : Constructor
     ********************************************************************** */
    public RequestCore(int timeout) {
        this.client = buildRequestClient(timeout);
    }

    /* **********************************************************************
     * Area : Function - Public
     ********************************************************************** */
    public void request(RequestParameter requestParameter, RequestCallback callback) {
        System.out.println("--- Get Response ---");
        if (callback == null) {
            return;
        } else if (requestParameter == null) {
            callback.fail(String.format(ERROR_INPUT, "requestParameter"));
            return;
        } else if (requestParameter.url == null) {
            callback.fail(String.format(ERROR_INPUT, "requestParameter.url"));
            return;
        } else if (requestParameter.method == null) {
            callback.fail(String.format(ERROR_INPUT, "requestParameter.requestType"));
            return;
        } else if (!Utility.isUrlValidFormat(requestParameter.url)) {
            callback.fail(String.format(ERROR_INPUT, "requestParameter.url"));
            return;
        }
        try {
            Request.Builder builder = new Request.Builder();
            RequestBody body = null;
            StringBuilder error = new StringBuilder();
            String url;
            switch (requestParameter.method) {
                case GET:
                    url = buildGetUrl(requestParameter.url, requestParameter.parameters);
                    System.out.println(url);
                    builder.url(url);
                    break;
                case HEAD:
                case POST:
                    builder.url(requestParameter.url);
                    if (requestParameter.bodyType == JSON){
                        body = buildPostBodyJson(requestParameter.json);
                        break;
                    }
                    if (requestParameter.bodyType == DATA_FORM) {
                        body = buildPostBodyForm(requestParameter.parameters);
                        break;
                    }
                case PUT:
                case DELETE:
                case PATCH:
                default:
                    callback.fail(String.format(ERROR_INPUT, "requestParameter.requestType"));
                    return;
            }
            error.append(buildHeader(builder, requestParameter.headers));
            error.append(buildMethod(builder, requestParameter.method, body));

            if (error.length() > 0) {
                callback.fail(error.toString());
            } else {
                callback.prepare();
                Request request = builder.build();
                Response response = client.newCall(request).execute();
                if (requestParameter.headers != null){
//                    System.out.println(response.body().string());
                }
                common.model.Response resp = new common.model.Response(requestParameter.url, response.code(), requestParameter.method.toString(), response.message());
                callback.success(resp);
            }
        } catch (Exception e) {
            callback.fail(e.getMessage());
        }
    }

    /* **********************************************************************
     * Area : Function - Private - Build Request
     ********************************************************************** */
    //Thêm tham số vào url.
    private String buildGetUrl(String url, Map<String, String> parameters) throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder(url);
        if (parameters != null) {
            Set<String> keys = parameters.keySet();
            int index = 0;
            for (String key : keys) {
                String value = parameters.get(key);
                if (value == null) {
                    continue;
                }
                urlBuilder.append(index == 0 ? "?" : "&");
                urlBuilder.append(key);
                urlBuilder.append("=");
                if (Utility.isUrlValidFormat(value)) {
                    value = URLEncoder.encode(value, CHARSET);
                }
                urlBuilder.append(value);
                index++;
            }
        }
        return urlBuilder.toString();
    }

    private RequestBody buildPostBodyForm(Map<String, String> parameters) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (parameters != null && parameters.keySet().size() > 0) {
            Set<String> keys = parameters.keySet();
            for (String key : keys) {
                String value = parameters.get(key);
                if (value == null) {
                    continue;
                }
                bodyBuilder.add(key, value);
            }
        }
        return bodyBuilder.build();
    }

    private RequestBody buildPostBodyJson(String json) {
        String jsonType = String.format("application/json; charset=%s", CHARSET);
        MediaType mediaType = MediaType.get(jsonType);
        return RequestBody.create(json == null ? "" : json, mediaType);
    }

    private String buildHeader(Request.Builder builder, Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            builder.headers(Headers.of(headers));
        } else {
            return "";
        }
        return "";
    }

    private String buildMethod(Request.Builder builder, RequestMethod method, RequestBody body) {
        switch (method) {
            case GET:
                builder.get();
                break;
            case HEAD:
                builder.head();
                break;
            case POST:
                builder.post(body);
                break;
            case PUT:
                builder.put(body);
                break;
            case DELETE:
                builder.delete(body);
                break;
            case PATCH:
                builder.patch(body);
                break;
            default:
                return String.format(ERROR_INPUT, method);
        }
        return "";
    }

    /* **********************************************************************
     * Area : Function - Private - Initialize
     ********************************************************************** */
    private OkHttpClient buildRequestClient(int timeout) {
        System.out.println("\n" + "---> Build Client ---");
        //Tạo một phiên bản của OkHttpClient.Builder và định cấu hình.
        OkHttpClient.Builder builder;
        try {
            builder = new OkHttpClient.Builder().connectTimeout(timeout, TimeUnit.SECONDS).readTimeout(timeout, TimeUnit.SECONDS).writeTimeout(timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            builder = new OkHttpClient.Builder().connectTimeout(TIMEOUT, TimeUnit.SECONDS).readTimeout(TIMEOUT, TimeUnit.SECONDS).writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        }
        return builder.build();
    }

    /* **********************************************************************
     * Area : Function - Private
     ********************************************************************* */
}
