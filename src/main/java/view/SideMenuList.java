package view;

import app.AppConfig;
import model.Categories;
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
                //TODO
//                initNewList(PrioritiesList.getList());
            }
            else {
//                PrioritiesList.getList().discard();
            }
        });

        setAction(text.getString("friends"), (selected) -> {
            if(selected) {
                //TODO
            }
        });

        setAction(text.getString("settings"), (selected) -> {
            if(selected) {
                showSettings();
            }
        });

    }

    private void showSettings() {
        JComboBox language = new JComboBox(getReadableLanguageList());
        JComboBox skin = new JComboBox(config.skinList().split(","));
        Object[] fields = {
                text.getString("selectLanguage"),
                language,
                text.getString("selectSkin"),
                skin,
        };
        int buttonClicked = JOptionPane.showConfirmDialog(MainPanel.getMainPanel(), fields, text.getString("settings"),
                JOptionPane.OK_CANCEL_OPTION);
        if(buttonClicked == JOptionPane.OK_OPTION) {
            String unformattedLocale = config.languageList().split(",")[language.getSelectedIndex()];
            String[] localeAttributes = getLocaleAttributesFromProperty(unformattedLocale);
            changeLanguage(localeAttributes);
        }
    }

    private void changeLanguage(String[] localeAttributes) {
        if(localeAttributes.length == 2) {
            String[] currentLocale = new String[] {config.language(), config.country()};
            Properties props = new Properties();
            FileInputStream in = null;
            FileOutputStream out = null;
            try {
                in = new FileInputStream(getClass().getResource("/app/AppConfig.properties").getFile());
                props.load(in);
                System.out.println(props);
                in.close();

                out = new FileOutputStream(getClass().getResource("/app/AppConfig.properties").getFile());

                if(!currentLocale[0].equals(localeAttributes[0]) && !currentLocale[1].equals(localeAttributes[1])) {
                    System.out.println(props.setProperty("language", localeAttributes[0]));
                    props.setProperty("country", localeAttributes[1]);
                    System.out.println(props);
                    props.store(out, null);
                    out.close();
                    //TODO restart app here
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            //TODO kill app here
        }
    }

    private String[] getReadableLanguageList() {
        String[] temp = config.languageList().split(",");
        String[] languageList = new String[temp.length];
        for(int i = 0; i < temp.length; i++) {
            languageList[i] = temp[i].split("\\(")[0];
        }
        return languageList;
    }

    private String[] getLocaleAttributesFromProperty(String property) {
        String regex = "^[a-zA-Z]+\\(([a-zA-Z]{2}):([a-zA-Z]{2})\\)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(property);
        String[] localeAttributes = new String[2];
        if(matcher.find()) {
            localeAttributes[0] = matcher.group(1);
            localeAttributes[1] = matcher.group(2);
            return localeAttributes;
        }
        return null;
    }

    private void initNewList(MenuList list) {
        int fieldHeight = getHeight() / getFields().length;
        int newListHeight = (int) (fieldHeight * list.getFields().length * 0.8);
        int yPosition = 0;//fieldHeight;
        list.setListDimensions(getWidth(), yPosition, (int) (getWidth() * 0.8), newListHeight);
        list.show();
    }
}
