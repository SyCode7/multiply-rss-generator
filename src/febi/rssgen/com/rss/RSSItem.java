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

import febi.rssgen.com.util.MailDateFormatter;
import java.util.ArrayList;
import java.util.Date;

public class RSSItem extends RSSTerm {

    private String title = "";
    private String link = "";
    private Date date = null;
    private String description = "";
    private String author = "";
    private int id = 0;
    private ArrayList<RSSItemComment> comments = null;
    private ArrayList<RSSCategoryInner> categories = null;
    private ArrayList<RSSTagInner> tags = null;
    private static int type = 0;

    public RSSItem(String title, String link, Date date, String description,
            String author, int id, ArrayList<RSSItemComment> comments) {

        super("title", RSSTerm.POST);

        this.title = title;
        this.link = getCleanedURL(link);
        this.date = date;
        this.description = repairDescription(description);
        this.author = author;
        this.id = id;
        this.comments = comments;

        categories = new ArrayList<RSSCategoryInner>();
        tags = new ArrayList<RSSTagInner>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = repairDescription(description);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<RSSItemComment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<RSSItemComment> comments) {
        this.comments = comments;
    }

    @Override
    public String getFormatted() {
        StringBuilder res = new StringBuilder();
        res.append("\t\t<item>\n");

        res.append("\t\t\t<wp:post_type>").append(getTypeString())
                .append("</wp:post_type>\n");
        res.append("\t\t\t<wp:status>publish</wp:status>\n");

        res.append("\t\t\t<title>").append(stripslashes(this.title)).append("</title>\n");
        res.append("\t\t\t<description></description>\n");
        res.append("\t\t\t<content:encoded>");
        res.append(this.description);
        res.append("</content:encoded>\n");
        if (this.date != null) {
            res.append("\t\t\t<pubDate>");
            res.append(MailDateFormatter.dateToRfc2822(this.date));
            res.append("</pubDate>\n");
            res.append("\t\t\t<wp:post_date>");
            res.append(Global.formatDate(this.date));
            res.append("</wp:post_date>\n");
        }

        if (this.link != null) {
            res.append("\t\t\t<link>");
            res.append(this.link);
            res.append("</link>\n");
        }

        //preparing comments
        if (this.comments instanceof ArrayList && this.comments != null) {
            for (RSSItemComment comment : this.comments) {
                res.append(comment.getFormatted());
            }

        }

        //tags
        for (RSSTagInner tag : tags) {
            res.append(tag.getFormatted());
        }
        //categories
        for (RSSCategoryInner category : categories) {
            res.append(category.getFormatted());
        }
        
        res.append("\t\t</item>\n");
        return res.toString();
    }

    protected String stripslashes(String str) {
        String ret;

        ret = str.replaceAll("/\"/", "\\\"");
        ret = ret.replaceAll("/'/", "\\'");
        ret = ret.replaceAll("/\\/", "\\\\");
        ret = ret.replaceAll("/\n/", "\\n");
        ret = ret.replaceAll("/\\{/", "\\{");
        ret = ret.replaceAll("/}/", "\\}");

        return ret;
    }

    public ArrayList<RSSCategoryInner> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<RSSCategoryInner> categories) {
        this.categories = categories;
    }

    public ArrayList<RSSTagInner> getTags() {
        return tags;
    }

    public void setTags(ArrayList<RSSTagInner> tags) {
        this.tags = tags;
    }
    
    private String getCleanedURL(String url){
        String newUrl = url;
        if(newUrl.startsWith("//")) {
            newUrl = url.replaceFirst("//", "http://");
        }
        
        return newUrl;
    }
    
    private String repairDescription(String desc){
        return desc.replaceAll("src=\"//", "src=\"http://");
    }

    @Override
    public String toString() {
        return getFormatted();
    }
}
