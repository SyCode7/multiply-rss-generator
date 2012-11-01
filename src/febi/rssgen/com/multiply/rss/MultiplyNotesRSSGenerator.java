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

import febi.rssgen.com.rss.Global;
import febi.rssgen.com.rss.RSSCategoryInner;
import febi.rssgen.com.rss.RSSGenerator;
import febi.rssgen.com.rss.RSSItem;
import febi.rssgen.com.rss.RSSItemComment;
import febi.rssgen.com.rss.RSSTagInner;
import febi.rssgen.com.rss.RSSTerm;
import java.util.ArrayList;
import java.util.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MultiplyNotesRSSGenerator extends RSSGenerator {

    private String folder = "notes";

    public MultiplyNotesRSSGenerator(String blogTitle, String link) {

        super(blogTitle, link, "");
    }

    @Override
    public ArrayList<RSSTerm> getParsedItems(String rawString) {
        ArrayList<RSSTerm> items = new ArrayList();

        String authorPost="", linkPost, titlePost, descriptionPost, dateStr="";
        int idPost=0;
        Date pubDate;

        String authorComment, urlComment, quoteCommentAuthor = "",
                quoteCommentContent = "", contentComment;
        Date dateComment;

        Document doc = Jsoup.parse(rawString);

        Element post = doc.select("div.itemboxsub").parents().first();
//
//        Element content = post.select("div.itemboxsub").first();

        String itemAttrib = post.attr("id");
        
        System.out.println("id: "+post.attr("id"));
        try{
            authorPost = itemAttrib.split(":")[0];
            idPost = Integer.parseInt(itemAttrib.split(":")[2]);
        } catch (ArrayIndexOutOfBoundsException e) {
            Global.printReportGuide(itemAttrib);
        }        
        
        //process the date
        //.*?(<nobr>|</a> on )(.*?)(</nobr>| for)(.+?)
        Element dateEl = post.select("nobr").first();
        if (dateEl != null) {
            dateStr = dateEl.html();
        } //alternate handling
        else {
            dateEl = post.select("div.itemsubsub").first();
            try{
                dateStr = dateEl.html().split(" on ")[1].split(" for ")[0];
            } catch (ArrayIndexOutOfBoundsException e) {
                Global.printReportGuide(dateEl.html());
            }
        }

        dateStr = dateStr.replaceAll(",|'| an |at|on", "").trim();

        pubDate = Global.getPostDate(dateStr);
        
        descriptionPost = post.select("td.cattitle").first().html()
                .replaceAll("&nbsp;", " ");

        linkPost = link;
        Element titleEl = post.select("span[itemprop=name]").first();
        if(titleEl!=null){
            titlePost = titleEl.html();
        }else{
            titlePost = descriptionPost;
        }

        //check for comments
        Elements commentsEl = doc.select("div.reply");

        ArrayList<RSSItemComment> commentList = new ArrayList();

        int index = 0;
        for (Element comment : commentsEl) {

            Element replybody = comment.select("div.replybodytext > div.replybodytext")
                    .first();
            authorComment = replybody.attr("author");
            urlComment = "http://" + authorComment + ".multiply.com";

            //process the date
            try{
            dateStr = comment.select("div.replyboxstamp").first()
                    .html().split(" wrote on ")[1].split(",")[0]
                    .replaceAll(",|'| an |at|on", "").trim();
            } catch (ArrayIndexOutOfBoundsException e) {
                try{
                //alternative date  2009/01/05 08:54
                dateStr = comment.select("div.replyboxstamp").first()
                        .html().split(" wrote ")[1]
                        .replaceAll(",|'| an |at|on", "").trim();
                }catch (ArrayIndexOutOfBoundsException e1){
                    Global.printReportGuide(comment.select("div.replyboxstamp").first()
                        .html());
                }
            }

            // comment post date is generated in sequence, additional of 1 second
            dateComment =
                    new Date((Global.getCommentPostDate(dateStr)).getTime() + (index * 1000));

            contentComment = replybody.html().replaceAll("&nbsp;", " ");

            //quote if exist
            Element quoteAuthorEl = comment.select("div.quotea > a").first();
            if (quoteAuthorEl != null) {
                quoteCommentAuthor = quoteAuthorEl.html();
                Element quoteCommentEl = comment.select("div.quotet > i").first();
                if(quoteCommentEl != null){
                    quoteCommentContent = quoteCommentEl.html();
                }else{
                    quoteCommentContent = comment.select("div.quotea").first().ownText();
                }

            }

            commentList.add(new RSSItemComment(index, authorComment, contentComment,
                    quoteCommentAuthor, quoteCommentContent, dateComment, urlComment));
            index++;
        }
        //end processing comments

        RSSItem newItem = new RSSItem(titlePost, linkPost, pubDate, descriptionPost,
                authorPost, idPost, commentList);

        //skipping attachments

        //skipping tags, only add notes tag
        newItem.getTags().add(new RSSTagInner(folder));
        newItem.getCategories().add(new RSSCategoryInner(folder));
        
        //store the object
        items.add(newItem);

        return items;
    }

    @Override
    public String getRSSItems(ArrayList<RSSTerm> items) {
        //construct rssString
        StringBuilder strbld = new StringBuilder();

        for (RSSTerm item : items) {

            strbld.append(item.getFormatted());

        }

        return strbld.toString();
    }

    public String getFolder() {
        return folder;
    }
}
