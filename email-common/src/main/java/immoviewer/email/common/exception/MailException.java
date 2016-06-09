package immoviewer.email.common.exception;

/**
 * Created by didac on 12.05.16.
 */
public class MailException extends RuntimeException {
    public MailException(String message) {
        super(message);
    }

    public MailException(String message, Throwable cause) {
        super(message, cause);
    }
}
