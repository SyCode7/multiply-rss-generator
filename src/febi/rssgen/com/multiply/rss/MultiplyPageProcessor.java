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
import febi.rssgen.com.rss.Global;
import febi.rssgen.com.rss.PageData;
import febi.rssgen.com.rss.PageProcessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author itrc169
 */
public class MultiplyPageProcessor extends PageProcessor {
    
    public static final String JOURNAL = "journal";
    public static final String REVIEWS = "reviews";
    public static final String RECIPES = "recipes";
    public static final String NOTES = "notes";
    public static final String PHOTOS = "photos";
    
    public static final String MULTIPLY_STRING = ".multiply.com";
    private String folder;
    private static final int POST_PER_PAGE = 20;
    private static final String URL_INFIX = "/item/";
    private TaskItem summary;
    private Date startDate, endDate;

    public MultiplyPageProcessor(String url, String folder, TaskItem summary,
            Date startDate, Date endDate) {

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
//        String multiplyID = this.getUrl().split("//")[1].split("\\.")[0];
//        System.exit(0);

        //initiate Multiply RSSGen
        this.setRssGen(MultiplyRSSGeneratorFactory.createMultiplyRSSGenerator(url, folder));

        this.folder = folder;
        this.summary = summary;
        
        this.startDate = startDate;
        this.endDate = endDate;

    }

    @Override
    public ArrayList getPageResults() {
        ArrayList<PageData> results = new ArrayList();
        ArrayList<PageData> pageList;

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
        
        //if it's a photos folder, just skip it, we'll handle it later in the
        //MultiplyPhotoRSSGenerator class
        if(startDate !=null && !folder.equals(PHOTOS)){
            summary.setDesc("Filtering page by preferred date range.");
            
            ArrayList<PageData> listToRemove = new ArrayList();
            for (Iterator<PageData> it = results.iterator(); it.hasNext();) {
                PageData page = it.next();
                if(page.getDate().after(endDate)
                        || page.getDate().before(startDate)) {
                    //remove from list
                    listToRemove.add(page);
                }
            }
            
            results.removeAll(listToRemove);
        }

        return results;
    }

    public ArrayList<PageData> parsePageUrl(String page) {

        ArrayList<PageData> list = new ArrayList();

        String url=null;
        Date date=null;

        Document doc = Jsoup.parse(page);

        Elements items = doc.select("div.item");
        if(items == null) {
            items = doc.select("span.itemboxsub").parents();
        }
        
        for (Element item : items) {
            try{
                url = folder + URL_INFIX + item.attr("id").split(":")[2];
            } catch (ArrayIndexOutOfBoundsException e) {
                Global.printReportGuide(item.attr("id"));
                System.out.println("item: "+item.html());
            }

            if (!url.startsWith(HTTP_STRING)) {
                url = getCleanedURL(url);
            }
            
            //obtain the date
            Element dateEl = item.select("nobr").first();
            if (dateEl != null) {
                date = Global.getPostDate(dateEl.html().
                        replaceAll(",|'| an |at|on", "").trim());
            } //alternate handling
            else {
                dateEl = item.select("div.itemsubsub[itemprop=description]").first();
                try{
                date = Global.getPostDate(
                        dateEl.html().split(" on ")[1].split(" for ")[0]
                        .replaceAll(",|'| an |at|on", "").trim());
                } catch (ArrayIndexOutOfBoundsException e) {
                    Global.printReportGuide(dateEl.html());
                }
            }

            list.add(new PageData(url,date));
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
                
                //obtain the date
                Element dateEl = item.select("nobr").first();
                if (dateEl != null) {
                    date = Global.getPostDate(dateEl.html()
                            .replaceAll(",|'| an |at|on", "").trim());
                } //alternate handling
                else {
                    dateEl = item.parent();

                    try{
                    date = Global.getPostDate(
                            dateEl.ownText().split(" on ")[1].split(" for ")[0]
                            .replaceAll(",|'| an |at|on", "").trim());
                    } catch (ArrayIndexOutOfBoundsException e) {
                    try{
                        //alternative date  2009/01/05 08:54
                        date = Global.getPostDate(dateEl.ownText().split(" wrote ")[1]
                                .replaceAll(",|'| an |at|on", "").trim());
                    }catch (ArrayIndexOutOfBoundsException e1){
                        Global.printReportGuide(dateEl.ownText());
                    }
            }
                }
                
                list.add(new PageData(url,date));
            }
        }
        
        //handling photo
        if(list.size() < POST_PER_PAGE) {
//            System.out.println("handling using last case.. alias photo");
            //change searched element
            items = doc.select("div.album");
            for (Element item : items) {
                //for table format
                url = item.select("a").first().attr("href");

                if (!url.startsWith(HTTP_STRING)) {
                    url = getCleanedURL(url);
                }
                
                //obtain the date
                //skipped for photos (no way to obtain oy right now
                
                list.add(new PageData(url,date));
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
