/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package febi.rssgen.com.rss;

/**
 *
 * @author itrc169
 */
public class RSSTag extends RSSTerm{
    
    public RSSTag(String tag){
        super(tag, RSSTerm.TAG);
    }
    
    @Override
    public String getFormatted(){
        String ret = "<wp:tag>"
                //+ "<wp:term_id>27</wp:term_id>"
                + "<wp:tag_slug>"+getNicename()+"</wp:tag_slug>"
                + "<wp:tag_name><![CDATA["+getName()+"]]>"
                + "</wp:tag_name></wp:tag>";
        return ret;
    }
    
    @Override
    public String toString(){
        return getFormatted();
    }
}
