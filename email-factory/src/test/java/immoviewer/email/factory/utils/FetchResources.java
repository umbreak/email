package immoviewer.email.factory.utils;

import immoviewer.email.factory.MailFactoryTest;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by didac on 17.05.16.
 */
public class FetchResources {

    public static File getFileFromResources(String name) throws URISyntaxException {
        URL resource = FetchResources.class.getResource("/" + name);
        return new File(resource.toURI());
    }

    public static String getStringFromResources(String name) throws URISyntaxException, IOException {
        URL resource = FetchResources.class.getResource("/" + name);
        InputStream in = resource.openStream();
        String result=null;
        try {
            result=IOUtils.toString( in, Charset.defaultCharset() );
        } finally {
            IOUtils.closeQuietly(in);
        }
        return result;
    }
}
