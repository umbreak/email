package immoviewer.email.common.exception;

/**
 * Created by didac on 12.05.16.
 */
public class TemplateException extends MailException {
    public TemplateException(String message) {
        super(message);
    }

    public TemplateException(String message, Throwable cause) {
        super(message, cause);
    }
}
