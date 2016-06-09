package immoviewer.email.common.exception;

/**
 * Created by didac on 12.05.16.
 */
public class MailEventException extends MailException {
    public MailEventException(String message) {
        super(message);
    }

    public MailEventException(String message, Throwable cause) {
        super(message, cause);
    }
}
