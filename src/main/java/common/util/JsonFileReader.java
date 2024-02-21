package common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.model.WebsiteLink;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonFileReader {

    public static List<WebsiteLink> readFIle() {
        ClassLoader classLoader = JsonFileReader.class.getClassLoader();
        File file = new File(classLoader.getResource("Websites.json").getFile());
        String jsonFilePath = file.getAbsolutePath();
        List<WebsiteLink> websiteLinks = readFromJsonFile(jsonFilePath);
        return websiteLinks;
    }

    private static List<WebsiteLink> readFromJsonFile(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return Arrays.asList(objectMapper.readValue(new File(jsonFilePath), WebsiteLink[].class));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

