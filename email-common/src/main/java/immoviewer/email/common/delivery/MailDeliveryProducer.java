package immoviewer.email.common.delivery;

import immoviewer.email.common.RequestBuilder;
import immoviewer.email.common.model.MailWrapper;


public interface MailDeliveryProducer<T> {
    RequestBuilder<T> generateRequestBuilder(MailWrapper wrapper);
    String sendRequest(T request) throws Exception;
}
