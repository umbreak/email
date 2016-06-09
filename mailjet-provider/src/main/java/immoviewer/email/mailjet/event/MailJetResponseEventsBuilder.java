package immoviewer.email.mailjet.event;

import com.google.common.base.Strings;
import immoviewer.email.common.event.MailEventResponse;
import immoviewer.email.common.event.ResponseEventsBuilder;
import immoviewer.email.common.model.MailProviderType;
import immoviewer.email.mailjet.model.MailJetEvent;

import java.util.Date;


public class MailJetResponseEventsBuilder extends ResponseEventsBuilder<MailJetEvent> {


    public MailJetResponseEventsBuilder(Object receivedEvents) {
        super(new MailJetResponseEventsParser(receivedEvents).parse());
    }

    @Override
    public boolean hasMessageId(MailJetEvent event){
        return !Strings.isNullOrEmpty(event.CustomID);
    }

    @Override
    public MailEventResponse buildEvent(MailJetEvent event){
        return new MailEventResponse.Builder(event.CustomID, event.email)
                .date(new Date())
                .url(event.url)
                .status(event.event.getMailStatus())
                .error(event.error)
                .provider(MailProviderType.MailJet).build();
    }
}
