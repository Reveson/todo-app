package view;

import app.AppConfig;
import app.TodoHelper;
import controller.DataManager;
import model.Category;
import model.Project;
import org.aeonbits.owner.ConfigFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Settings {
    private static Settings settings;

    private static AppConfig config = ConfigFactory.create(AppConfig.class);
    private static ResourceBundle text = ResourceBundle
            .getBundle("lang", new Locale(config.language(), config.country()));

    public static Settings getInstance() {
        if(settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    public void showSettings() {
        JComboBox language = new JComboBox(getReadableLanguageList());
        JComboBox skin = new JComboBox(config.skinList().split(","));
        JButton projectsButton = new JButton(text.getString("manageProjects"));
        JButton categoriesButton = new JButton(text.getString("manageCategories"));

        projectsButton.addActionListener(this::manageprojectsListener);
        categoriesButton.addActionListener(this::manageCategoriesListener);
        Object[] fields = {
                text.getString("selectLanguage"),
                language,
                text.getString("selectSkin"),
                skin,
                " ",
                projectsButton,
                categoriesButton,
                " "
        };
        int buttonClicked = JOptionPane.showConfirmDialog(MainPanel.getMainPanel(), fields, text.getString("settings"),
                JOptionPane.OK_CANCEL_OPTION);
        if(buttonClicked == JOptionPane.OK_OPTION) {
            changeLanguage(language.getSelectedIndex());
        }
    }

    private void manageprojectsListener(ActionEvent e) {
        ArrayList<Project> projects;
        //TODO to be uncommented
//        projects = new ArrayList<>(DataManager.getManager().getProjectController().getList());
        Project p[] = new Project[] {new Project(), new Project(), new Project()};
        p[0].setName("123");
        p[1].setName("Project 11");
        p[2].setName("lol");
        projects = new ArrayList<Project>(Arrays.asList(p));

        String[] projectNames = new String[projects.size()];
        int i = 0;
        for(Project project : projects) {
            projectNames[i++] = project.getName();
        }
        JComboBox projectsList = new JComboBox(projectNames);
        JTextField textFieldChangeName = new JTextField();
        JTextField textFieldAddNew = new JTextField();
        JButton delete = new JButton(text.getString("delete"));

        delete.addActionListener((e2 -> {
            textFieldChangeName.setEnabled(false);
            textFieldAddNew.setEnabled(false);
            delete.setEnabled(false);
            projectsList.setEnabled(false);
        }));

        Object[] fields = {
                projectsList,
                " ",
                delete,
                " ",
                text.getString("changeName"),
                textFieldChangeName,
                " ",
                text.getString("addNew"),
                textFieldAddNew
        };
        int buttonClicked = JOptionPane.showConfirmDialog(MainPanel.getMainPanel(), fields, text.getString("settings"),
                JOptionPane.OK_CANCEL_OPTION);
        if(buttonClicked == JOptionPane.OK_OPTION) {
            if(!projectsList.isEnabled()) { //delete button hit
                DataManager.getManager().getProjectController().deleteProject(projectsList.getSelectedItem().toString());
            }
            else {
                if(textFieldAddNew.getText() != null && !textFieldAddNew.getText().equals("")) {
                    DataManager.getManager().getProjectController().addNewProject(textFieldAddNew.getText());
                }
                if(textFieldChangeName.getText() != null && !textFieldChangeName.getText().equals("")) {
                    DataManager.getManager().getProjectController().renameProject(projectsList.getSelectedItem().toString(), textFieldChangeName.getText());
                }
            }
        }

    }

    private void manageCategoriesListener(ActionEvent e) {
        ArrayList<Category> categories;
        //TODO to be uncommented
        categories = new ArrayList<>(DataManager.getManager().getCategoryController().getList());

        String[] categoriesNames = new String[categories.size()];
        int i = 0;
        for(Category category : categories) {
            categoriesNames[i++] = category.getName();
        }
        JComboBox categoriesList = new JComboBox(categoriesNames);
        JTextField textFieldChangeName = new JTextField();
        JTextField textFieldAddNew = new JTextField();
        JButton delete = new JButton(text.getString("delete"));

        delete.addActionListener((e2 -> {
            delete.setEnabled(false);
            textFieldChangeName.setEnabled(false);
            textFieldAddNew.setEnabled(false);
            categoriesList.setEnabled(false);
        }));

        Object[] fields = {
                categoriesList,
                " ",
                delete,
                " ",
                text.getString("changeName"),
                textFieldChangeName,
                " ",
                text.getString("addNew"),
                textFieldAddNew
        };
        int buttonClicked = JOptionPane.showConfirmDialog(MainPanel.getMainPanel(), fields, text.getString("settings"),
                JOptionPane.OK_CANCEL_OPTION);
        if(buttonClicked == JOptionPane.OK_OPTION) {
            if(!categoriesList.isEnabled()) { //delete button hit
                DataManager.getManager().getCategoryController().deleteCategory(categoriesList.getSelectedItem().toString());
            }
            else {
                if(textFieldAddNew.getText() != null && !textFieldAddNew.getText().equals("")) {
                    DataManager.getManager().getCategoryController().addNewCategory(textFieldAddNew.getText());
                }
                if(textFieldChangeName.getText() != null && !textFieldChangeName.getText().equals("")) {
                    DataManager.getManager().getCategoryController().renameCategory(categoriesList.getSelectedItem().toString(), textFieldChangeName.getText());
                }
            }
        }

    }


    private void changeLanguage(int index) {
        String unformattedLocale = config.languageList().split(",")[index];
        String[] localeAttributes = getLocaleAttributesFromProperty(unformattedLocale);
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


                if(!currentLocale[0].equals(localeAttributes[0]) && !currentLocale[1].equals(localeAttributes[1])) {
                    out = new FileOutputStream(getClass().getResource("/app/AppConfig.properties").getFile());
                    System.out.println(props.setProperty("language", localeAttributes[0]));
                    props.setProperty("country", localeAttributes[1]);
                    System.out.println(props);
                    props.store(out, null);
                    out.close();
                    showRestartPrompt();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            //TODO add error message
            TodoHelper.shutDownTheApp();
        }
    }

    private void showRestartPrompt() {
        Object[] fields = {
                text.getString("restartQuestion"),
        };
        int buttonClicked = JOptionPane.showConfirmDialog(MainPanel.getMainPanel(), fields, text.getString("settings"),
                JOptionPane.OK_CANCEL_OPTION);
        if(buttonClicked == JOptionPane.OK_OPTION) {
            TodoHelper.shutDownTheApp();
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
}
