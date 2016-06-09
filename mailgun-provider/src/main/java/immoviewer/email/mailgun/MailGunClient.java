package immoviewer.email.mailgun;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.multipart.FormDataMultiPart;
import immoviewer.email.common.exception.MailException;
import immoviewer.email.common.exception.MailSendException;
import immoviewer.email.common.model.Token;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Created by didac on 12.05.16.
 */
public class MailGunClient extends RESTConnector {

    private final static String URL="https://api.mailgun.net/v3/{DOMAIN}/";
    private final ObjectMapper mapper;


    public MailGunClient(String domain, Token token) {
        super(getURIFromDomain(domain),token.getToken(),token.getTokenSecret());
        mapper=new ObjectMapper();
    }

    public MailGunResponse post(FormDataMultiPart request) throws MailSendException{
        final UriBuilder uriBuilder = generateUrl();
        try {
            ClientResponse response = getWebResource(uriBuilder).type(MediaType.MULTIPART_FORM_DATA_TYPE).
                    post(ClientResponse.class, request);
            if (Objects.equals(response.getClientResponseStatus(), ClientResponse.Status.OK)) {
                return convertResponse(response);
            } else {
                throw new MailSendException("Error code=" + response.getClientResponseStatus());
            }
        }catch(Exception e){
            throw new MailSendException("Error posting request="  + request +", to url=" + uriBuilder.toString(),e);
        }
    }

    private UriBuilder generateUrl(){
        return UriBuilder.fromUri(endpointUrl).path("messages");
    }

    private MailGunResponse convertResponse(ClientResponse response) throws IOException {
        String entityString = response.getEntity(String.class);
        return mapper.readValue(entityString, MailGunResponse.class);
    }


    private static URI getURIFromDomain(String domain){
        String urlString = URL.replace("{DOMAIN}", domain);
        try{
            return new URI(urlString);
        }catch(URISyntaxException ex){
            throw new MailException("MailGun client could not be created. Url not correct=" + urlString);
        }
    }
}
