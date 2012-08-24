/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package febi.rssgen.com.rss;

/**
 *
 * @author itrc169
 */
public class RSSCategory extends RSSTerm{
    
    public RSSCategory(String category){
        super(category, RSSTerm.CATEGORY);
        
    }
    
    @Override
    public String getFormatted(){
        String ret = "<wp:category>"
                //+ "<wp:term_id>3</wp:term_id>"
                + "<wp:category_nicename>"+getNicename()+"</wp:category_nicename>"
                + "<wp:category_parent></wp:category_parent>"
                + "<wp:cat_name><![CDATA["+getName()+"]]></wp:cat_name>"
                + "</wp:category>";
        return ret;
    }
    
    @Override
    public String toString(){
        return getFormatted();
    }
}
