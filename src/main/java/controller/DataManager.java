package controller;

import model.Category;
import model.Project;
import model.Task;

public class DataManager {
    private static DataManager manager;
    private ProjectController projectController = new ProjectController("http://google.com/");
    private CategoriesController categoryController;
    private TaskController taskController;
    private AuthController authController = new AuthController();

    private DataManager() {}

    public static DataManager getManager() {
        if(manager == null) {
            manager = new DataManager();
        }
        return manager;
    }

    public ProjectController getProjectController() {
        return projectController;
    }

    public CategoriesController getCategoryController() {
        return categoryController;
    }

    public TaskController getTaskController() {
        return taskController;
    }

    public AuthController getAuthController() { return authController; }
}
