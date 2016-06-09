package immoviewer.email.mailgun.model;

/**
 * Created by dmontero on 09/06/16.
 */
public class RestConnectorException extends RuntimeException {
    private int status;

    public RestConnectorException(int status, String message) {
        super(message);
        this.status = status;
    }
}
