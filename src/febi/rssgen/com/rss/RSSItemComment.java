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

import java.util.Date;

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
        res.append(Global.formatDate(this.getDate()));
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
