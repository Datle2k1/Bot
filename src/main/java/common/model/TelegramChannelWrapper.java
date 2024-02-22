package common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TelegramChannelWrapper {
    @JsonProperty("telegramChannel")
    private TelegramChannel telegramChannel;
    public TelegramChannel getTelegramChannel() {
        return telegramChannel;
    }
}