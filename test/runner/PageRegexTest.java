/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import febi.rssgen.com.multiply.rss.MultiplyPageProcessor;
import febi.rssgen.com.multiply.rss.MultiplyRSSGenerator;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author itrc169
 */
public class PageRegexTest {

    private static int authorIndex = 1;
    private static int postIdIndex = 2;
    private static int linkIndex = 3;
    private static int titleIndex = 4;
    private static int dateIndex = 6;
    private static int contentIndex = 10;

    public static void main(String args[]) throws IOException {
        String fileContents = readEntireFile("/home/itrc169/public_html/page_notes_120.htm");
//        String fileContents = "<br clear=\"left\"></div></div><div "
//                + "class=\"itemactionspacer\"><!-- --></div><div "
//                + "id=\"item_thedyingsirens:journal:46\" class=\"item\">"
//                + "<div class=itemboxsub><table border=0 cellpadding=0 "
//                + "cellspacing=0 width='100%'><tr><td class=icon width=24>"
//                + "<img alt='Blog Entry' title='Blog Entry' "
//                + "src=http://images.multiply.com/multiply/icons/clean/24x24/journal.png"
//                + " width=24 height=24></td><td class=cattitle><a rel='bookmark' "
//                + "href='/journal/item/46/laba-Laba-bernama-Luna' itemprop='url'><span itemprop='name'>laba-Laba bernama Luna</span></a></td><td class=itemsubsub><nobr>Oct 22, '04  1:41 PM</nobr><br> for everyone</td></tr></table></div><div class=itemshadow><div class=itembox><div id=\"item_body\" class=\"bodytext\">laba-Laba bernama Luna<br><br><br>Laba-laba bernama Luna<br><br>Piaraan satu-satunya<br><br>Luna setia sama tuannya<br><br>Temani terus hari-harinya<br><br>Suatu hari bersama tuannya<br><br>Naik mobil keliling kota<br><br>Tapi jendela terlalu terbuka<br><br>Si angin nakal meniup Luna<br><br>Luna melayang entah kemana<br><br>Si tuan sedih tidak terhingga<br><br> <br><br>21 oktober 2004<br><br>21:33<br><br>Metas bedroom<br><br>Tanjung Mas<br><br>Pugar Restu Julian<br><br>*buat my long lost friendwherever you may roam now...kamu tahu sober itu indah?tetap bertahan hidup yak!!XP<br><br> www.groups.yahoo.com/group/bungamatahari</div><div style='clear:both'><!-- --></div></div></div></div><div class=\"itemactiontopspacer\"><!-- --></div><div class=\"itemactions\" id=\"itemactions\"><div class=\"litemactions\"><a class='mine replybutton' href='/journal/item/46/laba-Laba-bernama-Luna'>0 comments</a></div><div class=\"ritemactions\"><!-- AddThis Button BEGIN -->";
        //new line cleansing
        fileContents = fileContents.replaceAll("(?:\\r|)\\n", " ");
        //fileContents = fileContents.replaceAll("[^\\p{ASCII}]", "");

//        System.out.println(fileContents);
//        String patternString = "<div id=\"item_.+:journal:.*>.*<a rel='bookmark' "
//                + "href='(.*)' itemprop='url'>.*<div style='clear:both'>"
//                + "<!-- -.</div>";
        String patternString = "href=(.*)>";
//         patternString = "(?i)href=\"?(.*journal.*?)\"?>";
//         patternString = "<td  align=left><span class=subject.*?href=(.*?)>";

//        patternString = "<div id=\"item_.+?:" + "journal" + ":.*?>.*?<a rel='bookmark' "
//                + "href='(.*?)' itemprop='url'>.*?<div style='clear:both'>"
//                + "<!-- -.*?</div>";
//            patternString = "<div id=\"item_.+?:" + "journal" + ":.*?>(.*?)<div style='clear:both'>";
        patternString = "<div id=\"item_.+?:" + "notes" + ":(.*?)\".*?>";

        String text = "<a href=\"ad1\">sdqs</a><a href=\"ad2\">sds</a><a href=ad3>qs</a>";
        MultiplyPageProcessor rssgen = new MultiplyPageProcessor("test", "notes", null);
        System.out.println(patternString);
        Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);

        Matcher m = pattern.matcher(fileContents);

//        System.out.println(m.find());
        int i = 1;
        while (m.find()) {

//            System.out.println(i++ +m.group(authorIndex)
//                    +" - "+ m.group(dateIndex)
//                    +" - "+ m.group(linkIndex)
//                    +" - "+ m.group(postIdIndex)
//                    +" - "+ m.group(titleIndex)
//                    +" - "+ m.group(contentIndex));
//            //process the date
//            String dateStr = m.group(dateIndex)
//                           .replaceAll(",|'| an |at", "");
//            StringToTime pubDate = new StringToTime(dateStr);

            System.out.println(i++ +" - "+ m.group(1));


        }
        

        System.out.println(rssgen.parsePageUrl(fileContents).size());
    }

    private static String readEntireFile(String filename) throws IOException {
        FileReader in = new FileReader(filename);
        StringBuilder contents = new StringBuilder();
        char[] buffer = new char[4096];
        int read = 0;
        do {
            contents.append(buffer, 0, read);
            read = in.read(buffer);
        } while (read >= 0);
        return contents.toString();
    }
}
