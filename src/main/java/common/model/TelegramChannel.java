package common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TelegramChannel {
    @JsonProperty("idChannel")
    private String idChannel;
    @JsonProperty("websitesLinks")
    private List<WebsiteLink> listWebLink;
    @JsonProperty("scheduleRequest")
    private ScheduleRequest scheduleRequest;

    public ScheduleRequest getScheduleRequest() {
        return scheduleRequest;
    }

    public String getIdChannel() {
        return idChannel;
    }

    public List<WebsiteLink> getListWebLink() {
        return listWebLink;
    }

    public static class ScheduleRequest{
        @JsonProperty("period")
        private int period;

        public int getPeriod() {
            return period;
        }
    }

    public static class WebsiteLink {
        @JsonProperty("name")
        private String name;
        @JsonProperty("link")
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}
