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
package febi.rssgen.com.multiply.rss;

import febi.com.log.Logger;
import febi.rssgen.com.multiply.gui.TaskItem;
import febi.rssgen.com.rss.PageProcessor;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author itrc169
 */
public class MultiplyPageProcessor extends PageProcessor {

    public static final String MULTIPLY_STRING = ".multiply.com";
    private String folder;
    private static final int POST_PER_PAGE = 20;
    private static final String URL_INFIX = "/item/";
    private TaskItem summary;

    public MultiplyPageProcessor(String url, String folder, TaskItem summary) {

        super(url);


        //checking ending of multiply.com
        if (!url.endsWith(MULTIPLY_STRING) && !url.endsWith(MULTIPLY_STRING + "/")) {
            this.setUrl(this.getUrl() + MULTIPLY_STRING);
        }

        while (true) {
            if (this.getUrl().endsWith("/")) {
                this.setUrl(this.getUrl().substring(0, -1));
            } else {
                break;
            }
        }
//        System.out.println(this.getUrl().split("//")[1]);
        String multiplyID = this.getUrl().split("//")[1].split("\\.")[0];
//        System.exit(0);

        //initiate Multiply RSSGen
        this.setRssGen(MultiplyRSSGeneratorFactory.createMultiplyRSSGenerator(url, folder));

        this.folder = folder;
        this.summary = summary;

    }

    @Override
    public ArrayList getPageResults() {
        ArrayList results = new ArrayList();
        ArrayList pageList;

        //subfolder
        String subFolder = "/" + folder + "/?&page_start=";
        int startPost = 0;
        String url_paged, rawString;
        int lastShortOf = 0;
        int sumShortOf = 0;

        //parse
        do {
            url_paged = this.getUrl() + subFolder + startPost;
            Logger.outputMessageLn("-->Fecthing: " + url_paged);

            rawString = getPageData(url_paged);

            pageList = parsePageUrl(rawString);

            results.addAll(pageList);

            Logger.outputMessageLn("----> " + pageList.size() + " page(s) processed..");

            if (lastShortOf > 0 && pageList.size() > 0) {
                sumShortOf += lastShortOf;
            }

            if (pageList.size() < POST_PER_PAGE) {
                lastShortOf = POST_PER_PAGE - pageList.size();
            }

            startPost += POST_PER_PAGE;

        } while (pageList.size() > 0);

        //message summary
        if (sumShortOf > 0) {
            summary.setDesc("Warning! Possible of " + sumShortOf + " page(s) failed to be parsed");
        } else {
            summary.setDesc("Pages parsed perfectly.");
        }

        return results;
    }

    public ArrayList<String> parsePageUrl(String page) {

        ArrayList<String> list = new ArrayList();

        String url;

        Document doc = Jsoup.parse(page);

        Elements items = doc.select("div.item");
        for (Element item : items) {
            url = folder + URL_INFIX + item.attr("id").split(":")[2];

            if (!url.startsWith(HTTP_STRING)) {
                url = getCleanedURL(url);
            }

            list.add(url);
        }

        if (list.size() < POST_PER_PAGE) {
            //change searched element
            items = doc.select("span.subject");
            for (Element item : items) {
                //for table format
                url = item.select("a").first().attr("href");

                if (!url.startsWith(HTTP_STRING)) {
                    url = getCleanedURL(url);
                }

                list.add(url);
            }

        }
        
        if (list.size() < POST_PER_PAGE) {
            //change searched element
            items = doc.select("span.itemboxsub");
            for (Element item : items) {
                //handling notes
                url = folder + URL_INFIX + item.parent().attr("id").split(":")[2];

                if (!url.startsWith(HTTP_STRING)) {
                    url = getCleanedURL(url);
                }

                list.add(url);
            }

        }

        return list;
    }

    public String getCleanedURL(String url) {
        String newURL = this.getUrl() + "/" + url;
        newURL = newURL.replaceAll("//", "/");
        newURL = newURL.replaceAll(":/", "://");
        return newURL;
    }
}
