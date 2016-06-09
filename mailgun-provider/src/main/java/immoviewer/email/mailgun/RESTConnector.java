package immoviewer.email.mailgun;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.ApacheHttpClientConfig;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import com.sun.jersey.multipart.impl.MultiPartWriter;
import immoviewer.email.mailgun.model.RestConnectorException;

import javax.net.ssl.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.MalformedURLException;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class RESTConnector {

    protected URI endpointUrl;
    protected String appUsername;
    protected String appPassword;
    protected Client client;
    protected boolean apache = false;

    private SSLContext ctx;

    public RESTConnector() {
    }

    public RESTConnector(URI endpointUrl) {
        this(endpointUrl, null, null, null, false);
    }

    public RESTConnector(URI endpointUrl, String appUsername, String appPassword) {
        this(endpointUrl, appUsername, appPassword, null, false);
    }

    public RESTConnector(
            final URI endpointUrl,
            final String appUsername,
            final String appPassword,
            final SSLContext ctx, boolean apache) {
        this.ctx = ctx;
        this.apache = apache;
        this.endpointUrl = endpointUrl;
        this.appUsername = appUsername;
        this.appPassword = appPassword;

        generateClient();
    }

    public void generateClient() {

        try {
            client = endpointUrl.toURL().getProtocol().equalsIgnoreCase("https") ? getDefaultSSLClient() : getDefaultClient();

        } catch (MalformedURLException ex) {
            Logger.getLogger(RESTConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private ClientConfig acceptAllSSLConfig() {
        TrustManager[] certs = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }
                }
        };
        SSLContext context = null;
        try {
            context = SSLContext.getInstance("TLS");
            context.init(null, certs, new SecureRandom());
        } catch (java.security.GeneralSecurityException ex) {
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

        ClientConfig config = apache ? new DefaultApacheHttpClientConfig() : new DefaultClientConfig();
        try {
            config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(
                    new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    },
                    ctx));
        } catch (Exception e) {
        }
        return config;
    }

    protected final Client getDefaultSSLClient() {
        ClientConfig config;
        if (ctx == null) {
            config = acceptAllSSLConfig();
        } else {
            config = apache ? new DefaultApacheHttpClientConfig() : new DefaultClientConfig();
            config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(null, ctx));
        }
        config.getClasses().add(MultiPartWriter.class);
        Client c;
        if (apache) {
            config.getProperties().put(ApacheHttpClientConfig.PROPERTY_HANDLE_COOKIES, true);
            c = ApacheHttpClient.create(config);
        } else {
            c = Client.create(config);
        }
        if (appUsername != null && appPassword != null) {
            c.addFilter(new HTTPBasicAuthFilter(appUsername, appPassword));
        }
        return c;
    }

    protected final Client getDefaultClient() {
        ClientConfig cc = apache ? new DefaultApacheHttpClientConfig() : new DefaultClientConfig();
        cc.getClasses().add(MultiPartWriter.class);
        Client c;
        if (apache) {
            cc.getProperties().put(ApacheHttpClientConfig.PROPERTY_HANDLE_COOKIES, true);
            c = ApacheHttpClient.create(cc);
        } else {
            c = Client.create(cc);
        }
        if (appUsername != null && appPassword != null) {
            c.addFilter(new HTTPBasicAuthFilter(appUsername, appPassword));
        }
        return c;
    }

    protected WebResource getWebResource(final UriBuilder uriBuilder) {
        try {
            final URI uri = uriBuilder.build();
            return uri.toURL().getProtocol().equalsIgnoreCase("https") ? getDefaultSSLClient().resource(uri)
                    : getDefaultClient().resource(uri);
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    protected <T> T getEntity(
            final ClientResponse response,
            final Class<T> clazz) {
        T result = null;
        try {
            if ((response.getStatus() == Response.Status.OK.getStatusCode()
                    || response.getStatus() == Response.Status.CREATED.getStatusCode()
                    || response.getStatus() == 100)
                    && response.hasEntity()) {
                result = response.getEntity(clazz);
            } else {
                this.consumeError(response);
            }
            return result;
        } finally {
            this.closeResponse(response);
        }
    }

    private void consumeError(ClientResponse response) {
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RestConnectorException(response.getStatus(), response.toString());
        }
    }

    protected void closeResponse(ClientResponse response) {
        if (response != null) {
            response.close();
        }
    }

    public boolean setOptionalQueyParms(UriBuilder uriBuilder, String key, Object value) {
        if (key != null && value != null) {
            uriBuilder.queryParam(key, value);
            return true;
        }
        return false;
    }
}