/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package febi.rssgen.com.rss;

import febi.com.log.Logger;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author itrc169
 */
public abstract class PageProcessor {

    private RSSGenerator rssGen;
    //execute command
    private static HttpClient client;
    public static final String HTTP_STRING = "http://";
    private String url;

    public PageProcessor(String url) {

        //prepare http client
        client = new DefaultHttpClient();
        client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
                HttpVersion.HTTP_1_1);

        //construct url

        //adding http
        if (!url.startsWith(HTTP_STRING)) {
            this.url = HTTP_STRING + url;
        } else {
            this.url = url;
        }


    }

    public RSSGenerator getRSSGenerator() {
        return rssGen;
    }

    public void setRssGen(RSSGenerator rssGen) {
        this.rssGen = rssGen;
    }

    public static String getPageData(String url) {
        //getter
        HttpGet get = new HttpGet(url);

        // Here we go!
        String result = null;
        try {
            result = EntityUtils.toString(client.execute(get).getEntity(), "UTF-8");
        } catch (IOException ex) {
            Logger.outputMessage("\nError on fecth data");
        }

        if (result == null) {
            Logger.outputMessage("\nData fetch from : " + url + " is null\n");
            return null;
        }
        return result;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public abstract ArrayList getPageResults();
}
