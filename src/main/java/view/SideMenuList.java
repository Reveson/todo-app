package view;

import app.AppConfig;
import app.TodoHelper;
import org.aeonbits.owner.ConfigFactory;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        ProjectsList.initList(panel);
        CategoriesList.initList(panel);
        PrioritiesList.initList(panel);
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
                initNewList(ProjectsList.getList());
            }
            else {
                ProjectsList.getList().discard();
            }
        });

        setAction(text.getString("categories"), (selected) -> {
            if(selected) {
                initNewList(CategoriesList.getList());
            }
            else {
                CategoriesList.getList().discard();
            }
        });

        setAction(text.getString("priorities"), (selected) -> {
            if(selected) {
                initNewList(PrioritiesList.getList());
            }
            else {
                PrioritiesList.getList().discard();
            }
        });

        setAction(text.getString("friends"), (selected) -> {
            if(selected) {
                //TODO
            }
        });

        setAction(text.getString("settings"), (selected) -> {
            if(selected) {
                Settings.getInstance().showSettings();
            }
        });

    }



    private void initNewList(MenuList list) {
        int fieldHeight = getHeight() / getFields().length;
        int newListHeight = (int) (fieldHeight * list.getFields().length * 0.8);
        int yPosition = 0;//fieldHeight;
        list.setListDimensions(getWidth(), yPosition, (int) (getWidth() * 0.8), newListHeight);
        list.show();
    }
}
