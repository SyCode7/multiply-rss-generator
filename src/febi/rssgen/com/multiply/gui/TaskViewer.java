/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package febi.rssgen.com.multiply.gui;

import java.util.ArrayList;

/**
 *
 * @author itrc169
 */
public interface TaskViewer {
    
    public void updateStatus(TaskItem item);
    
    public ArrayList<TaskItem> getTaskList();
    
    public TaskItem getTaskByName(String name);
    
}
