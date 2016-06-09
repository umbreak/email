# Email management
All together provides interface and implementation for sending emails and receiving events (webhooks) from the sent emails.
The existing implementations are for the following providers:
* Mailjet
* MailGun

To use it, a factory is provided:
```java
    //Example with mailJet
    Token token1 = new Token(MailJet_KEY, MailJet_SECRET);
    MailProvider mailjetProvider=MailProviderCreator.
            create(MailProviderType.MailJet, token1, null);

    //Example with MailGun
    Token token2 = new Token(MailGun_KEY, MailGun_SECRET);
    MailProvider mailgubProvider=MailProviderCreator.
            create(MailProviderType.MailGun, token2, MailGun_DOMAIN);
```
An example how to send an email would be as follows:
```java
    //If you need a callback (to track the further events),
    //some providers allow you to specify it in advance
    //and some others give it to you back in the response
    String callbackID = UUID.randomUUID().toString();
    MailWrapper mailContent = new MailWrapper.Builder()
            .from("a_email_from_address@domain.com")
            .fromName("A name")
            .to("a_email_to_address@domain.com")
            .callbackID(callbackID)
            .subject("Test sendEmailBasic " + providerType)
            .replyTo("reply_to_email_address@domain.com")
            .replyToName("Didac Montero")
            .htmlBody("<h1>Test with replyTo</h1><p>body test wuith reply to <b>sendEmailBasic</b> <a href='http://google.com'>Link</a></p>").build();
    callbackID=provider.send(mailContent);
```

An example how to fetch an event(s)would be as follows. Note that you will need a HTTP server listening to some URL where the Webhooks form the providers will be sent:
```java
    List<MailEventResponse> mailEventResponses = provider.fetchEvents(receivedMessage);

```
