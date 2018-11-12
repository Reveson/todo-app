import javax.swing.*;

public class ProjectsList extends MenuList {
    private static ProjectsList projectsList;

    //TODO z properties
    private static String[] fields = new String[] {"P1", "Pr2", "Project3"};

    public static ProjectsList initList(JPanel panel) {
        projectsList = new ProjectsList(panel, fields);
        return projectsList;
    }

    public static ProjectsList getList() {
        if(projectsList == null) throw new NullPointerException("You need to init list first!");
        return projectsList;
    }
    private ProjectsList(JPanel panel, String[] fields) {
        super(panel, fields);
        //initListFields();
    }

}
