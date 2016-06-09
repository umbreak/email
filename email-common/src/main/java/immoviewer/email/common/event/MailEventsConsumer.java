package immoviewer.email.common.event;

import java.util.List;


public interface MailEventsConsumer {
    List<MailEventResponse> fetchEvents(Object events);
}
