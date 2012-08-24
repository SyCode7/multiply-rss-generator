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
