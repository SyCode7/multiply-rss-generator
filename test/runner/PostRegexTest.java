/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import com.clutch.dates.StringToTime;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author itrc169
 */
public class PostRegexTest {

    private static int authorIndex = 1;
    private static int postIdIndex = 2;
    //private static int linkIndex = 3;
    private static int titleIndex = 3;
    private static int dateIndex = 6;
    private static int contentIndex = 3;

    public static void main(String args[]) throws IOException {
        String fileContents = readEntireFile("/home/itrc169/public_html/test.3.htm");
        //new line cleansing
        fileContents = fileContents.replaceAll("(?:\\r|)\\n", " ");

        //non ascri cleansing
        fileContents = fileContents.replaceAll("[^\\p{ASCII}]", "");

//        System.out.println(fileContents);
//        String patternString = "<div id=\"item_.+:journal:.*>.*<a rel='bookmark' "
//                + "href='(.*)' itemprop='url'>.*<div style='clear:both'>"
//                + "<!-- -.</div>";
        String patternString = "href=(.*)>";
//         patternString = "(?i)href=\"?(.*journal.*?)\"?>";
//         patternString = "<td  align=left><span class=subject.*?href=(.*?)>";

//        patternString = "<div id=\"item_(.+?):journal:(.*?)\".*?>.*?<a rel='bookmark' "
//                        + "href='(.*?)' itemprop='url'><span itemprop='name'>(.*?)"
//                        + "</span></a>.*?(<nobr>|</a> on )(.*?)(</nobr>| for)(.+?)"
//                        + "<div id=\"item_body\"(.+?)>(.*?)(?:</div>|)(?:<br clear=right>"
//                        + "<img .*?>|)<div style='clear:both'><!-- --></div>";
        patternString = "<div id=\"item_(.+?):" + "notes" + ":(.*?)\".*?>.*?(?:</div>|)"
                + "(?:.*?<a rel='bookmark'.*?itemprop='url'><span itemprop='name'>|)(.*?)"
                + "(</span></a>|</div>).*?(<nobr>|</a> on )(.*?)(</nobr>| for)(?:.+?"
                + "<div id=\"item_body\".+?>(.*?)</div>(</div></div></div>)"
                + "?(<br clear=right>|<div class=)|)";

        String text = "<a href=\"ad1\">sdqs</a><a href=\"ad2\">sds</a><a href=ad3>qs</a>";


        System.out.println(patternString);
        Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);

        Matcher m = pattern.matcher(fileContents);

//        System.out.println(m.find());
        int i = 0;
        if (m.find()) {

            System.out.println(i++ + m.group(authorIndex)
                    + " - " + m.group(dateIndex)
                    + " - " + m.group(postIdIndex)
                    + " - " + m.group(titleIndex)
                    + " - " + m.group(contentIndex));
            //process the date
            String dateStr = m.group(dateIndex)
                    .replaceAll(",|'| an |at|on", "");
            StringToTime pubDate = new StringToTime(dateStr);

            System.out.println(m.group(contentIndex).replaceAll("(<div.*>.*</div>|</div>)", ""));

            System.out.println(m.group(contentIndex).replaceAll("</div>", ""));
            //additional content index
            int addContent = 8;
            System.out.println(m.group(addContent).endsWith("</span> "));


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
