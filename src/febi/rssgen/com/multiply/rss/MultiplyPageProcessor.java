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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author itrc169
 */
public class MultiplyPageProcessor extends PageProcessor {

    public static final String MULTIPLY_STRING = ".multiply.com";
    private String itemSearchPattern;
    private String alternateSearchPattern;
    private String folder;
    private Pattern pattern;
    private static final int POST_ID = 1;
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

        this.itemSearchPattern =
                "<div id=\"item_"+multiplyID+":" + folder + ":(.*?)\".*?>";
        this.alternateSearchPattern =
                "<td  align=left><span class=subject.*?href=(.*?)>";

        //initiate Multiply RSSGen
        this.setRssGen(new MultiplyRSSGenerator(this.getUrl(),
                "Multiply RSS - " + this.getUrl(), folder));

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
//
//            Logger.dumpToFile("/home/itrc169/public_html/page_" + folder + "_"+
//                    startPost+".htm", rawString);

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

        //pattern
        pattern = Pattern.compile(this.itemSearchPattern, Pattern.DOTALL);

        //new line cleansing
        String cleanedStr = page.replaceAll("(?:\\r|)\\n", " ");
        // Match it.
        Matcher m = pattern.matcher(cleanedStr);

        String url;

        while (m.find()) {
            url = folder+URL_INFIX+m.group(POST_ID);

            if (!url.startsWith(HTTP_STRING)) {
                url = this.getUrl() + "/" + url;
                url = url.replaceAll("//", "/");
                url = url.replaceAll(":/", "://");
            }

            list.add(url);
        }

        if (list.size() < POST_PER_PAGE) {
            //change patern
            //do something here
            pattern = Pattern.compile(this.alternateSearchPattern, Pattern.DOTALL);

            m = pattern.matcher(cleanedStr);

            while (m.find()) {
                //post id here is actually an actual link :P
                url = m.group(POST_ID);

                if (!url.startsWith(HTTP_STRING)) {
                    url = this.getUrl() + "/" + url;
                }

                list.add(url);
            }

        }

        return list;
    }
}
