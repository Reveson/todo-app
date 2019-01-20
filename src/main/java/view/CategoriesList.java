package view;

import controller.DataManager;
import controller.TaskContainerList;
import model.Category;
import model.Project;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriesList extends TaskContainerList {
    private static CategoriesList categoriesList;
    private static List<Category> categories;

    public static CategoriesList initList(JPanel panel) {

        //TODO to be uncommented
        categories = new ArrayList<>();
        Category c = new Category();
        c.setName("Category a");
        categories.add(c);
//        categories = DataManager.getManager().getCategoryController().getList();


        String[] categoriesNames = getNamesFromList();
        categoriesList = new CategoriesList(panel, categoriesNames);
        return categoriesList;
    }

    public static CategoriesList getList() {
        if(categoriesList == null) throw new NullPointerException("You need to init list first!");
        return categoriesList;
    }
    private CategoriesList(JPanel panel, String[] fields) {
        super(panel, fields);
        initListFields();
    }

    private static String[] getNamesFromList() {
        String[] projectsNames = new String[categories.size()];
        int i = 0;
        for(Category category : categories) {
            projectsNames[i] = category.getName();
            i++;
        }
        return projectsNames;
    }

    protected void initListFields() {
        for(Category category : categories) {
            setAction(category.getName(), (selected) -> {
                if (selected) {
                    initNewTaskList(TaskList.init(category.getTasks()));
                }
            });

        }
    }
}
