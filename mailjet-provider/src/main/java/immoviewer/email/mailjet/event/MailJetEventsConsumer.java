package immoviewer.email.mailjet.event;

import immoviewer.email.common.event.MailEventResponse;
import immoviewer.email.common.event.MailEventsConsumer;
import immoviewer.email.mailjet.event.MailJetResponseEventsBuilder;

import java.util.List;

public class MailJetEventsConsumer implements MailEventsConsumer{

    @Override
    public List<MailEventResponse> fetchEvents(Object receivedEvent) {
        return new MailJetResponseEventsBuilder(receivedEvent).build();
    }
}
