package febi.rssgen.com.rss;

import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author itrc169
 */
public abstract class RSSGenerator {

    public static final String title = "title";
    public static final String link = "link";
    public static final String date = "pubDate";
    public static final String description = "description";
    public static final String author = "author";
    public static final String id = "postId";
    public static final String comments = "comments";
    //quote stuffs
    public static final String quoteAuthor = "qAuthor";
    public static final String quoteContent = "qContent";
    protected String blogTitle = "Default";
    protected String srcLink = "";
    protected String itemSearchPattern = "";
    protected String rssString = "";
    protected String _encoding = "UTF-8";
    protected String _language = "en-us";
    protected String _generator = "febiansyah_rss_generator_v"+Global.VERSION;
    protected String _version = "2.0";
    private static String _header = null;
    private static String _footer = null;

    public RSSGenerator(String srcLink, String blogTitle, String itemSearchPattern){
        this.srcLink = srcLink;
        this.blogTitle = blogTitle;
        this.itemSearchPattern = itemSearchPattern;
    }
    
    public void setItemSearchPattern(String pattern) {
        this.itemSearchPattern = pattern;
    }

    public String getItemSearchPattern() {
        return this.itemSearchPattern;
    }

    public String getHeader() {
        if (RSSGenerator._header == null) {
            RSSGenerator._header = "<?xml version=\"1.0\" encoding=\"" + this._encoding + "\"?>\n"
                    + "<rss version=\"2.0\"\n"
                    + "\txmlns:excerpt=\"http://wordpress.org/export/1.2/excerpt/\"\n"
                    + "\txmlns:content=\"http://purl.org/rss/1.0/modules/content/\"\n"
                    + "\txmlns:wfw=\"http://wellformedweb.org/CommentAPI/\"\n"
                    + "\txmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n"
                    + "\txmlns:wp=\"http://wordpress.org/export/1.2/\"\n"
                    + ">\n"
                    + "\t<channel>\n"
                    + "\t\t<title><![CDATA[" + this.blogTitle + "]]></title>\n"
                    + "\t\t<description><![CDATA[" + this.blogTitle + " RSS's data]]></description>\n"
                    + "\t\t<link>" + this + this.srcLink + "</link>\n"
                    + "\t\t<language>" + this._language + "</language>\n"
                    + "\t\t<wp:wxr_version>1.1</wp:wxr_version>\n"
                    + "\t\t<generator>" + this._generator + "</generator>\n";
        }
        return RSSGenerator._header;
    }

    public String getFooter() {
        if (RSSGenerator._footer == null) {
            _footer = "\t</channel>\n"
                    + "</rss>\n";
        }
        return RSSGenerator._footer;
    }
    
    public abstract ArrayList<RSSTerm> getParsedItems(String rawString);
    
    public abstract String getRSSItems( ArrayList<RSSTerm> items);

}