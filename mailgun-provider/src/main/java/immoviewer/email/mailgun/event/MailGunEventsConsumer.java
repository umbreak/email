package immoviewer.email.mailgun.event;

import immoviewer.email.common.event.MailEventResponse;
import immoviewer.email.common.event.MailEventsConsumer;

import java.util.List;


public class MailGunEventsConsumer implements MailEventsConsumer {

    @Override
    public List<MailEventResponse> fetchEvents(Object receivedEvent) {
        return new MailGunResponseEventsBuilder(receivedEvent).build();
    }
}

