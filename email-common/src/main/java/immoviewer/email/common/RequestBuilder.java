package immoviewer.email.common;

import com.google.common.base.Strings;
import immoviewer.email.common.model.MailWrapper;

import java.io.File;
import java.util.List;

/**
 * Created by didac on 12.05.16.
 */
public abstract class RequestBuilder<T> {


    public static enum RequestProperties{
        FROM, TO, CC, REPLY_TO, SUBJECT, CUSTOM_ID,TEXT,HTML,ATTACHMENT
    }

    protected final static String OCTET_SCREAM = "application/octet-stream";

    protected final MailWrapper wrapper;

    public RequestBuilder(MailWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public abstract T getRequest();
    public abstract RequestBuilder addProperty(RequestProperties propertyKey, String propertyValue);
    public abstract RequestBuilder addContactProperty(RequestProperties propertyKey, String name, String email);
    public abstract RequestBuilder addAttachmentsProperty(List<File> attachments);

    public T build(){
        addContactProperty(RequestProperties.FROM, wrapper.getFromName(), wrapper.getFrom())
                .addContactProperty(RequestProperties.TO, wrapper.getToName(), wrapper.getTo())
                .addContactProperty(RequestProperties.CC, wrapper.getCcName(), wrapper.getCc())
                .addContactProperty(RequestProperties.REPLY_TO, wrapper.getReplyToName(), wrapper.getReplyTo())
                .addProperty(RequestProperties.SUBJECT, wrapper.getSubject())
                .addProperty(RequestProperties.CUSTOM_ID, wrapper.getCallbackID())
                .addProperty(RequestProperties.TEXT, wrapper.getTextBody())
                .addProperty(RequestProperties.HTML, wrapper.getHtmlBody())
                .addAttachmentsProperty(wrapper.getAttachments());
        return getRequest();
    }

    public String buildNameAndEmail(String name, String email) {
        return (!Strings.isNullOrEmpty(name) ? (name + " <" + email + ">") : email);
    }

}
