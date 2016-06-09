package immoviewer.email.mailjet;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import immoviewer.email.common.RequestBuilder;
import immoviewer.email.common.delivery.MailDeliveryProducer;
import immoviewer.email.common.exception.MailSendException;
import immoviewer.email.common.model.MailWrapper;
import immoviewer.email.common.model.Token;


public class MailJetDeliveryProducer implements MailDeliveryProducer<MailjetRequest> {
    private final MailjetClient client;

    public MailJetDeliveryProducer(Token token) {
        this.client = new MailjetClient(token.getToken(), token.getTokenSecret());
    }

    @Override
    public RequestBuilder<MailjetRequest> generateRequestBuilder(MailWrapper wrapper) {
        return new MailJetRequestBuilder(wrapper);
    }

    @Override
    public String sendRequest(MailjetRequest request) throws Exception {
        MailjetResponse response = client.post(request);
        if (response.getStatus() != 200)
            throw new MailSendException("status=" + response.getStatus() + " data=" + response.getData());
        return "";
    }

}
