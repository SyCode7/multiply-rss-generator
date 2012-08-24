/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package febi.rssgen.multiply;

import febi.com.log.Logger;
import febi.rssgen.com.multiply.gui.MainFrame;
import java.util.Locale;
import javax.swing.JFrame;

/**
 *
 * @author itrc169
 */
public class Main {
    
    public static void main(String args[]){
        Logger.init();
        
        Logger.outputMessage("Program started.");
        
        //set locale
         Locale.setDefault(Locale.ENGLISH);
        
        JFrame frame = new MainFrame();
        
        frame.setVisible(true);
    }
}
