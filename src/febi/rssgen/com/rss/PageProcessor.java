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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author itrc169
 */
public abstract class PageProcessor {

    private RSSGenerator rssGen;
    //execute command
    public static final String HTTP_STRING = "http://";
    private String url;

    public PageProcessor(String url) {

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

    public static String getPageData(String urlStr) {
        StringBuilder result = new StringBuilder();
        String response;
        URL url;
        try {

            //source: http://www.vogella.com/articles/JavaNetworking/article.html#javanetwork_example_readpage
            url = new URL(urlStr);
//            System.out.println("Opening: "+urlStr);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            while ((response = in.readLine()) != null) {
                result.append(response).append("\n");
            }
        } catch (MalformedURLException ex) {
            System.err.println("Error on: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("Error on: " + ex.getMessage());
        }

        return result.toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public abstract ArrayList getPageResults();
}
