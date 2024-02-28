package common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ConfigFileJson {
    /* **********************************************************************
     * Area : Variable : idChannel, listWeblink, reqeust
     ********************************************************************** */
    @JsonProperty("idChannel")
    private String idChannel;
    @JsonProperty("token")
    private String token;
    @JsonProperty("websitesLinks")
    private List<WebsiteLink> listWebLink;
    @JsonProperty("request")
    private Request request;

    /* **********************************************************************
     * Area : Getter
     ********************************************************************** */

    public String getToken() {
        return token;
    }

    public Request getRequest() {
        return request;
    }

    public String getIdChannel() {
        return idChannel;
    }

    public List<WebsiteLink> getListWebLink() {
        return listWebLink;
    }

    /* **********************************************************************
     * Area : Class - Public : Request
     ********************************************************************** */
    public static class Request{
        /* **********************************************************************
         * Area : Variable
         ********************************************************************** */
        @JsonProperty("timeout")
        private int timeout;
        @JsonProperty("schedule")
        private Schedule schedule;
        /* **********************************************************************
         * Area : Getter
         ********************************************************************** */
        public int getTimeout() {
            return timeout;
        }

        public Schedule getSchedule() {
            return schedule;
        }

        /* **********************************************************************
         * Area : Class - Public : Schedule
         ********************************************************************** */
        public static class Schedule{
            /* **********************************************************************
             * Area : Variable
             ********************************************************************** */
            @JsonProperty("period")
            private int period;
            /* **********************************************************************
             * Area : Getter
             ********************************************************************** */
            public int getPeriod() {
                return period;
            }
        }
    }

    /* **********************************************************************
     * Area : Class - Public : WebsiteLink
     ********************************************************************** */
    public static class WebsiteLink {
        /* **********************************************************************
         * Area : Variable
         ********************************************************************** */
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
        /* **********************************************************************
         * Area : Getter
         ********************************************************************** */
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

        /* **********************************************************************
         * Area : Class - Public : Header
         ********************************************************************** */
        public static class Header{
            /* **********************************************************************
             * Area : Variable
             ********************************************************************** */
            @JsonProperty("key")
            private String key;
            @JsonProperty("value")
            private String value;
            /* **********************************************************************
             * Area : Getter
             ********************************************************************** */
            public String getKey() {
                return key;
            }
            public String getValue() {
                return value;
            }
        }

        /* **********************************************************************
         * Area : Class - Public : Parameter
         ********************************************************************** */
        public static class Parameter{
            /* **********************************************************************
             * Area : Variable
             ********************************************************************** */
            @JsonProperty("key")
            private String key;
            @JsonProperty("value")
            private String value;
            /* **********************************************************************
             * Area : Getter
             ********************************************************************** */
            public String getKey() {
                return key;
            }
            public String getValue() {
                return value;
            }
        }
    }
}
