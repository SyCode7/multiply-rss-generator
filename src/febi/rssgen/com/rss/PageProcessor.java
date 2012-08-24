/*
 * This file is part of MultiplyRSSGenerator.
 * 
 * MultiplyRSSGenerator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Hidayat Febiansyah
 * @email havban@gmail.com
 * @link http://code.google.com/p/multiply-rss-generator/
 * @year 2012
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
