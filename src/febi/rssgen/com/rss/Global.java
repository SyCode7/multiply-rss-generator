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

    public static final String VERSION = "0.2.3";
    public static final String UPDATE = "- bugs fixing..\n- Update on Locale"
            + "\n- Date format simplified."
            + "\n- Date format bug repair, use no StringToTime"
            + "\n- \"Table view\" parsing bug fixed"
            + "\n- Remove apache http lib dependency"
            + "\n- Set checkbox defaults"
            + "\n- Version visible in main window"
            + "\n- alternate parse on link bug"
            + "\n- image attachment support"
            + "\n- tags support"
            + "\n- category support"
            + "\n\nNew in 0.2.0"
            + "\n- faster and robust parsing using jsoup"
            + "\n\nNew in 0.2.1"
            + "\n- photos posts parsing support (album only)"
            + "\n- date range support"
            + "\n\nNew in 0.2.2"
            + "\n- robustness by retrying to download page 3 times"
            + "\n\nNew in 0.2.3"
            + "\n- bug fix on higher resolution photo text bug";
    
    public static String REPORT_LINK =
            "http://code.google.com/p/multiply-rss-generator/issues/list";
    public static String APP_WEB = 
            "http://febiansyah.name/apps/rssgen/multiply.php";
            
    public static final SimpleDateFormat formatter =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat postDateFormat =
            new SimpleDateFormat("MMM d yy h:m a");
    
    public static final SimpleDateFormat commentDateFormat =
            new SimpleDateFormat("MMM d yy");
    public static final SimpleDateFormat commentDateAltFormat =
            new SimpleDateFormat("MMM d");
    //2009/01/05 08:54
    public static final SimpleDateFormat commentDateAlt2Format =
            new SimpleDateFormat("yyyy/MM/dd HH:mm");

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
                //try final date format
                try{
                    pubDate = commentDateAlt2Format.parse(str);
                } catch (ParseException x2){
                pubDate = Calendar.getInstance().getTime();
                }
            }
        }
        
        return pubDate;
    }
    
    public static void printReportGuide(String report){
        
        System.out.println("System Error: "+report);
        System.out.println("You can try again, if problem persist, please");
        System.out.println("report the the latest output to:");
        System.out.println(REPORT_LINK);
        System.out.println("Or you can post a comment in:");
        System.out.println(APP_WEB);
    }
}
