package immoviewer.email.common.model;

/**
 * Created by didac on 13.05.16.
 */
public enum MailStatus {
    unknown,
    pending,
    queued,
    sent,
    arrived,
    opened,
    clicked,
    bounce,
    spam,
    unsub,
    blocked;

	public static MailStatus[] mainEvents() {
        return new MailStatus[]{sent, arrived, opened, clicked, bounce, spam};
    }
}
