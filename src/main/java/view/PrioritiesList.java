package view;

import controller.DataManager;
import controller.TaskContainerList;
import model.Category;
import model.Task;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PrioritiesList extends TaskContainerList {
    private static PrioritiesList prioritiesList;
    private static String[] priorities = {"★", "★★", "★★★", "★★★★", "★★★★★"};
    private static ArrayList<ArrayList<Task>> tasksWithPriorities = new ArrayList<>();

    public static PrioritiesList initList(JPanel panel) {

        //TODO to be uncommented
//        for(int i = 0; i< 5; i++) {
//            tasksWithPriorities.add(new ArrayList<>(DataManager.getManager().getTaskController().getListWithPriorities(i)));
//        }
        prioritiesList = new PrioritiesList(panel, priorities);
        return prioritiesList;
    }

    public static PrioritiesList getList() {
        if(prioritiesList == null) throw new NullPointerException("You need to init list first!");
        return prioritiesList;
    }
    private PrioritiesList(JPanel panel, String[] fields) {
        super(panel, fields);
        initListFields();
    }

    protected void initListFields() {
        for(int i = 0; i < priorities.length; i++) {
            final int final_i = i;
            setAction(priorities[i], (selected) -> {
                if (selected) {
                    initNewTaskList(TaskList.init(tasksWithPriorities.get(final_i)));
                }
            });

        }
    }
}
