/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

/**
 *
 * @author itrc169
 */
public class TestReplace {
    
    public static void main (String args[]){
        
        String dodol="/////";
//        String oke = dodol.replaceAll("//", "/");
        String oke = dodol.replaceAll("\\W", "-");
        System.out.println(dodol+" "+oke);
    }
}
