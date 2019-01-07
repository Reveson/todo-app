package view;

import app.AppConfig;
import controller.Controller;
import model.Project;
import model.Task;
import org.aeonbits.owner.ConfigFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProjectsList extends MenuList {
    private static ProjectsList projectsList;
    private static Controller<Project> controller;
    private static String dbAdress = "http://a.pl"; //TODO
    private static List<Project> projects;
    AppConfig config = ConfigFactory.create(AppConfig.class);

    //TODO z web service
//    private static String[] fields = new String[] {"P1", "Pr2", "Project3"};

    public static ProjectsList initList(JPanel panel) {
        controller = new Controller<>(dbAdress);


        //TODO to be undone
//        try {
//            ProjectsList.projects = controller.getList();
//        } catch (IOException e) {
//            //TODO add popup that kills an app
//            e.printStackTrace();
//        }
        ProjectsList.projects = new ArrayList<>();
        Project p1 = new Project();
        Project p2 = new Project();
        Project p3 = new Project();
        p1.setName("Abc");
        List<Task> ts = new ArrayList<>();
        Task t1 = new Task();
        Task t2 = new Task();
        Task t3 = new Task();
        t1.setName("Tas1");
        t2.setName("Tas2");
        t3.setName("Tas3");
        ts.add(t1);
        ts.add(t2);
        ts.add(t3);
        p1.setTasks(ts);
        p2.setName("Abcdd");
        p3.setName("Abceeee");
        List<Task> ts2 = new ArrayList<>();
        Task t12 = new Task();
        t12.setName("Thats a very long task");
        ts2.add(t12);
        p3.setTasks(ts2);
        projects.add(p1);
        projects.add(p2);
        projects.add(p3);


        String[] projectsNames = getNamesFromList();

        ProjectsList.projectsList = new ProjectsList(panel, projectsNames);
        return projectsList;
    }

    public static ProjectsList getList() {
        if(projectsList == null) throw new NullPointerException("You need to init list first!");
        return projectsList;
    }

    public boolean updateList() { //true if it worked
        List<Project> tempList;
        try {
            tempList = controller.getList();
        } catch (IOException e) {
            return false;
        }

        String[] projectsNames = getNamesFromList();
        projects = tempList;
        refresh(projectsNames);
        return true;
    }

    private static String[] getNamesFromList() {
        String[] projectsNames = new String[projects.size()];
        int i = 0;
        for(Project project : projects) {
            projectsNames[i] = project.getName();
            i++;
        }
        return projectsNames;
    }

    private ProjectsList(JPanel panel, String[] fields) {
        super(panel, fields);
        initListFields();
    }

    private void initListFields() {
        for(Project project : projects) {
            setAction(project.getName(), (selected) -> {
                if (selected) {
                    initNewTaskList(TaskList.init(project.getTasks()));
                }
            });

        }
    }

    private void initNewTaskList(TaskList taskList) {

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
