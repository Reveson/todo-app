package view;

import app.AppConfig;
import org.aeonbits.owner.ConfigFactory;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class SideMenuList extends MenuList {
    private static SideMenuList sideMenuList;

    private static AppConfig config = ConfigFactory.create(AppConfig.class);
    private static ResourceBundle text = ResourceBundle
            .getBundle("lang", new Locale(config.language(), config.country()));
    private static String[] fields =
            new String[]{text.getString("projects"),
                    text.getString("categories"),
                    text.getString("priorities"),
                    text.getString("friends"),
                    text.getString("settings")};

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
        setAction(text.getString("projects"), (selected) -> {
            if(selected) {
                initNewList(ProjectsList.initList(panel));
            }
            else {
                ProjectsList.getList().discard();
            }
        });

        setAction(text.getString("categories"), (selected) -> {
            if(selected) {
                initNewList(CategoriesList.initList(panel));
            }
            else {
                CategoriesList.getList().discard();
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
