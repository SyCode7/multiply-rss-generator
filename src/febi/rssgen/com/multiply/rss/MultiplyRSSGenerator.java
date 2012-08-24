/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package febi.rssgen.com.multiply.rss;

import com.clutch.dates.StringToTime;
import febi.rssgen.com.rss.RSSGenerator;
import febi.rssgen.com.rss.RSSItem;
import febi.rssgen.com.rss.RSSItemComment;
import febi.rssgen.com.rss.RSSTerm;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author itrc169
 */
public class MultiplyRSSGenerator extends RSSGenerator {

    private int authorIndex = 1;
    private int postIdIndex = 2;
    private int linkIndex = 3;
    private int titleIndex = 4;
    private int dateIndex = 6;
    private int contentIndex = 10;
    //special case for notes
    private static int noteContentIndex = 3;
    private static int additionalNoteContentIndex = 8;
    //end special case
    private String folder = "journal";
    private Pattern pattern;
    //for comments
    private int authorCommentIndex = 3;
    private int urlCommentIndex = 2;
    private int dateCommentIndex = 4;
    private int quoteAllIndex = 6;
    private int quoteAuthorIndex = 1;
    private int quoteContentIndex = 2;
    private int contentCommentIndex = 7;
    private String commentSearchPattern;
    private Pattern patternComment;
    private String quoteSearchPattern;
    private Pattern patternQuote;

    public MultiplyRSSGenerator(String blogTitle, String link, String folder) {

        super(blogTitle, link, "");

        this.folder = folder;
        if (folder.equals("journal")) {
            this.setItemSearchPattern(
                    "<div id=\"item_(.+?):journal:(.*?)\".*?>.*?<a rel='bookmark' "
                    + "href='(.*?)' itemprop='url'><span itemprop='name'>(.*?)"
                    + "</span></a>.*?(<nobr>|</a> on )(.*?)(</nobr>| for)(.+?)"
                    + "<div id=\"item_body\"(.+?)>(.*?)(?:</div>|)(?:<br clear=right>"
                    + "<img .*?>|)<div style='clear:both'><!-- --></div>");
        } else if (folder.equals("recipes")) {

            this.setItemSearchPattern(
                    "<div id=\"item_(.+?):" + folder + ":(.*?)\".*?>.*?<a rel='bookmark' "
                    + "href='(.*?)' itemprop='url'><span itemprop='name'>(.*?)"
                    + "</span></a>.*?(<nobr>|</a> on )(.*?)(</nobr>| for)(.+?)"
                    + "<div id=\"item_body\"(.+?)>(.*?)</div><br clear=right>");
        } else if (folder.equals("reviews")) {
            this.setItemSearchPattern(
                    "<div id=\"item_(.+?):" + folder + ":(.*?)\".*?>.*?<a rel='bookmark' "
                    + "href='(.*?)' itemprop='url'><span itemprop='name'>(.*?)"
                    + "</span></a>.*?(<nobr>|</a> on )(.*?)(</nobr>| for)(.+?)"
                    + "<div id=\"item_body\"(.+?)>(.*?)</div>(</div></div></div>)"
                    + "?(<br clear=right>|<div class=)");
        } else if (folder.equals("notes")) {
            this.setItemSearchPattern(
                    "<div id=\"item_(.+?):" + "notes" + ":(.*?)\".*?>.*?(?:</div>|)"
                    + "(?:.*?<a rel='bookmark'.*?itemprop='url'><span itemprop='name'>|)(.*?)"
                    + "(</span></a>|</div>).*?(<nobr>|</a> on )(.*?)(</nobr>| for)(?:.+?"
                    + "<div id=\"item_body\".+?>(.*?)</div>(</div></div></div>)"
                    + "?(<br clear=right>|<div class=)|)");

        }

        // Compile the patten.
        pattern = Pattern.compile(itemSearchPattern, Pattern.DOTALL);

        //comment pattern
        commentSearchPattern = "<div id='reply_(.+?):" + folder + ":.*?>.*?"
                + "<div class=replyboxstamp><a href=(.*?)>(.*?)</a> wrote (.*?)"
                + "(, edited|</div>).*?<div class=\"replybodytext\">"
                + "(?:<div class=quotet><div class=quotea>|)(.*?)"
                + "(?:<img src=.*?end\\.gif.*?|)<div class=\"replybodytext\""
                + " id=\"reply_body_.*?>(.*?)</div></div></div></td>";

        this.patternComment = Pattern.compile(commentSearchPattern, Pattern.DOTALL);

        //quote pattern
        this.quoteSearchPattern = "<a href=.*?>(.*?)</a.*?<i>(.*?)</i>";
        this.patternQuote = Pattern.compile(quoteSearchPattern, Pattern.DOTALL);

    }

    @Override
    public String getRSSItems(ArrayList<RSSTerm> items) {
        //construct rssString
        StringBuilder strbld = new StringBuilder();

        for (RSSTerm item : items) {

            strbld.append(item.getFormatted());

        }

        return strbld.toString();

    }

    public String getFolder() {
        return folder;
    }

    @Override
    public ArrayList<RSSTerm> getParsedItems(String rawString) {
        ArrayList<RSSTerm> items = new ArrayList();
        String cleanedStr;

        String authorPost, linkPost, titlePost, descriptionPost, dateStr;
        int idPost;
        Date pubDate;

        String authorComment, urlComment, quoteAll, quoteCommentAuthor = "",
                quoteCommentContent = "", contentComment;
        Date dateComment;

        //new line cleansing
        cleanedStr = rawString.replaceAll("(?:\\r|)\\n", " ");

        // Match it.
        Matcher matcher = pattern.matcher(cleanedStr);

        //parse items
        if (matcher.find()) {
            authorPost = matcher.group(this.authorIndex);
            idPost = Integer.parseInt(matcher.group(this.postIdIndex));


            //process the date
            dateStr = matcher.group(this.dateIndex)
                    .replaceAll(",|'| an |at|on", "");
            
            pubDate = new StringToTime(dateStr);

            if (!this.folder.equals("notes")) {

                descriptionPost = matcher.group(this.contentIndex)
                        .replaceAll("&nbsp;", " ");

                linkPost = matcher.group(this.linkIndex);
                titlePost = matcher.group(this.titleIndex);
            } else {
                String temp[] = matcher.group(noteContentIndex).split(">>");
                titlePost = temp[0];
                linkPost = "/notes/item/" + idPost;

                //check for additional content
                if (matcher.group(additionalNoteContentIndex) != null) {
                    descriptionPost = matcher.group(additionalNoteContentIndex)
                            .replaceAll("&nbsp;", " ");
                } else {
                    descriptionPost = matcher.group(noteContentIndex)
                            .replaceAll("&nbsp;", " ");
                }
            }

            //check for comments
            Matcher commentMatcher = patternComment.matcher(cleanedStr);
            ArrayList<RSSItemComment> commentList = new ArrayList();

            int index = 0;
            while (commentMatcher.find()) {
                authorComment = commentMatcher.group(this.authorCommentIndex);
                urlComment = commentMatcher.group(this.urlCommentIndex);

                //process the date
                dateStr = commentMatcher.group(this.dateCommentIndex)
                        .replaceAll(",|'| an |at|on", "");


                dateComment =
                        new Date((new StringToTime(dateStr)).getTime() + (index * 1000));


                contentComment = commentMatcher.group(this.contentCommentIndex)
                        .replaceAll("&nbsp;", " ");

                //quote if exist
                if (commentMatcher.group(this.quoteAllIndex) != null) {

                    quoteAll = commentMatcher.group(this.quoteAllIndex)
                            .replaceAll("&nbsp;", " ");

                    Matcher quoteMatcher = patternQuote.matcher(quoteAll);

                    if (quoteMatcher.find()) {
                        quoteCommentAuthor = quoteMatcher.group(quoteAuthorIndex);
                        quoteCommentContent = quoteMatcher.group(quoteContentIndex);
                    }

                }

                commentList.add(new RSSItemComment(index, authorComment, contentComment,
                        quoteCommentAuthor, quoteCommentContent, dateComment, urlComment));
                index++;
            }
            //end processing comments

            //store the object
            items.add(new RSSItem(titlePost, linkPost, pubDate, descriptionPost,
                    authorPost, idPost, commentList));

        }

        return items;
    }
}
