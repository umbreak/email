package immoviewer.email.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import immoviewer.email.common.MailProvider;
import immoviewer.email.common.event.MailEventResponse;
import immoviewer.email.common.model.MailProviderType;
import immoviewer.email.common.model.MailStatus;
import immoviewer.email.common.model.Token;
import immoviewer.email.factory.utils.Constants;
import immoviewer.email.factory.utils.FetchResources;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class MailEventsTest {
    private MailProvider mailjetProvider;

    private MailProvider mailgubProvider;

    private ObjectMapper mapper;

    @Before
    public void init(){
        Token mailjetToken = new Token(Constants.MailJet_KEY, Constants.MailJet_SECRET);
        mailjetProvider=MailProviderCreator.
                create(MailProviderType.MailJet,mailjetToken,null);

        Token mailgunToken = new Token(Constants.MailGun_KEY, Constants.MailJet_SECRET);
        mailgubProvider=MailProviderCreator.
                create(MailProviderType.MailGun, mailgunToken, Constants.MailGun_DOMAIN);

        mapper = new ObjectMapper();
    }

    @Test
    public void sentEvent() throws IOException, URISyntaxException {
        sentMailjetEvent(mailjetProvider, MailProviderType.MailJet);
        sentMailjetEvent(mailgubProvider, MailProviderType.MailGun);
    }

    @Test
    public void openEvent() throws IOException, URISyntaxException {
        openedMailjetEvent(mailjetProvider, MailProviderType.MailJet);
        openedMailjetEvent(mailgubProvider, MailProviderType.MailGun);
    }

    @Test
    public void clickedMailjetEvent() throws IOException, URISyntaxException {
        clickedMailjetEvent(mailjetProvider, MailProviderType.MailJet);
        clickedMailjetEvent(mailgubProvider, MailProviderType.MailGun);
    }

    @Test
    public void failMailjetEvent() throws IOException, URISyntaxException {
        failMailjetEvent(mailjetProvider, MailProviderType.MailJet);
        failMailjetEvent(mailgubProvider, MailProviderType.MailGun);
    }



    //Mailjet tests ------------------------------------------------------------------------
    private void sentMailjetEvent(MailProvider provider, MailProviderType type) throws IOException, URISyntaxException {
        String eventResponse= FetchResources.getStringFromResources(type.name().toLowerCase() +"/sent.json");
        JsonNode json=mapper.readTree(eventResponse);
        List<MailEventResponse> mailEventResponses = provider.fetchEvents(json);
        Assert.assertEquals(mailEventResponses.size(), 1);
        MailEventResponse realResponse = mailEventResponses.get(0);

        MailEventResponse expectedResponse = new MailEventResponse.Builder("dd6eb9d0-bd54-4edb-8b20-9a879e57529d", "didac.montero@clipnow.com")
                .status(MailStatus.arrived)
                .date(realResponse.date).provider(type).build();
        Assert.assertEquals(realResponse,expectedResponse);
    }

    private void failMailjetEvent(MailProvider provider, MailProviderType type) throws IOException, URISyntaxException {
        String eventResponse= FetchResources.getStringFromResources(type.name().toLowerCase() +"/fail.json");
        JsonNode json=mapper.readTree(eventResponse);
        List<MailEventResponse> mailEventResponses = provider.fetchEvents(json);
        Assert.assertEquals(mailEventResponses.size(), 1);
        MailEventResponse realResponse = mailEventResponses.get(0);

        MailEventResponse expectedResponse = new MailEventResponse.Builder("dd6eb9d0-bd54-4edb-8b20-9a879e57529d", "didac.montero@clipnow.com")
                .status(MailStatus.bounce).error("permanent: bounce")
                .date(realResponse.date).provider(type).build();
        Assert.assertEquals(realResponse,expectedResponse);
    }


    private void openedMailjetEvent(MailProvider provider, MailProviderType type) throws IOException, URISyntaxException {
        String eventResponse= FetchResources.getStringFromResources(type.name().toLowerCase() +"/open.json");
        JsonNode json=mapper.readTree(eventResponse);
        List<MailEventResponse> mailEventResponses = provider.fetchEvents(json);
        Assert.assertEquals(mailEventResponses.size(), 1);
        MailEventResponse realResponse = mailEventResponses.get(0);

        MailEventResponse expectedResponse = new MailEventResponse.Builder("dd6eb9d0-bd54-4edb-8b20-9a879e57529d", "didac.montero@clipnow.com")
                .status(MailStatus.opened)
                .date(realResponse.date).provider(type).build();
        Assert.assertEquals(realResponse,expectedResponse);
    }

    private void clickedMailjetEvent(MailProvider provider, MailProviderType type) throws IOException, URISyntaxException {
        String eventResponse= FetchResources.getStringFromResources(type.name().toLowerCase() +"/click.json");
        JsonNode json=mapper.readTree(eventResponse);
        List<MailEventResponse> mailEventResponses = provider.fetchEvents(json);
        Assert.assertEquals(mailEventResponses.size(), 1);
        MailEventResponse realResponse = mailEventResponses.get(0);

        MailEventResponse expectedResponse = new MailEventResponse.Builder("dd6eb9d0-bd54-4edb-8b20-9a879e57529d", "didac.montero@clipnow.com")
                .status(MailStatus.clicked)
                .url("http://google.com")
                .date(realResponse.date).provider(type).build();
        Assert.assertEquals(realResponse,expectedResponse);
    }

    //Mailgun tests ------------------------------------------------------------------------



}
