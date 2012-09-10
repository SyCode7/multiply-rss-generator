/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import febi.com.log.Logger;
import febi.rssgen.com.multiply.gui.TaskItem;
import febi.rssgen.com.multiply.gui.TaskViewer;
import febi.rssgen.multiply.MultiplyRSSGeneratorHandler;
import java.util.ArrayList;

/**
 *
 * @author itrc169
 */
public class SimpleTest {

    public static void main(String args[]) {

        Logger.init();

        MultiplyRSSGeneratorHandler.startProcess(
                "havban",
                true,
                false,
                false,
                false,
                "/home/itrc169/public_html/havban/",
                null,
                null,
                null,
                new TaskViewer() {
                    TaskItem dummy = new TaskItem("dummy", "", null);

                    @Override
                    public void updateStatus(TaskItem item) {
                    }

                    @Override
                    public ArrayList<TaskItem> getTaskList() {
                        return null;
                    }

                    @Override
                    public TaskItem getTaskByName(String name) {
                        return dummy;
                    }
                });


    }
}
