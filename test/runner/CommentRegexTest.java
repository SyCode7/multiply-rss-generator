/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import febi.rssgen.com.rss.Global;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author itrc169
 */
public class CommentRegexTest {

    private static int authorCommentIndex = 3;
    private static int urlCommentIndex = 2;
    private static int dateCommentIndex = 4;
    private static int quoteAllIndex = 6;
    private static int quoteAuthorIndex = 1;
    private static int quoteContentIndex = 2;
    private static int contentCommentIndex = 7;

    public static void main(String args[]) throws IOException {
        String fileContents = readEntireFile("/home/itrc169/public_html/test.true.html");
        //new line cleansing
        fileContents = fileContents.replaceAll("(?:\\r|)\\n", " ");

//        System.out.println(fileContents);
//        String patternString = "<div id=\"item_.+:journal:.*>.*<a rel='bookmark' "
//                + "href='(.*)' itemprop='url'>.*<div style='clear:both'>"
//                + "<!-- -.</div>";
        String patternString = "href=(.*)>";
//         patternString = "(?i)href=\"?(.*journal.*?)\"?>";
//         patternString = "<td  align=left><span class=subject.*?href=(.*?)>";

        patternString = "<div id='reply_(.+?):journal:.*?>.*?"
                + "<div class=replyboxstamp><a href=(.*?)>(.*?)</a> wrote (.*?)"
                + "(, edited|</div>).*?<div class=\"replybodytext\">"
                + "(?:<div class=quotet><div class=quotea>(.*?)"
                + "<img src=.*?end\\.gif.*?|)<div class=\"replybodytext\""
                + " id=\"reply_body_.*?>(.*?)</div></div></div></td>";

        String text = "<a href=\"ad1\">sdqs</a><a href=\"ad2\">sds</a><a href=ad3>qs</a>";

        System.out.println(patternString);
        Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);

        Matcher m = pattern.matcher(fileContents);


        //quote pattern
        patternString = "<a href=.*?>(.*?)</a.*?<i>(.*?)</i>";
        pattern = Pattern.compile(patternString, Pattern.DOTALL);


//        System.out.println(m.find());
        int i = 0;
        while (m.find()) {
//            System.out.println(m.group(0));

            System.out.println(i++ + m.group(authorCommentIndex)
                    + " - " + m.group(urlCommentIndex)
                    + " - " + m.group(dateCommentIndex)
                    + " - " + m.group(quoteAllIndex)
                    + " - " + m.group(contentCommentIndex));
            //process the date
            String dateStr = m.group(dateCommentIndex)
                    .replaceAll(",|'| an |at|on", "");
            Date pubDate = Global.getCommentPostDate(dateStr.trim());
            System.out.println(dateStr + " : " + pubDate);

            if (m.group(quoteAllIndex) != null) {
                Matcher m2 = pattern.matcher(m.group(quoteAllIndex));
                while (m2.find()) {
                    System.out.println("-->Quote: " + m2.group(quoteAuthorIndex)
                            + " - " + m2.group(quoteContentIndex));
                }
            }
        }

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
