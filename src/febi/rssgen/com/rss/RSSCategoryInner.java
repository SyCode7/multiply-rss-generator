/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package febi.rssgen.com.rss;

/**
 *
 * @author itrc169
 */
public class RSSCategoryInner extends RSSCategory{

    public RSSCategoryInner(String category) {
        super(category);
    }

    @Override
    public String getFormatted() {
        String ret = "<category domain=\"" + getTypeString()
                + "\" nicename=\"" + getNicename() + "\"><![CDATA[" + getName()
                + "]]></category>\n";
        return ret;
    }
}
