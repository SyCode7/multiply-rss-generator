/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import com.clutch.dates.StringToTime;
import febi.rssgen.com.rss.Global;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author itrc169
 */
public class DateTest {

    public static void main(String args[]) {

        System.out.println(Locale.getDefault());
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        
//        String a = "Jan 12 12 4:08 PM";
        String a = "Apr 9, '12 2:56 PM";
        a = "Apr 27";
        a = a.replaceAll(",|'| an |at|on", "");
//        StringToTime pubDate = new StringToTime(a);
        
        SimpleDateFormat postDateFormat = new SimpleDateFormat("MMM d yy h:m a");
        postDateFormat = new SimpleDateFormat("MMM d yy");
        
        Date pubDate = null;
        try {
            pubDate = postDateFormat.parse(a);
        } catch (ParseException ex) {
            try{
                postDateFormat = new SimpleDateFormat("MMM d");
                pubDate = postDateFormat.parse(a);
            } catch (ParseException x) {
            pubDate = Calendar.getInstance().getTime();
            }
        }
        System.out.println(pubDate);
        System.out.println(formatter.format(pubDate));
        
        a = "2013-01-12 16:08:00";
        pubDate = new StringToTime(a);
        System.out.println(pubDate);
        System.out.println(formatter.format(pubDate));
        
        System.out.println(Global.getCommentPostDate("Jan 28"));
    }
}
