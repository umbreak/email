package immoviewer.email.mailgun;

import com.google.common.base.Strings;
import com.sun.jersey.multipart.FormDataMultiPart;
import immoviewer.email.common.RequestBuilder;
import immoviewer.email.common.delivery.MailDeliveryProducer;
import immoviewer.email.common.model.MailWrapper;
import immoviewer.email.common.model.Token;

public class MailGunDeliveryProducer implements MailDeliveryProducer<FormDataMultiPart> {
    private MailGunClient client;

    public MailGunDeliveryProducer(Token token, String domain) {
        this.client = new MailGunClient(domain, token);
    }

    @Override
    public RequestBuilder<FormDataMultiPart> generateRequestBuilder(MailWrapper mailContent) {
        return new MailGunRequestBuilder(mailContent);
    }

    @Override
    public String sendRequest(FormDataMultiPart request) throws Exception {
        MailGunResponse response = client.post(request);
        return cleanMessageID(response.getId());
    }


    private String cleanMessageID(String messageId) {
        if (!Strings.isNullOrEmpty(messageId)) {
            return messageId.replace("<", "").replace(">", "");
        }
        return messageId;
    }
}
