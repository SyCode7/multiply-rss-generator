/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import com.clutch.dates.StringToTime;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author itrc169
 */
public class DateTest {

    public static void main(String args[]) {

        System.out.println(Locale.getDefault());
        Locale.setDefault(Locale.KOREA);
        System.out.println(Locale.getDefault());
        
        String a = "01 17 08 5:41 PM";
        Date pubDate = new StringToTime(a);
        System.out.println(pubDate);
    }
}
