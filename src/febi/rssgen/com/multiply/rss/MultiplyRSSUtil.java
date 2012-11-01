/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package febi.rssgen.com.multiply.rss;

/**
 *
 * @author itrc169
 */
public class MultiplyRSSUtil {
    
    //the extension for images
    private static String attachmentSuffix = "&amp;nmid=0";
    private static String attachmentExtension = "&.jpg";
    private static String attachmentSuffixShort = "&nmid=0";
    private static String attachmentExtensionShort = "&.jpg";
    
    //preprocess image links from multiply
    public static String getGoodImageLink(String url) {
        String newImageLink = url.replaceAll(attachmentSuffix, attachmentExtension);
        newImageLink = newImageLink.replaceAll(attachmentSuffixShort, attachmentExtensionShort);
        return newImageLink;
    }
}
