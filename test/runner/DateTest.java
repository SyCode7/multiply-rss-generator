/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import com.clutch.dates.StringToTime;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 *
 * @author itrc169
 */
public class DateTest {

    public static void main(String args[]) {

        System.out.println(Locale.getDefault());
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        
        String a = "Jan 12 12 4:08 PM";
        StringToTime pubDate = new StringToTime(a);
        System.out.println(pubDate);
        System.out.println(formatter.format(pubDate));
        
        a = "2013-01-12 16:08:00";
        pubDate = new StringToTime(a);
        System.out.println(pubDate);
        System.out.println(formatter.format(pubDate));
    }
}
