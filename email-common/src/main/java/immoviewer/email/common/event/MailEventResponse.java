package immoviewer.email.common.event;

import immoviewer.email.common.model.MailProviderType;
import immoviewer.email.common.model.MailStatus;

import java.util.Date;

public class MailEventResponse {


    public final String callbackID;
    public final MailStatus status;
    public final MailProviderType provider;
    public final Date date;
    public final String error;
    public final String url;
    public final String email;

    private MailEventResponse(Builder builder){
        callbackID = builder.callbackID;
        status = builder.status;
        provider = builder.provider;
        date = builder.date;
        error = builder.error;
        url=builder.url;
        email=builder.email;
    }

    public static class Builder {
        public final String callbackID;
        public final String email;
        public MailStatus status;
        public MailProviderType provider;
        public Date date;
        public String error;
        public String url;

        public Builder(String callbackID, String email){
            this.callbackID=callbackID;
            this.email=email;
            this.date=new Date();
        }

        public Builder status(MailStatus value){ status=value; return this;}
        public Builder provider(MailProviderType value){ provider=value; return this;}
        public Builder date(Date value){ date=value; return this;}
        public Builder error(String value){ error=value; return this;}
        public Builder url(String value){ url=value; return this;}

        public MailEventResponse build(){
            return new MailEventResponse(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MailEventResponse that = (MailEventResponse) o;

        if (callbackID != null ? !callbackID.equals(that.callbackID) : that.callbackID != null) return false;
        if (status != that.status) return false;
        if (provider != that.provider) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        return url != null ? url.equals(that.url) : that.url == null;

    }

    @Override
    public int hashCode() {
        int result = callbackID != null ? callbackID.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MailEventResponse{" +
                "callbackID='" + callbackID + '\'' +
                ", status=" + status +
                ", provider=" + provider +
                ", date=" + date +
                ", error='" + error + '\'' +
                ", url='" + url + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
