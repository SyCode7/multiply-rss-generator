/*
 * This file is part of MultiplyRSSGenerator.
 * 
 * MultiplyRSSGenerator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Hidayat Febiansyah
 * @email havban@gmail.com
 * @link http://code.google.com/p/multiply-rss-generator/
 * @year 2012
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
