package controller;

import model.Project;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class ProjectController extends Controller<Project> {


    public ProjectController(String urlAdress) {

        super(urlAdress);
    }

    public void deleteProject(String name) {

    }

    public void renameProject(String original, String newName) {

    }

    public void addNewProject(String name) {

    }

    //todo to be deleted
    @Override
    public List<Project> getList() {
        ArrayList<Project> l = new ArrayList<>();
        Project p1 = new Project();
        p1.setName("Porject1");
        Project p2 = new Project();
        p2.setName("Sth2");
        l.add(p1);
        l.add(p2);
        Task t = new Task();
        t.setName("abc");
        ArrayList<Task> tl = new ArrayList<>();
        tl.add(t);
        p1.setTasks(tl);
        return l;
    }
}
