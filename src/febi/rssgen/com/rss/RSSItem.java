/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package febi.rssgen.com.rss;

import febi.rssgen.com.util.MailDateFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author itrc169
 */
public class RSSItem extends RSSTerm {

    private String title = "";
    private String link = "";
    private Date date = null;
    private String description = "";
    private String author = "";
    private int id = 0;
    private ArrayList<RSSItemComment> comments = null;
    private ArrayList<String> categories = null;
    private ArrayList<String> tags = null;
    private static int type = 0;

    public RSSItem(String title, String link, Date date, String description,
            String author, int id, ArrayList<RSSItemComment> comments) {
        
        super("title",RSSTerm.POST);
        
        this.title = title;
        this.link = link;
        this.date = date;
        this.description = description;
        this.author = author;
        this.id = id;
        this.comments = comments;
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
        this.description = description;
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

        res.append("\t\t\t<wp:post_type>post</wp:post_type>\n");
        res.append("\t\t\t<wp:status>publish</wp:status>\n");

        res.append("\t\t\t<title>").append(stripslashes(this.title)).append("</title>\n");
        res.append("\t\t\t<description></description>\n");
        res.append("\t\t\t<content:encoded>");
        res.append(stripslashes(this.description));
        res.append("</content:encoded>\n");
        if (this.date != null) {
            res.append("\t\t\t<pubDate>");
            res.append(MailDateFormatter.dateToRfc2822(this.date));
            res.append("</pubDate>\n");
            res.append("\t\t\t<wp:post_date>");
            res.append(MailDateFormatter.dateToRfc2822(this.date));
            res.append("</wp:post_date>\n");
        }

        if (this.link != null) {
            res.append("\t\t\t<link>");
            res.append(this.link);
            res.append("</link>\n");
        }

        //preparing comments
        int comment_id = 1;
        if (this.comments instanceof ArrayList && this.comments != null) {
            for (RSSItemComment comment : this.comments) {
                res.append(comment.getFormatted());
            }

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

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return getFormatted();
    }
}
