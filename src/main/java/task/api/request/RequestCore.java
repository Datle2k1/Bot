package task.api.request;

import common.util.Utility;
import okhttp3.RequestBody;
import okhttp3.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    public void request(RequestParameter parameter, RequestCallback callback) {
        System.out.println("request");
        if (callback == null) {
            return;
        } else if (parameter == null) {
            callback.fail(String.format(ERROR_INPUT, "parameter"));
            return;
        } else if (parameter.url == null) {
            callback.fail(String.format(ERROR_INPUT, "parameter.url"));
            return;
        } else if (parameter.requestType == null) {
            callback.fail(String.format(ERROR_INPUT, "parameter.requestType"));
            return;
        }
        try {
            Request.Builder builder = new Request.Builder();
            RequestBody body = null;
            StringBuilder error = new StringBuilder();
            switch (parameter.requestType) {
                case GET:
                case HEAD:
                    String url = buildGetUrl(parameter.url, parameter.parameters);
                    builder.url(url);
                    break;
                case POST:
                case PUT:
                case DELETE:
                case PATCH:
                    builder.url(parameter.url);
                    if (parameter.parameters != null) {
                        body = buildPostBodyForm(parameter.parameters);
                    }
                    if (parameter.json != null) {
                        body = buildPostBodyJson(parameter.json);
                    }
                    break;
                default:
                    callback.fail(String.format(ERROR_INPUT, "parameter.requestType"));
                    return;
            }
            error.append(buildHeader(builder, parameter.headers));
            error.append(buildMethod(builder, parameter.requestType, body));

            if (error.length() > 0) {
                callback.fail(error.toString());
            } else {
                callback.prepare();
                Request request = builder.build();
                Response response = client.newCall(request).execute();
//                ResponseBody responseBody = response.body();
                //TODO tam thoi in ra moi code
//                String result = null;
//                if (responseBody != null) {
//                    result = responseBody.string();
//                }
                callback.success(response.code() + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.fail(e.getMessage());
        }
    }

    /* **********************************************************************
     * Area : Function - Private - Build Request
     ********************************************************************** */
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
        System.out.println("OkHttpClient");
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
     ********************************************************************** */
}
