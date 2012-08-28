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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author itrc169
 */
public class Global {

    public static final String VERSION = "0.1.5";
    public static final String UPDATE = "- bugs fixing..\n- Update on Locale"
            + "\nDate format simplified."
            + "\nDate format bug repair, use no StringToTime";
    public static final SimpleDateFormat formatter =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat postDateFormat =
            new SimpleDateFormat("MMM d yy h:m a");
    
    
    public static final SimpleDateFormat commentDateFormat =
            new SimpleDateFormat("MMM d yy");
    public static final SimpleDateFormat commentDateAltFormat =
            new SimpleDateFormat("MMM d");

    public static String formatDate(Date d) {
        return formatter.format(d);
    }

    public static Date getPostDate(String str) {

        Date pubDate = null;
        try {
            pubDate = postDateFormat.parse(str);
        } catch (ParseException ex) {
            pubDate = Calendar.getInstance().getTime();
        }
        
        return pubDate;
    }
    
    public static Date getCommentPostDate(String str){
             
        Date pubDate = null;
        try {
            pubDate = commentDateFormat.parse(str);
        } catch (ParseException ex) {
            try{
                pubDate = commentDateAltFormat.parse(str);
                Calendar commentDt = Calendar.getInstance();
                commentDt.setTime(pubDate);
                
                Calendar now  = Calendar.getInstance();
                commentDt.set(Calendar.YEAR, now.get(Calendar.YEAR));
                pubDate = commentDt.getTime();
                
            } catch (ParseException x) {
            pubDate = Calendar.getInstance().getTime();
            }
        }
        
        return pubDate;
    }
}
