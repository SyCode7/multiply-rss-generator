/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package febi.rssgen.com.rss;

import febi.rssgen.com.util.MailDateFormatter;
import java.util.Date;

/**
 *
 * @author itrc169
 */
public class RSSItemComment extends RSSItem{

    private String quoteAuthor = "qAuthor";
    private String quoteContent = "qContent";

    public RSSItemComment(int id, String author, String description, String qAuthor,
            String qContent, Date date, String link) {
        super("", link, date, description, author, id, null);

        this.quoteAuthor = qAuthor;
        this.quoteContent = qContent;

        setType(RSSTerm.COMMENT);
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    public String getQuoteContent() {
        return quoteContent;
    }

    public void setQuoteContent(String quoteContent) {
        this.quoteContent = quoteContent;
    }

    @Override
    public String getFormatted() {
        StringBuilder res = new StringBuilder();
        res.append("<wp:comment>");
        res.append("<wp:comment_id>");
        res.append(this.getId());
        res.append("</wp:comment_id>");
        res.append("<wp:comment_author>");
        res.append(this.getAuthor());
        res.append("</wp:comment_author>");
        res.append("<wp:comment_author_url>");
        res.append(this.getLink());
        res.append("</wp:comment_author_url>");
        res.append("<wp:comment_author_email>");
        res.append(this.getAuthor());
        res.append("@multiply.com</wp:comment_author_email>");
        res.append("<wp:comment_date>");
        res.append(MailDateFormatter.dateToRfc2822(this.getDate()));
        res.append("</wp:comment_date>");
        res.append("<wp:comment_content><![CDATA[");

        if (this.getQuoteAuthor().length() > 0 && this.getQuoteAuthor() != null) {
            res.append("<blockquote><b><a href='http://");
            res.append(this.getQuoteAuthor());
            res.append(".multiply.com' target='_blank'>");
            res.append(this.getQuoteAuthor());
            res.append("</a></b> said: ");
            res.append(this.getQuoteContent());
            res.append("</blockquote>\n");
        }

        res.append("<br/>");
        res.append(this.getDescription());
        res.append("]]></wp:comment_content>");
        res.append("<wp:comment_approved>1</wp:comment_approved>");
        res.append("</wp:comment>\n");

        return res.toString();
    }

    @Override
    public String toString() {
        return getFormatted();
    }
}
