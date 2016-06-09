package immoviewer.email.mailgun;

import com.google.common.base.Strings;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import immoviewer.email.common.model.MailWrapper;
import immoviewer.email.common.RequestBuilder;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class MailGunRequestBuilder extends RequestBuilder<FormDataMultiPart> {

    private final FormDataMultiPart request;

    public MailGunRequestBuilder(MailWrapper wrapper) {
        super(wrapper);
        request= new FormDataMultiPart();
    }

    public RequestBuilder addProperty(RequestProperties propertyKey, String propertyValue){
        if(propertyKey == RequestProperties.CUSTOM_ID) return this;
        String mailJetPropertyKey = mapToMailGunProperty(propertyKey);
        addProperty(mailJetPropertyKey,propertyValue);
        return this;
    }

    public RequestBuilder addContactProperty(RequestProperties propertyKey, String name, String email){
        String mailgunMapProperty = mapToMailGunProperty(propertyKey);
        addContactProperty(mailgunMapProperty,name,email);
        return this;
    }

    private String mapToMailGunProperty(RequestProperties property){
        switch (property){
            case SUBJECT: return "subject";
            case CC: return "cc";
            case HTML: return "html";
            case TEXT: return "text";
            case TO: return "to";
            case FROM: return "from";
            case REPLY_TO: return "h:Reply-To";
        }
        return null;
    }

    public FormDataMultiPart getRequest() {
        //Add the tracking properties to the email
        request.field("o:tracking","yes");
        request.field("o:tracking-clicks","yes");
        request.field("o:tracking-opens","yes");
        request.field("o:deliverytime",dateRFC2822());
        return request;
    }

    private String dateRFC2822(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        return formatter.format(date);
    }

    private RequestBuilder addContactProperty(String propertyKey, String name, String email) {
        if (!Strings.isNullOrEmpty(email)) {
            request.field(propertyKey, buildNameAndEmail(name, email));
        }
        return this;
    }


    public RequestBuilder addAttachmentsProperty(List<File> attachments) {
        if (attachments != null && !attachments.isEmpty()) {
            for (File attachment : attachments) {
                FileDataBodyPart bodyPart = prepareAttachmentForMailGun(attachment);
                request.bodyPart(bodyPart);
            }
        }
        return this;
    }

    private FileDataBodyPart prepareAttachmentForMailGun(File attachment) {
        return new FileDataBodyPart("attachment", attachment, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }

    private RequestBuilder addProperty(String propertyKey, String propertyValue) {
        if (!Strings.isNullOrEmpty(propertyValue)) {
            request.field(propertyKey, propertyValue);
        }
        return this;
    }
}
