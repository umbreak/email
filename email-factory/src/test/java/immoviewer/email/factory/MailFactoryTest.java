package immoviewer.email.factory;

import immoviewer.email.common.MailProvider;
import immoviewer.email.common.model.MailProviderType;
import immoviewer.email.common.model.MailWrapper;
import immoviewer.email.common.model.Token;
import immoviewer.email.factory.utils.Constants;
import immoviewer.email.factory.utils.FetchResources;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class MailFactoryTest {

    private final static String from="info@immoviewer.com";
    private final static String to="didac.montero.mendez@gmail.com";
    private final static String CC="umbreak@gmail.com";
    private final static String replyTo="didac.montero@clipnow.com";

    private MailProvider mailjetProvider;

    private MailProvider mailgubProvider;



    @Before
    public void init(){
        Token mailjetToken = new Token(Constants.MailJet_KEY, Constants.MailJet_SECRET);
        mailjetProvider=MailProviderCreator.
                create(MailProviderType.MailJet,mailjetToken,null);

        Token mailgunToken = new Token(Constants.MailGun_KEY, Constants.MailGun_SECRET);
        mailgubProvider=MailProviderCreator.
                create(MailProviderType.MailGun, mailgunToken, Constants.MailGun_DOMAIN);
    }

    @Test
    public void checkGetProvider(){
        Assert.assertEquals(mailgubProvider.getProviderType(), MailProviderType.MailGun);
        //Assert.assertEquals(mailjetProvider.getProviderType(), MailProviderType.MailJet);
    }

    @Test
    public void sendBasicEmailThroughMaijet(){sendEmailBasic(mailjetProvider, MailProviderType.MailJet);}

    @Test
    public void sendBasicEmailThroughMailgun(){
        sendEmailBasic(mailgubProvider, MailProviderType.MailGun);
    }

    @Test
    public void sendEmailWithAttachmentsThroughMaijet() throws URISyntaxException {
        sendEmailWithAttachments(mailjetProvider, MailProviderType.MailJet);
    }

    @Test
    public void sendEmailWithAttachmentsThroughMailgun() throws URISyntaxException {
        sendEmailWithAttachments(mailgubProvider, MailProviderType.MailGun);
    }


    private void sendEmailBasic(MailProvider provider, MailProviderType providerType){
        String callbackID = UUID.randomUUID().toString();
        MailWrapper mailContent = new MailWrapper.Builder()
                .from(from)
                .fromName("Info Immoviewer")
                .to(to)
                .callbackID(callbackID)
                .subject("Test sendEmailBasic " + providerType)
                .replyTo(replyTo)
                .replyToName("Didac Montero")
                .htmlBody("<h1>Test with replyTo</h1><p>body test wuith reply to <b>sendEmailBasic</b><a href='http://google.com'>Link</a></p>").build();
        System.out.println(provider.send(mailContent));

    }

    private void sendEmailWithAttachments(MailProvider provider, MailProviderType providerType) throws URISyntaxException {
        List<File> attachments=getAttachmentsFromResources();
        String callbackID = UUID.randomUUID().toString();
        MailWrapper mailContent = new MailWrapper.Builder()
                .from(from)
                .fromName("Info Immoviewer")
                .to(to)
                .attachments(attachments)
                .callbackID(callbackID)
                .subject("Test sendEmailWithAttachments test" + providerType)
                .replyTo(replyTo)
                .replyToName("Didac Montero")
                .htmlBody("<h1>Test with replyTo</h1><p>body test wuith reply to <b>sendEmailWithAttachments</b></p>").build();
        System.out.println(provider.send(mailContent));

    }

    private List<File> getAttachmentsFromResources() throws URISyntaxException {
        return Arrays.asList(FetchResources.getFileFromResources("attachment1.png"),
                FetchResources.getFileFromResources("attachment2.txt"));
    }

}
