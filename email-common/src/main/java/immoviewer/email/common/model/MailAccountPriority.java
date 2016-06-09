package immoviewer.email.common.model;


public enum MailAccountPriority {
    TRANSACTIONAL(MailProviderType.MailJet),
    NEWSLETTER(MailProviderType.MailGun),
    LEAD(MailProviderType.MailGun);

    private final MailProviderType providerType;

    MailAccountPriority(MailProviderType providerType) {
        this.providerType = providerType;
    }

    public MailProviderType getProviderType() {
        return providerType;
    }
}
