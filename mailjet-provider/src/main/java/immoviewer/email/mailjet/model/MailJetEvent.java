package immoviewer.email.mailjet.model;

import immoviewer.email.common.model.MailStatus;

import java.util.Date;

/**
 * Created by didac on 05.10.15.
 */
public class MailJetEvent {
    public Status event;
    public Long MessageID;
    public Date time;
    public Long mj_campaign_id;
    public Long mj_contact_id;
    public String customcampaign;
    public Long mj_message_id;
    public String smtp_reply;
    public String CustomID;
    public String Payload;
    public String geo;
    public String email;
    public String ip;
    public String agent;
    public String url;
    public Boolean blocked;
    public Boolean hard_bounce;
    public String error_related_to;
    public String error;
    public String source;
    public Long mj_list_id;


    public static enum Status {
        open(MailStatus.opened),
        click(MailStatus.clicked),
        bounce(MailStatus.bounce),
        spam(MailStatus.spam),
        blocked(MailStatus.blocked),
        unsub(MailStatus.unsub),
        typofix(MailStatus.unknown),
        sent(MailStatus.arrived),
        parseapi(MailStatus.unknown),
        newsender(MailStatus.unknown),
        newsenderautovalid(MailStatus.unknown);

        private final MailStatus mailStatus;

        Status(MailStatus mailStatus) {
            this.mailStatus = mailStatus;
        }

        public MailStatus getMailStatus() {
            return mailStatus;
        }
    }
}

