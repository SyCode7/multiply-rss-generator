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
import java.util.Date;

/**
 *
 * @author itrc169
 */
public class RSSItemImage extends RSSItem{
    
    public RSSItemImage(String link, String description, Date date, String author){
        super("",link,date,description,author,0,null);
        
        setType(RSSTerm.ATTACHMENT);
    }
    
    @Override
    public String getFormatted(){
        StringBuilder res = new StringBuilder();
        res.append("\t\t<item>\n");
        res.append("\t\t\t<title>").append(getLink()).append("</title>\n");
        res.append("\t\t\t<pubDate>").append(MailDateFormatter.dateToRfc2822(getDate()))
                .append("</pubDate>\n");
	res.append("\t\t\t<description>").append(stripslashes(getDescription()))
                .append("</description>\n");
	res.append("\t\t\t<content:encoded><![CDATA[").append(stripslashes(getDescription()))
                .append("]]></content:encoded>\n");
		
	res.append("\t\t\t<wp:post_date>").append(Global.formatDate(getDate()))
                .append("</wp:post_date>\n");
	res.append("\t\t\t<wp:ping_status>open</wp:ping_status>\n");
        res.append("\t\t\t<wp:post_type>").append(getTypeString())
                .append("</wp:post_type>\n");
        res.append("\t\t\t<wp:attachment_url>").append(getLink())
                .append("</wp:attachment_url>\n");
	res.append("\t\t</item>\n");
        
        return res.toString();
    }
}
