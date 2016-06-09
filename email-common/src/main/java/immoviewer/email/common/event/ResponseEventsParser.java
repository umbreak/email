package immoviewer.email.common.event;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import immoviewer.email.common.exception.MailEventException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class ResponseEventsParser<T> {

    private final static ObjectMapper mapper = new ObjectMapper();

    private final Object eventsToParse;

    private final Class<T> clazz;

    public ResponseEventsParser(Object eventsToParse, Class<T> clazz) {
        this.clazz = clazz;
        this.eventsToParse = eventsToParse;
    }

    public abstract List<T> parse();

    public List<T> parseJson(JsonNode jsonEvents){
        String stringReceivedEvents = jsonEvents.toString();
        try {
            List<T> events = jsonEvents.isArray() ?
                    parseArrayEvent(stringReceivedEvents) :
                    parseSingleEventAndCreateList(stringReceivedEvents);

            return events;

        } catch (IOException ex) {
            throw new MailEventException("Parsing exception. Event="+ stringReceivedEvents, ex);
        }
    }

    private List<T> parseArrayEvent(String jsonString) throws IOException {
        JavaType javaType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return mapper.readValue(jsonString, javaType);
    }
    private List<T> parseSingleEventAndCreateList(String jsonString) throws IOException {
        T ummarshalled = mapper.readValue(jsonString, clazz);
        return Arrays.asList(ummarshalled);
    }

    public void throwsIfNull(Object receivedEvent){
        if(receivedEvent == null)
            throw new MailEventException("null received event from the webhooks for class="+this.getClass()+". Maybe the catching of the event was wrong? ");
    }

    public void throwsIfListEmpty(List events){
        if(events == null || events.isEmpty())
            throw new MailEventException("Unmarshalling from JSON returned a null or empty List. Message=" +  getEventsToParse());
    }

    public Object getEventsToParse() {
        return eventsToParse;
    }
}
