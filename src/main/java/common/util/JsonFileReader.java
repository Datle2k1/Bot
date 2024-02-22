package common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.model.TelegramChannel;
import common.model.TelegramChannelWrapper;

import java.io.File;
import java.io.IOException;

public class JsonFileReader {

    public static String getPathFileJson() {
        ClassLoader classLoader = JsonFileReader.class.getClassLoader();
        File file = new File(classLoader.getResource("Websites.json").getFile());
        String jsonFilePath = file.getAbsolutePath();
        return jsonFilePath;
    }

    public static TelegramChannel readFromJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(getPathFileJson());
            TelegramChannelWrapper wrapper = objectMapper.readValue(file, TelegramChannelWrapper.class);
            TelegramChannel telegramChannel = wrapper.getTelegramChannel();
            return telegramChannel;
        } catch (IOException e) {
            // Thêm xử lý cho IOException
            e.printStackTrace();
            throw new RuntimeException("Error reading from JSON file", e);
        }
    }
}

