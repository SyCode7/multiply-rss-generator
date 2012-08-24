/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package febi.rssgen.com.multiply.gui;

/**
 *
 * @author itrc169
 */
public class TaskItem {
    
    private String name;
    private String desc;
    private boolean done;
    private TaskViewer viewer;
    
    private int status;
    
    public final static int ONGOING=0;
    public final static int DONE=1;
    public final static int ERROR=2;
    
    public TaskItem(String name, String desc, TaskViewer viewer){
        this.name = name;
        this.desc = desc;
        this.viewer = viewer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        
        //trigger the message
        if(viewer!=null) {
            viewer.updateStatus(this);
        }
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        
        if(status == ERROR){
            setDone(false);
        }else{
            setDone(true);
        }
    }
    
}
