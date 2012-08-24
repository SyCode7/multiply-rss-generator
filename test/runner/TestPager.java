/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import febi.com.log.Logger;
import febi.rssgen.com.multiply.rss.MultiplyPageProcessor;

/**
 *
 * @author itrc169
 */
public class TestPager {
    public static void main(String args[]){
        MultiplyPageProcessor rssgen = new MultiplyPageProcessor("http://jaraway.multiply.com", "notes", null);
        Logger.init();
        
        rssgen.getPageResults();
    }
}
