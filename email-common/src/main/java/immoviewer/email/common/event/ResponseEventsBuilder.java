package immoviewer.email.common.event;

import java.util.ArrayList;
import java.util.List;


public abstract class ResponseEventsBuilder<T> {
    private List<T> receivedEvents;

    public ResponseEventsBuilder(List<T> receivedEvents) {
        this.receivedEvents = receivedEvents;
    }

    public abstract boolean hasMessageId(T event);
    public abstract MailEventResponse buildEvent(T event);


    public List<MailEventResponse> build(){
        List<MailEventResponse> events=new ArrayList<>();
        for (T event : receivedEvents) {
            if(hasMessageId(event)){
                MailEventResponse mailEventResponse = buildEvent(event);
                events.add(mailEventResponse);
            }
        }
        return events;
    }
}
