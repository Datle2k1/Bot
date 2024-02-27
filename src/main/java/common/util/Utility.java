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
     * Area : Function - Public - CHECK,GET INFORMATION REQUEST,WEBSITE FROM FILE JSON
     * Information before Request : idChannel, timeout, period, link. method, bodyType.
     * Information Request :headers, parameters.
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

    //Kiem tra idChannel,timeout, period, listweblink.
    public static void checkInformationFromFIleJson(ConfigFileJson configFileJson) {
        if (configFileJson.getIdChannel().isEmpty()) {
            throw new RuntimeException("\nidChannel can't Empty");
        }
        if (configFileJson.getRequest().getSchedule().getPeriod() <= 0 || configFileJson.getRequest().getTimeout() <= 0) {
            throw new RuntimeException("\nperiod & timeout must > 0 ");
        }
        if (configFileJson.getListWebLink().isEmpty()) {
            throw new RuntimeException("\nList listWebLinks is Empty ");
        }
    }

    //Kiem tra bodyType
    public static Boolean checkBodyType(ConfigFileJson.WebsiteLink w) {
//        System.out.print("Check BodyType : ");
        if (!JSON.toString().equals(w.getBodyType()) && !DATA_FORM.toString().equals(w.getBodyType())) {
//           System.out.println("bodyType != JSON,DATA_FORM");
            return false;
        }
//        System.out.println("Checked");
        return true;
    }

    //Kiem tra method
    public static Boolean checkMethod(ConfigFileJson.WebsiteLink w) {
//        System.out.print("Check Method : ");
        for (RequestMethod m : RequestMethod.values()) {
            if (w.getMethod().equals(m.toString())) {
//                System.out.println("Checked");
                return true;
            }
        }
//        System.out.println("Error Method");
        return false;
    }

    //Kiem tra va lay Parameter neu co
    public static Map<String, String> getParameters(ConfigFileJson.WebsiteLink w) {
        List<ConfigFileJson.WebsiteLink.Parameter> listParameter = w.getListParameter();
//        System.out.print("Check ListParameter : ");
        if (listParameter == null || listParameter.isEmpty()) {
//            System.out.println("null or Empty");
            return null;
        }
//        System.out.println("Checked" );
        Map<String, String> parameters = new HashMap<>();
        for (ConfigFileJson.WebsiteLink.Parameter p : listParameter) {
            parameters.put(p.getKey(), p.getValue());
        }
        return parameters;
    }

    //Kiem tra va Lay Header neu co
    public static Map<String, String> getHeader(ConfigFileJson.WebsiteLink w) {
        List<ConfigFileJson.WebsiteLink.Header> listHeaders = w.getListHeader();
//        System.out.print("Check List Headers : ");
        if (listHeaders == null || listHeaders.isEmpty()) {
//            System.out.println("null or Empty");
            return null;
        }
//        System.out.println("Checked" );
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
        respString.append("URL Request : " + response.getUrl() + "\n");
        respString.append("Request Method : " + response.getMethod() + "\n");
        respString.append("Response Code : " + response.getCode() + "\n");
        respString.append("Message Code : " + messageCode + "\n");
        if (!response.getMessage().isEmpty()) {
            respString.append("Response Message : " + response.getMessage() + "\n");
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
