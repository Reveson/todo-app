package view;

import app.AppConfig;
import controller.DataManager;
import controller.TaskContainerList;
import model.Project;
import model.Task;
import org.aeonbits.owner.ConfigFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectsList extends TaskContainerList {
    private static ProjectsList projectsList;
    private static List<Project> projects;


    public static ProjectsList initList(JPanel panel) {

        //TODO uncomment when DB is set
        projects = DataManager.getManager().getProjectController().getList();


        //TODO To be deleted
//        ProjectsList.projects = new ArrayList<>();
//        Project p1 = new Project();
//        Project p2 = new Project();
//        Project p3 = new Project();
//        p1.setName("Abc");
//        List<Task> ts = new ArrayList<>();
//        Task t1 = new Task();
//        Task t2 = new Task();
//        Task t3 = new Task();
//        t1.setName("Tas1");
//        t2.setName("Tas2");
//        t3.setName("Tas3");
//        ts.add(t1);
//        ts.add(t2);
//        ts.add(t3);
//        p1.setTasks(ts);
//        p2.setName("Abcdd");
//        p3.setName("Abceeee");
//        List<Task> ts2 = new ArrayList<>();
//        Task t12 = new Task();
//        t12.setName("Thats a very long task");
//        ts2.add(t12);
//        p3.setTasks(ts2);
//        projects.add(p1);
//        projects.add(p2);
//        projects.add(p3);


        String[] projectsNames = getNamesFromList();

        ProjectsList.projectsList = new ProjectsList(panel, projectsNames);
        return projectsList;
    }

    public static ProjectsList getList() {
        if(projectsList == null) throw new NullPointerException("You need to init list first!");
        return projectsList;
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

    protected void initListFields() {
        for(Project project : projects) {
            setAction(project.getName(), (selected) -> {
                if (selected) {
                    initNewTaskList(TaskList.init(project));
                }
            });

        }
    }

}
