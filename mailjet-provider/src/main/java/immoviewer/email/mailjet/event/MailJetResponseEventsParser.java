package immoviewer.email.mailjet.event;

import com.fasterxml.jackson.databind.JsonNode;
import immoviewer.email.common.event.ResponseEventsParser;
import immoviewer.email.common.exception.MailEventException;
import immoviewer.email.mailjet.model.MailJetEvent;

import java.util.List;

/**
 * Created by didac on 12.05.16.
 */
public class MailJetResponseEventsParser extends ResponseEventsParser<MailJetEvent> {

    public MailJetResponseEventsParser(Object eventsToParse) {
        super(eventsToParse, MailJetEvent.class);
    }

    @Override
    public List<MailJetEvent> parse() {
        throwsIfNull(getEventsToParse());

        if(getEventsToParse() instanceof JsonNode){
            JsonNode jsonEvent = (JsonNode) getEventsToParse();
            List<MailJetEvent> mailJetEvents = parseJson(jsonEvent);
            throwsIfListEmpty(mailJetEvents);
            return mailJetEvents;
        }

        throw new MailEventException("Expected JsonNode element, but found something else. Class="+ getEventsToParse().getClass() + " Not supported");
    }
}
