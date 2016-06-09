package immoviewer.email.common.exception;

/**
 * Created by didac on 12.05.16.
 */
public class MailSendException extends MailException {
    public MailSendException(String message) {
        super(message);
    }

    public MailSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
