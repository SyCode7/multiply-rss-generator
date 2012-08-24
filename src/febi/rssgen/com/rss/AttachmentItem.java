/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package febi.rssgen.com.rss;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author itrc169
 */
public class AttachmentItem extends RSSItem{
    
    public AttachmentItem (String title, String link, Date date, int id){
        super(title, link, date, "","", id, new ArrayList());
    }
    
    public String getFormatted(){
        StringBuilder res = new StringBuilder();
        
        res.append("\t<item>\n\t\t<title></title>\n");
        res.append("\t\t<link>"+getLink()+"</link>\n");
        res.append("\t\t<description></description>\n");
        res.append("\t\t<content:encoded><![CDATA[]]></content:encoded>\n");
        res.append("\t\t<wp:post_id>"+getId()+"</wp:post_id>\n");
        res.append("\t\t<wp:post_date>2011-12-17 18:21:57</wp:post_date>\n");
        res.append("\t\t<wp:comment_status>open</wp:comment_status>\n");
        res.append("\t\t<wp:post_name>"+getTitle()+"</wp:post_name>\n");
        res.append("\t\t<wp:ping_status>open</wp:ping_status>\n");
        res.append("\t\t<wp:post_type>attachment</wp:post_type>\n");
        res.append("\t\t<wp:attachment_url>"+getLink()+"</wp:attachment_url>\n");
        res.append("\t</item>\n");
        
        return res.toString();
    }
    
    public String toString(){
        return getFormatted();
    }
}
