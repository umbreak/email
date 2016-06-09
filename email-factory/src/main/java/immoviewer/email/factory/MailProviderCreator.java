package immoviewer.email.factory;


import immoviewer.email.common.MailProvider;
import immoviewer.email.common.delivery.MailDeliveryProducer;
import immoviewer.email.common.event.MailEventsConsumer;
import immoviewer.email.common.exception.MailException;
import immoviewer.email.common.model.MailProviderType;
import immoviewer.email.common.model.Token;
import immoviewer.email.mailgun.MailGunDeliveryProducer;
import immoviewer.email.mailgun.event.MailGunEventsConsumer;
import immoviewer.email.mailjet.MailJetDeliveryProducer;
import immoviewer.email.mailjet.event.MailJetEventsConsumer;


public class MailProviderCreator {

    private static final String MAILPROVIDER_NOT_EXISTS="The email provider does not exist";

    public static MailProvider create(MailProviderType type, Token token, String domain){
        if(type == null) return null;
        MailDeliveryProducer deliveryProducer;
        MailEventsConsumer eventsConsumer;
        switch (type){
            case MailJet:
                deliveryProducer = new MailJetDeliveryProducer(token);
                eventsConsumer = new MailJetEventsConsumer();
                break;
            case MailGun:
                deliveryProducer = new MailGunDeliveryProducer(token,domain);
                eventsConsumer = new MailGunEventsConsumer();
                break;
            default:
                throw new MailException(MAILPROVIDER_NOT_EXISTS + " " + type);
        }
        return new MailProvider(type, deliveryProducer,eventsConsumer);
    }

}
