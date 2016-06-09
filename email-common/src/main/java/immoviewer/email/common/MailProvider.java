package immoviewer.email.common;

import com.google.common.base.Strings;
import immoviewer.email.common.delivery.MailDeliveryProducer;
import immoviewer.email.common.exception.MailSendException;
import immoviewer.email.common.event.MailEventResponse;
import immoviewer.email.common.model.MailProviderType;
import immoviewer.email.common.model.MailWrapper;
import immoviewer.email.common.event.MailEventsConsumer;

import java.util.List;

public class MailProvider{
    private final MailProviderType providerType;
    private final MailDeliveryProducer requestProducer;
    private final MailEventsConsumer webhooksConsumer;

    public MailProvider(MailProviderType providerType, MailDeliveryProducer requestProducer, MailEventsConsumer webhooksConsumer) {
        this.providerType = providerType;
        this.requestProducer = requestProducer;
        this.webhooksConsumer = webhooksConsumer;
    }

    public String send(MailWrapper mailContent) {
        RequestBuilder requestBuilder = requestProducer.generateRequestBuilder(mailContent);

        try {
            String callbackID = requestProducer.sendRequest(requestBuilder.build());
            return (Strings.isNullOrEmpty(callbackID)) ?
                    mailContent.getCallbackID() : callbackID;
        } catch (Exception e) {
            throw new MailSendException(errorInfo(mailContent) + e.getMessage(), e);
        }
    }

    public List<MailEventResponse> fetchEvents(Object events){
        return webhooksConsumer.fetchEvents(events);
    }

    private String errorInfo(MailWrapper wrapper) {
        return "Error from=" + wrapper.getFrom() + " to=" + wrapper.getTo() + " ";
    }

    public MailProviderType getProviderType() {
        return providerType;
    }

}
