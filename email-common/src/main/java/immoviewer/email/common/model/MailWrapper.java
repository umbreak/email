package immoviewer.email.common.model;

import java.io.File;
import java.util.List;


public class MailWrapper {
    private String from;
    private String fromName;
    private String to;
    private String toName;
    private String cc;
    private String ccName;
    private String replyTo;
    private String replyToName;
    private String subject;
    private String textBody;
    private String htmlBody;
    private List<File> attachments;
    private String callbackID;
    private String templateID;


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getReplyToName() {
        return replyToName;
    }

    public void setReplyToName(String replyToName) {
        this.replyToName = replyToName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public List<File> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<File> attachments) {
        this.attachments = attachments;
    }

    public String getCallbackID() {
        return callbackID;
    }

    public void setCallbackID(String callbackID) {
        this.callbackID = callbackID;
    }

    public String getTemplateID() {
        return templateID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    @Override
    public String toString() {
        return "MailWrapper{" +
                "from='" + getFrom() + '\'' +
                ", fromName='" + getFromName() + '\'' +
                ", to='" + getTo() + '\'' +
                ", toName='" + getToName() + '\'' +
                ", cc='" + getCc() + '\'' +
                ", ccName='" + getCcName() + '\'' +
                ", replyTo='" + getReplyTo() + '\'' +
                ", replyToName='" + getReplyToName() + '\'' +
                ", subject='" + getSubject() + '\'' +
                ", textBody='" + (getTextBody() == null ? "" : getTextBody().length()) + '\'' +
                ", htmlBody='" + (getHtmlBody() == null ? "" : getHtmlBody().length()) + '\'' +
                ", attachments=" + getAttachments() +
                ", callbackID='" + getCallbackID() + '\'' +
                ", templateID='" + getTemplateID() + '\'' +
                '}';
    }


    public static class Builder {

        private final MailWrapper mailWrapper;

        public Builder() {
            this.mailWrapper = new MailWrapper();
        }

        public MailWrapper build(){
            return mailWrapper;
        }


        public Builder from(String from){
            mailWrapper.setFrom(from); return this;
        }


        public Builder fromName(String fromName){
            mailWrapper.setFromName(fromName); return this;
        }

        public Builder callbackID(String value){
            mailWrapper.setCallbackID(value); return this;
        }

        public Builder templateID(String value){
            mailWrapper.setTemplateID(value); return this;
        }

        public Builder to(String to){
            mailWrapper.setTo(to); return this;
        }

        public Builder toName(String toName){
            mailWrapper.setToName(toName); return this;
        }

        public Builder cc(String cc){
            mailWrapper.setCc(cc); return this;
        }

        public Builder replyTo(String replyTo){
            mailWrapper.setReplyTo(replyTo); return this;
        }

        public Builder replyToName(String replyToName){
            mailWrapper.setReplyToName(replyToName); return this;
        }

        public Builder subject(String subject){
            mailWrapper.setSubject(subject); return this;
        }

        public Builder htmlBody(String htmlBody){
            mailWrapper.setHtmlBody(htmlBody); return this;
        }

        public Builder textBody(String textBody){
            mailWrapper.setTextBody(textBody); return this;
        }

        public Builder attachments(List<File> attachments){
            mailWrapper.setAttachments(attachments); return this;
        }

    }
}
