package common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import task.api.request.RequestBody;
import task.api.request.RequestMethod;

import java.util.List;
import java.util.Map;

public class ConfigFileJson {
    @JsonProperty("idChannel")
    private String idChannel;
    @JsonProperty("websitesLinks")
    private List<WebsiteLink> listWebLink;
    @JsonProperty("request")
    private Request reqeust;

    public Request getRequest() {
        return reqeust;
    }

    public String getIdChannel() {
        return idChannel;
    }

    public List<WebsiteLink> getListWebLink() {
        return listWebLink;
    }

    public static class Request{
        @JsonProperty("timeout")
        private int timeout;
        @JsonProperty("schedule")
        private Schedule schedule;
        public int getTimeout() {
            return timeout;
        }

        public Schedule getSchedule() {
            return schedule;
        }

        public static class Schedule{
            @JsonProperty("period")
            private int period;
            public int getPeriod() {
                return period;
            }
        }
    }

    @JsonFormat()
    public static class WebsiteLink {
        @JsonProperty("name")
        private String name;
        @JsonProperty("link")
        private String url;
        @JsonProperty("method")
        private String method;
        @JsonProperty("bodyType")
        private String bodyType;
        @JsonProperty("headers")
        private List<Header> listHeader;
        @JsonProperty("parameters")
        private List<Parameter> listParameter;
        public List<Header> getListHeader() {
            return listHeader;
        }
        public String getMethod() {
            return method;
        }

        public List<Parameter> getListParameter() {
            return listParameter;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public String getBodyType() {
            return bodyType;
        }
        public static class Header{
            @JsonProperty("key")
            private String key;
            @JsonProperty("value")
            private String value;

            public String getKey() {
                return key;
            }
            public String getValue() {
                return value;
            }
        }

        public static class Parameter{
            @JsonProperty("key")
            private String key;
            @JsonProperty("value")
            private String value;

            public String getKey() {
                return key;
            }
            public String getValue() {
                return value;
            }
        }
    }
}
