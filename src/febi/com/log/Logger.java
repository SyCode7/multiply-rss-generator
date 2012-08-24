/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package febi.com.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 *
 * @author itrc169
 */
public class Logger {

    public static final String FILENAME="MultiplyRSSGenerator.log";
    private static BufferedWriter buffWriter;
    private static int counter;
    private static Calendar cal;

    public static void init(){
        try {
            File f = new File(FILENAME);
            if(f.length() > 1000000)
                f.renameTo(new File(FILENAME+".old"));
            
            cal = Calendar.getInstance();
            
            buffWriter = new BufferedWriter(new FileWriter(FILENAME, true));
            buffWriter.write(cal.getTime().toString()+" : Starting..\n=====\n");
            buffWriter.flush();
            counter = 0;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void write(String str){
        try {
            if(counter > 50){
                buffWriter.write("== "+cal.getTime().toString()+" ==");
                buffWriter.flush();
            }
            
            buffWriter.write(str);
            counter++;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void close(){
        try {
            buffWriter.write(cal.getTime().toString()+" : Closing\n=====\n");
            buffWriter.flush();
            buffWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void outputMessage(String message) {
        Logger.write(message);
        System.out.print(message);
    }

    public static void outputMessageDone() {
        outputMessage(" done\n");
    }

    public static void outputMessageLn(String message) {
        Logger.write(message+"\n");
        System.out.print(message+"\n");
    }
    
    public static void dumpToFile(String filePath, String content){
        
        System.out.println("Dumping file: "+filePath);
        
        BufferedWriter buffWriter = null;
        try {
            buffWriter = new BufferedWriter(new FileWriter(filePath, false));
            buffWriter.write(content);
            buffWriter.flush();
            buffWriter.close();
        } catch (IOException ex) {
            Logger.outputMessageLn(ex.getMessage());
        } finally {
            try {
                buffWriter.close();
            } catch (IOException ex) {
                Logger.outputMessageLn(ex.getMessage());
            }
        }
    }
}