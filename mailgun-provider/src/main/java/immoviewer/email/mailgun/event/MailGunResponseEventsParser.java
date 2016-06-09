package immoviewer.email.mailgun.event;

import com.fasterxml.jackson.databind.JsonNode;
import immoviewer.email.common.event.ResponseEventsParser;
import immoviewer.email.common.exception.MailEventException;
import immoviewer.email.mailgun.model.MailGunEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by didac on 12.05.16.
 */
public class MailGunResponseEventsParser extends ResponseEventsParser<MailGunEvent> {

    public MailGunResponseEventsParser(Object eventsToParse) {
        super(eventsToParse, MailGunEvent.class);
    }

    @Override
    public List<MailGunEvent> parse() {
        throwsIfNull(getEventsToParse());
        List<MailGunEvent> mailGunEvents=null;
        boolean foundType=false;
        if(getEventsToParse() instanceof JsonNode){
            JsonNode jsonEvent = (JsonNode) getEventsToParse();
            mailGunEvents = parseJson(jsonEvent);
            foundType=true;
        }else if (getEventsToParse() instanceof Map){
            Map<String,String[]> mapData = (Map<String, String[]>) getEventsToParse();
            mailGunEvents = parseMap(mapData);
            foundType=true;
        }
        if(!foundType) {
            throw new MailEventException("Expected JsonNode/Map element, but found something else. Class=" + getEventsToParse().getClass() + " Not supported");
        }
        throwsIfListEmpty(mailGunEvents);
        return mailGunEvents;
    }

    public List<MailGunEvent> parseMap(Map<String,String[]> data){
        List<MailGunEvent> results = new ArrayList<>();

        MailGunEvent event = new MailGunEvent();
        if(data != null){
            if(data.containsKey("country")){
                String[] countries = data.get("country");
                if(countries.length > 0){
                    if(event.geolocation == null) event.geolocation = new MailGunEvent.Geolocation();
                    event.geolocation.country = countries[0];
                }
            }
            if(data.containsKey("city")){
                String[] cities = data.get("city");
                if(cities.length > 0){
                    if(event.geolocation == null) event.geolocation = new MailGunEvent.Geolocation();
                    event.geolocation.city = cities[0];
                }
            }
            if(data.containsKey("region")){
                String[] regions = data.get("region");
                if(regions.length > 0){
                    if(event.geolocation == null) event.geolocation = new MailGunEvent.Geolocation();
                    event.geolocation.region = regions[0];
                }
            }

            if(data.containsKey("message-id")){
                String[] messageIDs = data.get("message-id");
                if(messageIDs.length > 0){
                    if(event.message == null) event.message = new MailGunEvent.Message();
                    if(event.message.headers == null) event.message.headers = new MailGunEvent.Message.Headers();
                    if(messageIDs[0].startsWith("<") && messageIDs[0].endsWith(">")) {
                        event.message.headers.messageId = messageIDs[0].substring(1, messageIDs[0].length() - 1);
                    }else{
                        event.message.headers.messageId = messageIDs[0];
                    }
                }
            }

            if(data.containsKey("Message-Id")){
                String[] messageIDs = data.get("Message-Id");
                if(messageIDs.length > 0){
                    if(event.message == null) event.message = new MailGunEvent.Message();
                    if(event.message.headers == null) event.message.headers = new MailGunEvent.Message.Headers();
                    if(messageIDs[0].startsWith("<") && messageIDs[0].endsWith(">")) {
                        event.message.headers.messageId = messageIDs[0].substring(1, messageIDs[0].length() - 1);
                    }else{
                        event.message.headers.messageId = messageIDs[0];
                    }
                }
            }

            if(data.containsKey("event")){
                String[] events = data.get("event");
                if(events.length > 0){
                    event.event = MailGunEvent.Status.valueOf(events[0]);
                }
            }

            if(data.containsKey("timestamp")){
                String[] timestamps = data.get("timestamp");
                if(timestamps.length > 0){
                    event.timestamp = Double.valueOf(timestamps[0]);
                }
            }

            if(data.containsKey("url")){
                String[] url = data.get("url");
                if(url.length > 0){
                    event.url = url[0];
                }
            }

            if(data.containsKey("ip")){
                String[] ips = data.get("ip");
                if(ips.length > 0){
                    event.ip = ips[0];
                }
            }


            if(data.containsKey("reason")){
                String[] reasons = data.get("reason");
                if(reasons.length > 0){
                    event.reason = reasons[0];
                }
            }

            if(data.containsKey("severity")){
                String[] values = data.get("severity");
                if(values.length > 0){
                    event.severity = values[0];
                }
            }

            if(data.containsKey("device-type")){
                String[] deviceType = data.get("device-type");
                if(deviceType.length > 0){
                    if(event.clientInfo == null) event.clientInfo = new MailGunEvent.ClientInfo();
                    event.clientInfo.deviceType = deviceType[0];
                }
            }


            if(data.containsKey("client-name")){
                String[] values = data.get("client-name");
                if(values.length > 0){
                    if(event.clientInfo == null) event.clientInfo = new MailGunEvent.ClientInfo();
                    event.clientInfo.clientName = values[0];
                }
            }

            if(data.containsKey("client-os")){
                String[] values = data.get("client-os");
                if(values.length > 0){
                    if(event.clientInfo == null) event.clientInfo = new MailGunEvent.ClientInfo();
                    event.clientInfo.clientOS = values[0];
                }
            }

            if(data.containsKey("user-agent")){
                String[] values = data.get("user-agent");
                if(values.length > 0){
                    if(event.clientInfo == null) event.clientInfo = new MailGunEvent.ClientInfo();
                    event.clientInfo.userAgent = values[0];
                }
            }

            if(data.containsKey("user-agent")){
                String[] values = data.get("user-agent");
                if(values.length > 0){
                    if(event.clientInfo == null) event.clientInfo = new MailGunEvent.ClientInfo();
                    event.clientInfo.userAgent = values[0];
                }
            }

            if(data.containsKey("recipient")){
                String[] values = data.get("recipient");
                if(values.length > 0){
                    event.recipient = values[0];
                }
            }
        }
        results.add(event);
        return results;

    }
}
