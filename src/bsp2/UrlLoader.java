package bsp2;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlLoader {

    public static WebPage loadWebPage(String url) throws UrlLoaderException{
       WebPage webPage;

        try {
            URL reference = new URL(url);
            webPage = new WebPage(url, (String) reference.getContent());
        } catch (Exception e) {
            throw new UrlLoaderException(e);
        }

        return webPage;
    }

}
