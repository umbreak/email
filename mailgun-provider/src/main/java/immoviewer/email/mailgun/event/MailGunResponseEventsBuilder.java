package immoviewer.email.mailgun.event;

import com.google.common.base.Strings;
import immoviewer.email.common.event.MailEventResponse;
import immoviewer.email.common.event.ResponseEventsBuilder;
import immoviewer.email.common.model.MailProviderType;
import immoviewer.email.mailgun.model.MailGunEvent;

import java.util.Date;

public class MailGunResponseEventsBuilder extends ResponseEventsBuilder<MailGunEvent> {

    public MailGunResponseEventsBuilder(Object receivedEvents) {
        super(new MailGunResponseEventsParser(receivedEvents).parse());
    }


    @Override
    public boolean hasMessageId(MailGunEvent event){
        return event.message != null && event.message.headers != null
                && !Strings.isNullOrEmpty(event.message.headers.messageId);
    }

    @Override
    public MailEventResponse buildEvent(MailGunEvent event){
        String messageId = cleanMessageID(event.message.headers.messageId);
        String error=generateError(event);
        return new MailEventResponse.Builder(messageId, event.message.headers.to)
                .date(new Date())
                .url(event.url)
                .status(event.getMailStatus())
                .error(error)
                .provider(MailProviderType.MailGun).build();
    }

    private String generateError(MailGunEvent event){
        if(!Strings.isNullOrEmpty(event.severity) && !Strings.isNullOrEmpty(event.reason)) {
            return event.severity + ": " + event.reason;
        }
        return null;
    }

    private String cleanMessageID(String messageId){
        if(!Strings.isNullOrEmpty(messageId)){
            return messageId.replace("<","").replace(">","");
        }
        return messageId;
    }
}
