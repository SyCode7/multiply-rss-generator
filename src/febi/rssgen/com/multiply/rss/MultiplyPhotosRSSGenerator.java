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

import com.google.gson.Gson;
import febi.rssgen.com.multiply.rss.json.PhotoBySizesData;
import febi.rssgen.com.multiply.rss.json.PhotoData;
import febi.rssgen.com.rss.Global;
import febi.rssgen.com.rss.RSSCategoryInner;
import febi.rssgen.com.rss.RSSGenerator;
import febi.rssgen.com.rss.RSSItem;
import febi.rssgen.com.rss.RSSItemComment;
import febi.rssgen.com.rss.RSSItemImage;
import febi.rssgen.com.rss.RSSTagInner;
import febi.rssgen.com.rss.RSSTerm;
import java.util.ArrayList;
import java.util.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MultiplyPhotosRSSGenerator extends RSSGenerator {

    private String folder = "photos";

    public MultiplyPhotosRSSGenerator(String blogTitle, String link) {

        super(blogTitle, link, "");
    }

    @Override
    public ArrayList<RSSTerm> getParsedItems(String rawString) {
        ArrayList<RSSTerm> items = new ArrayList();

        String authorPost, linkPost, titlePost, descriptionPost, dateStr="";
        int idPost=0;
        Date pubDate;

        String authorComment, urlComment, quoteCommentAuthor = "",
                quoteCommentContent = "", contentComment;
        Date dateComment;

        Document doc = Jsoup.parse(rawString);

        Element albumbody = doc.getElementById("albumbody");
        Element item = doc.select("div.itemboxsub").first().parent();
        
        Element content = albumbody.getElementById("item_body");

        authorPost = content.attr("author");

        try{
            idPost = Integer.parseInt(item.attr("id").split(":")[2]);
        } catch (ArrayIndexOutOfBoundsException e) {
            Global.printReportGuide(item.attr("id"));
        }

        //process the date
        //.*?(<nobr>|</a> on )(.*?)(</nobr>| for)(.+?)
        Element dateEl = item.select("nobr").first();
        if (dateEl != null) {
            dateStr = dateEl.html();
        } //alternate handling
        else {
            dateEl = item.select("div.itemsubsub[itemprop=description]").first();
            try{
                dateStr = dateEl.html().split(" on ")[1].split(" for ")[0];
            } catch (ArrayIndexOutOfBoundsException e) {
                Global.printReportGuide(dateEl.html());
            }
        }

        dateStr = dateStr.replaceAll(",|'| an |at|on", "").trim();

        pubDate = Global.getPostDate(dateStr);

        
        descriptionPost = content.html().replaceAll("&nbsp;", " ");

        linkPost = "url: http://"+content.attr("author")
                +".multiply.com/"
                +"photos/album/"+ idPost;
        titlePost = item.select("span[itemprop=name]").first().html();


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

        //obtain embedded images           
        //parse description for images
        Elements attachments = content.select("img");
        int attachmentFound = 0;
        for (Element attachment : attachments) {

            String imageLink = attachment.attr("src");
            String newImageLink = MultiplyRSSUtil.getGoodImageLink(imageLink);

            //add to item list
            items.add(new RSSItemImage(newImageLink, newImageLink, pubDate, authorPost));
            attachmentFound++;
        }

        if (attachmentFound > 0) {
            descriptionPost = MultiplyRSSUtil.getGoodImageLink(descriptionPost);
            newItem.setDescription(descriptionPost);
        }
        
        //obtain image list
        Elements scripts = doc.select("script");
        String _600 = "";
        String _photoIds = "";
        for(Element script:scripts){
            //search for "600"
            String[] lines = script.html().split("\n");
            for(String line:lines){
                if(line.startsWith("\"600\"")){
                
                    _600 = "{"+line+"}";
                    
                    _600 = _600.replaceAll("\"600\":", "\"_600\":");
                    _600 = _600.replaceAll("}],", "}]");
                    continue;
                }
                
                if(line.startsWith("var PhotoIds")){
                    _photoIds = line;
                }
            }
        }
        
        //parse for json
        Gson gson = new Gson();
        PhotoBySizesData photos = gson.fromJson(_600,PhotoBySizesData.class);
        
        //process the photo ids to generate links to single photo pages
        //it will be recursive process, just to obtain comments
        //the page it self will be regenerated using RSSImageItem
        //todo - next week
        
        StringBuilder contentStr = new StringBuilder(descriptionPost);
        
        attachmentFound = 0;
        for(PhotoData photo:photos.get600()){

            String imageLink = photo.getSrc();
            if(imageLink == null) continue;

//            String newImageLink = MultiplyRSSUtil.getGoodImageLink(imageLink);
            //well, it seems not working.. haha.. let's just add &.jpg to the
            //end of the string
            String newImageLink = imageLink+"&.jpg";

            //add to item list
            items.add(new RSSItemImage(newImageLink, newImageLink, pubDate, authorPost));
            attachmentFound++;

            //append the content
            contentStr.append("<a href=\"").append(newImageLink).append("\">")
                    .append("<img src=\"").append(newImageLink)
                    .append("\" /></a><br/>\n");
        }
       
        if (attachmentFound > 0) {
            newItem.setDescription(contentStr.toString());
        }

        //obtain tags
        //parse page for tags
        Elements tagsEl = item.select("a[rel=tag]");
        for (Element tagEl : tagsEl) {
            String tag = tagEl.html();
            //add to item list
            newItem.getTags().add(new RSSTagInner(tag));
            newItem.getCategories().add(new RSSCategoryInner(tag));
        }
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
