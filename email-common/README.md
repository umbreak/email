# Email commons
This project contains the POJOS for common requests/responses and the common interfaces and abstract classes to wrap the multiple mail providers.

This interface underlines the functionality to send emails & receive email events (many email providers offer this, sometimes called webhooks).

In order to implement the sending functionality for a new provider, you will have to implement the methods in the interface `MailDeliveryProducer` and the methods not implemented in the abstract class `RequestBuilder`:

In order to implement the receiving events functionality for a new provider, you will have to implement the methods in the interface `MailEventsConsumer` and the methods not implemented in the abstract class `ResponseEventsBuilder` and `ResponseEventsParser` (this offers a default json parsing, which is enough for most of the providers).