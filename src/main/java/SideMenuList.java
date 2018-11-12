import javax.swing.*;

public class SideMenuList extends MenuList {
    private static SideMenuList sideMenuList;

    //TODO z pliku properties
    private static String[] fields = new String[]{"Projects", "Categories", "Priority", "Friends", "Settings"};

    public static SideMenuList initList(JPanel panel) {
        sideMenuList = new SideMenuList(panel, fields);
        return sideMenuList;
    }

    public static SideMenuList getList() {
        if (sideMenuList == null) throw new NullPointerException("You need to initialize list first!");
        return sideMenuList;
    }

    private SideMenuList(JPanel panel, String[] fields) {
        super(panel, fields);
        initListFields();
    }

    private void initListFields() {
        setAction("Projects", (selected) -> {
            if(selected) {
                initNewList(ProjectsList.initList(panel));
            }
            else {
                ProjectsList.getList().discard();
                System.out.println("removing projects");
            }
        });

        setAction("Categories", (selected) -> {
            if(selected) {
                initNewList(CategoriesList.initList(panel));
            }
            else {
                CategoriesList.getList().discard();
                System.out.println("removing categories");
            }
        });

        setAction("Priority", (selected) -> {
            if(selected) {
                initNewList(CategoriesList.initList(panel));
            }
            else {
                CategoriesList.getList().discard();
                System.out.println("removing categories");
            }
        });
    }

    private void initNewList(MenuList list) {
        int fieldHeight = getHeight() / getFields().length;
        int newListHeight = (int) (fieldHeight * list.getFields().length * 0.8);
        int yPosition = 0;//fieldHeight;
        list.setListDimensions(getWidth(), yPosition, (int) (getWidth() * 0.8), newListHeight);

    }
}
