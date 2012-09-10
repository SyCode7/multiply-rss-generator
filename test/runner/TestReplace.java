/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import febi.rssgen.com.rss.RSSItemImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author itrc169
 */
public class TestReplace {

    public static void main(String args[]) {

//        String dodol="/////";
////        String oke = dodol.replaceAll("//", "/");
//        String oke = dodol.replaceAll("\\W", "-");
//        System.out.println(dodol+" "+oke);

        String text = "dfdsfsdf<div><span class=\"insertedphoto\">"
                + "<a href=\"http://havban.multiply.com/photos/hi-res/1M/4\">"
                + "<img border=\"0\" class=\"alignleft\" "
                + "src=\"//multiply.com/mu/havban/image/D7c7Ph3GM1a6TzvN1crDh"
                + "g/photos/1M/300x300/4/hurdle-almost-best.png?et=kFgQQED8zpft"
                + "D59Bh8AezA&amp;nmid=0\"></a></span>    </div><div><br></div>"
                + "<div><br></div><div><br></div><div><br></div><div><br></div>"
                + "<div><br></div><div><br></div><div><br></div><div><br></div>"
                + "<div><br></div><div><br></div><div><br></div><div>another "
                + "image</div><div><span class=\"insertedphoto\"><a href=\"http"
                + "://havban.multiply.com/photos/hi-res/1M/5\"><img border=\"0\""
                + " class=\"alignmiddleb\" src=\"//multiply.com/mu/havban/image"
                + "/GKkIacUiVryWRAeSBNmwxA/photos/1M/300x300/5/desain-dasar.png?"
                + "et=L1OyMxaDf1f%2ByN5rA2H3AQ&amp;nmid=0\"></a></span></div>"
                + "<div>again..</div><div><span class=\"insertedphoto\"><a href"
                + "=\"http://havban.multiply.com/photos/hi-res/1M/6\"><img "
                + "border=\"0\" class=\"alignmiddleb\" src=\"//multiply.com/mu"
                + "/havban/image/SUpjE64p+KQZVqyWeFxOoQ/photos/1M/300x300/6/"
                + "digital-security-door-lock.jpg?et=NH0SEHiuhLx4cb0%2BC3pFlA&a"
                + "mp;nmid=0\"></a></span></div>";
        String replText = text;

        //image attachment pattern
        String attachmentSearchPattern = "<img .*?src=\"(.*?)\"";
        Pattern patternAttachment = Pattern.compile(attachmentSearchPattern, Pattern.DOTALL);
        Matcher attachmentMatcher = patternAttachment.matcher(replText);
        int attachmentFound = 0;
        while (attachmentMatcher.find()) {
            String imageLink = attachmentMatcher.group(1);
            String newImageLink = getGoodImageLink(imageLink);
            replText = replText.replaceAll(imageLink.replaceAll("/", "\\/"), newImageLink);
            System.out.println("replace: "+imageLink);
            System.out.println("replaceWith: "+newImageLink);

        }
        
        System.out.println(replText);


    }

    private static String getGoodImageLink(String url) {
        String newImageLink = url.replaceAll("&amp;", "&");
        newImageLink += "&.jpg";
        return newImageLink;
    }
}
