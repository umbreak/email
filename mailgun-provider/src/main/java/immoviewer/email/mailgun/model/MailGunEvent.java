package immoviewer.email.mailgun.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import immoviewer.email.common.model.MailStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * Created by didac on 05.10.15.
 */
public class MailGunEvent {
    public Status event;
    public Double timestamp;
    public String[] tags;
    public String[] campaigns;
    @JsonProperty("user-variables")
    public Map userVariables;
    public Map flags;
    public Routes[] routes;
    public Message message;
    public String recipient;
    public String method;

    public Envelope envelope;
    @JsonProperty("delivery-status")
    public DeliveryStatus deliveryStatus;
    public String severity;
    public String reason;

    public Geolocation geolocation;
    public String ip;
    @JsonProperty("client-info")
    public ClientInfo clientInfo;
    public String url;

    public Storage storage;

    public MailStatus getMailStatus() {
        if(event == null) return null;
        MailStatus status=event.getMailStatus();
        if(status == MailStatus.bounce){
            if (Objects.equals(reason, "bounce") || Objects.equals(reason, "hardfail")) {
                status = MailStatus.bounce;
            } else {
                status = MailStatus.blocked;
            }
        }
        return status;
    }

    public static class Routes{

        public int priority;
        public String expression;
        public String description;
        public String[] actions;

        @Override
        public String toString() {
            return "Routes{" +
                    "priority=" + priority +
                    ", expression='" + expression + '\'' +
                    ", description='" + description + '\'' +
                    ", actions=" + Arrays.toString(actions) +
                    '}';
        }
    }

    public static class Message{
        public static class Headers {
            public String to;
            @JsonProperty("message-id")
            public String messageId;
            public String from;
            public String subject;

            @Override
            public String toString() {
                return "Headers{" +
                        "to='" + to + '\'' +
                        ", messageId='" + messageId + '\'' +
                        ", from='" + from + '\'' +
                        ", subject='" + subject + '\'' +
                        '}';
            }
        }
        public Headers headers;
        public String[] attachments;
        public String[] recipients;
        public int size;

        @Override
        public String toString() {
            return "Message{" +
                    "headers=" + headers +
                    ", attachments=" + Arrays.toString(attachments) +
                    ", recipients=" + Arrays.toString(recipients) +
                    ", size=" + size +
                    '}';
        }
    }


    public enum Status {
        accepted(MailStatus.queued),
        rejected(MailStatus.blocked),
        delivered(MailStatus.arrived),
        failed(MailStatus.bounce),
        opened(MailStatus.opened),
        clicked(MailStatus.clicked),
        unsubscribed(MailStatus.unknown),
        complained(MailStatus.spam),
        stored(MailStatus.unknown);

        private final MailStatus mailStatus;

        Status(MailStatus mailStatus) {
            this.mailStatus = mailStatus;
        }

        public MailStatus getMailStatus() {
            return mailStatus;
        }
    }

    public static class Envelope {
        public String transport;
        public String sender;
        @JsonProperty("sending-ip")
        public String sendingIp;


        @Override
        public String toString() {
            return "Envelope{" +
                    "transport='" + transport + '\'' +
                    ", sender='" + sender + '\'' +
                    ", sendingIp='" + sendingIp + '\'' +
                    '}';
        }
    }

    public static class DeliveryStatus {
        public String message;
        public int code;
        public String description;

        @Override
        public String toString() {
            return "DeliveryStatus{" +
                    "message='" + message + '\'' +
                    ", code=" + code +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    public static class Geolocation {
        public String country;
        public String region;
        public String city;

        @Override
        public String toString() {
            return "Geolocation{" +
                    "country='" + country + '\'' +
                    ", region='" + region + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }

    public static class ClientInfo {

        @JsonProperty("client-type")
        public String clientType;
        @JsonProperty("client-os")
        public String clientOS;
        @JsonProperty("device-type")
        public String deviceType;
        @JsonProperty("client-name")
        public String clientName;
        @JsonProperty("user-agent")
        public String userAgent;

        @Override
        public String toString() {
            return "ClientInfo{" +
                    "clientType='" + clientType + '\'' +
                    ", clientOS='" + clientOS + '\'' +
                    ", deviceType='" + deviceType + '\'' +
                    ", clientName='" + clientName + '\'' +
                    ", userAgent='" + userAgent + '\'' +
                    '}';
        }
    }

    public static class Storage {
        public String url;
        public String key;

        @Override
        public String toString() {
            return "Storage{" +
                    "url='" + url + '\'' +
                    ", key='" + key + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MailGunEvent{" +
                "event=" + event +
                ", timestamp=" + timestamp +
                ", tags=" + Arrays.toString(tags) +
                ", campaigns=" + Arrays.toString(campaigns) +
                ", userVariables=" + userVariables +
                ", flags=" + flags +
                ", routes=" + Arrays.toString(routes) +
                ", message=" + message +
                ", recipient='" + recipient + '\'' +
                ", method='" + method + '\'' +
                ", envelope=" + envelope +
                ", deliveryStatus=" + deliveryStatus +
                ", severity='" + severity + '\'' +
                ", reason='" + reason + '\'' +
                ", geolocation=" + geolocation +
                ", ip='" + ip + '\'' +
                ", clientInfo=" + clientInfo +
                ", url='" + url + '\'' +
                ", storage=" + storage +
                '}';
    }
}

