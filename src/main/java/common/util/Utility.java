package common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.model.ConfigFileJson;
import common.model.Response;
import task.api.request.RequestMethod;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static task.api.request.RequestBody.DATA_FORM;
import static task.api.request.RequestBody.JSON;

public class Utility {
    /* *******************************************************************************
     * Area : Function - Read File Json
     **********************************************************************************/
    //Read file Json tra ve doi tuong ConfigFileJson.
    public static ConfigFileJson readFromJsonFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("src/main/assets/Websites.json");
            return objectMapper.readValue(file, ConfigFileJson.class);
        } catch (NullPointerException e) {
            throw new NullPointerException("--- Exceoption : \nFile dows not exits : " + e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("--- Exception : \nFile Json not found : " + e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* ************************************************************************************
     * Area : Function - Check idChannel, timeout, period, listWeblinks, bodyType, method
     ***************************************************************************************/
    //Kiem tra idChannel,timeout, period, listweblink.
    public static void checkInformationFromFIleJson(ConfigFileJson configFileJson) {
        if (configFileJson.getIdChannel() == null || configFileJson.getIdChannel().isEmpty()) {
            throw new RuntimeException("\nidChannel null or Empty");
        }
        if (configFileJson.getRequest().getSchedule().getPeriod() <= 0 || configFileJson.getRequest().getTimeout() <= 0) {
            throw new RuntimeException("\nperiod & timeout must > 0 ");
        }
        if (configFileJson.getListWebLink() == null || configFileJson.getListWebLink().isEmpty()) {
            throw new RuntimeException("\nList listWebLinks is Empty ");
        }
        if (configFileJson.getToken() == null || configFileJson.getToken().isEmpty()){
            throw new RuntimeException("\nToken Bot null or Empty");
        }
    }

    //Kiem tra bodyType
    public static Boolean checkBodyType(ConfigFileJson.WebsiteLink w) {
        if (!JSON.toString().equals(w.getBodyType()) && !DATA_FORM.toString().equals(w.getBodyType())) {
            return false;
        }
        return true;
    }

    //Kiem tra method
    public static Boolean checkMethod(ConfigFileJson.WebsiteLink w) {
        for (RequestMethod m : RequestMethod.values()) {
            if (w.getMethod().equals(m.toString())) {
                return true;
            }
        }
        return false;
    }

    /* ************************************************************************************
     * Area : Function - Check & Get header, parameter, link.
     ***************************************************************************************/
    //Kiem tra va lay Parameter neu co
    public static Map<String, String> getParameters(ConfigFileJson.WebsiteLink w) {
        List<ConfigFileJson.WebsiteLink.Parameter> listParameter = w.getListParameter();
        if (listParameter == null || listParameter.isEmpty()) {
            return null;
        }
        Map<String, String> parameters = new HashMap<>();
        for (ConfigFileJson.WebsiteLink.Parameter p : listParameter) {
            parameters.put(p.getKey(), p.getValue());
        }
        return parameters;
    }

    //Kiem tra va Lay Header neu co
    public static Map<String, String> getHeader(ConfigFileJson.WebsiteLink w) {
        List<ConfigFileJson.WebsiteLink.Header> listHeaders = w.getListHeader();
        if (listHeaders == null || listHeaders.isEmpty()) {
            return null;
        }
        Map<String, String> headers = new HashMap<>();
        for (ConfigFileJson.WebsiteLink.Header h : listHeaders) {
            if (!Boolean.valueOf(h.getValue())) {
                continue;
            }
            switch (h.getKey()){
                case "authorization" :
                    String auth = "";
                    for (ConfigFileJson.WebsiteLink.Parameter p : w.getListParameter()) {
                        if (p == w.getListParameter().get(w.getListParameter().size() - 1)) {
                            auth += p.getValue();
                            continue;
                        }
                        auth += p.getValue() + ":";
                    }
                    String base64Creds = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
                    headers.put(h.getKey(), "base" + base64Creds);
                    break;
                case  "useragent" :
                    headers.put(h.getKey(), String.valueOf(h.getValue()));
            }
        }
        return headers;
    }

    //Check link
    public static boolean isUrlValidFormat(String url) {
        String regex = "^(http|Http|https?|Https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return url.matches(regex);
    }

    /* *******************************************************************************
     * Area : Function - Public - Get String Result
     **********************************************************************************/
    //Method lấy response từ list response
    public static String getResponseString(Response response, ConfigFileJson.WebsiteLink w, String messageCode) {
        StringBuilder respString = new StringBuilder();
        respString.append("--- Result Response ---" + "\n");
        respString.append("Websites : " + w.getName() + "\n");
        respString.append("URL Request : " + response.url+ "\n");
        respString.append("Request Method : " + response.method + "\n");
        respString.append("Response Code : " + response.code + "\n");
        respString.append("Message Code : " + messageCode + "\n");
        if (!response.message.isEmpty()) {
            respString.append("Response Message : " + response.message + "\n");
        }
        respString.append("--- End Response ---");
        return String.valueOf(respString);
    }

    //In ra khi thuoc tinh method,bodyType bi loi
    public static String outputError(ConfigFileJson.WebsiteLink w) {
        StringBuilder errorString = new StringBuilder();
        errorString.append("Website : " + w.getName() + "\n");
        errorString.append("Method : " + w.getMethod() + "\n");
        errorString.append("BodyType : " + w.getBodyType() + "\n");
        errorString.append("Link : " + w.getUrl() + "\n");
        return String.valueOf(errorString);
    }
}
