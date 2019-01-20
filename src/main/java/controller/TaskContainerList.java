package controller;

import app.AppConfig;
import model.Project;
import org.aeonbits.owner.ConfigFactory;
import view.MenuList;
import view.TaskList;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class TaskContainerList extends MenuList {

    protected AppConfig config = ConfigFactory.create(AppConfig.class);

    public TaskContainerList(JPanel panel, String[] fields) {
        super(panel, fields);
    }

    protected void initNewTaskList(TaskList taskList) {

        int taskMenuWidth = (int)(config.windowWidth()*0.25);
        int taskWidth = config.windowWidth() - taskMenuWidth - config.windowWidth()/6;
        int taskHeight = config.windowHeight();

        taskList.setBounds(config.windowWidth()/6,
                0,
                taskWidth,
                taskHeight);
    }

    public void checkIfShouldBeClosed(Point point) {
        if(list.isVisible()) {
            if(canBeHidden)
                discard();
            else
                canBeHidden = true;
        }
    }
}
