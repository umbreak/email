package immoviewer.email.mailjet;

import com.google.common.base.Strings;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.resource.Email;
import immoviewer.email.common.RequestBuilder;
import immoviewer.email.common.exception.MailException;
import immoviewer.email.common.model.MailWrapper;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by didac on 12.05.16.
 */
public final class MailJetRequestBuilder extends RequestBuilder<MailjetRequest> {

    private final MailjetRequest request;

    public MailJetRequestBuilder(MailWrapper wrapper) {
        super(wrapper);
        request = new MailjetRequest(Email.resource);
    }

    public RequestBuilder addProperty(RequestBuilder.RequestProperties propertyKey, String propertyValue) {
        String mailJetPropertyKey = mapToMailJetProperty(propertyKey);
        addProperty(mailJetPropertyKey, propertyValue);
        return this;
    }

    public RequestBuilder addContactProperty(RequestBuilder.RequestProperties propertyKey, String name, String email) {
        if (propertyKey == RequestProperties.REPLY_TO) {
            addReplyTo(name, email);
        } else if (propertyKey == RequestProperties.FROM) {
            addProperty(Email.FROMNAME, wrapper.getFromName());
            addProperty(Email.FROMEMAIL, wrapper.getFrom());
        } else {
            String mailJetPropertyKey = mapToMailJetProperty(propertyKey);
            addContactProperty(mailJetPropertyKey, name, email);
        }
        return this;
    }

    private String mapToMailJetProperty(RequestBuilder.RequestProperties property) {
        switch (property) {
            case SUBJECT:
                return Email.SUBJECT;
            case CC:
                return Email.CC;
            case CUSTOM_ID:
                return Email.MJCUSTOMID;
            case HTML:
                return Email.HTMLPART;
            case TEXT:
                return Email.TEXTPART;
            case TO:
                return Email.TO;
        }
        return null;
    }


    public MailjetRequest getRequest() {
        return request;
    }

    private RequestBuilder addReplyTo(String name, String email) {
        if (!Strings.isNullOrEmpty(email)) {
            JSONObject headerValue = new JSONObject().put("Reply-To", buildNameAndEmail(name, email));
            request.property(Email.HEADERS, headerValue);
        }
        return this;
    }

    private RequestBuilder addContactProperty(String propertyKey, String name, String email) {
        if (!Strings.isNullOrEmpty(email)) {
            request.property(propertyKey, buildNameAndEmail(name, email));
        }
        return this;
    }


    public RequestBuilder addAttachmentsProperty(List<File> attachments) {
        if (attachments != null && !attachments.isEmpty()) {
            List mailAttachments = new ArrayList<>();
            for (File attachment : attachments) {
                HashMap<String, String> mailjetAttach = prepareAttachmentForMailjet(attachment);
                if (!mailjetAttach.isEmpty()) {
                    mailAttachments.add(mailjetAttach);
                }
            }
            if (!mailAttachments.isEmpty()) {
                request.property(Email.ATTACHMENTS, mailAttachments);
            }
        }
        return this;
    }

    private HashMap<String, String> prepareAttachmentForMailjet(File attachment) {
        HashMap<String, String> mailjetAttach = new HashMap<>();
        try {
            mailjetAttach.put("Content-type", OCTET_SCREAM);
            mailjetAttach.put("Filename", attachment.getName());
            mailjetAttach.put("content", Base64.encodeBase64String(Files.readAllBytes(attachment.toPath())));
        } catch (IOException e) {
            throw new MailException("Error when trying to attach file=" + attachment, e);
        }
        return mailjetAttach;
    }

    private RequestBuilder addProperty(String propertyKey, String propertyValue) {
        if (!Strings.isNullOrEmpty(propertyValue)) {
            request.property(propertyKey, propertyValue);
        }
        return this;
    }


}
