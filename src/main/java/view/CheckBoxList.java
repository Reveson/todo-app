package view;

import app.AppConfig;
import javafx.scene.control.CheckBox;
import model.Task;
import org.aeonbits.owner.ConfigFactory;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@SuppressWarnings("serial")
public class CheckBoxList extends JList<JCheckBox> {
    private static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
    private DefaultListModel<JCheckBox> model;
    private List<Task> listOfTasks = new ArrayList<>();
    private AppConfig config = ConfigFactory.create(AppConfig.class);
    private ResourceBundle text = ResourceBundle
            .getBundle("lang", new Locale(config.language(), config.country()));

    private CheckBoxList() {
        setCellRenderer(new CellRenderer());
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int index = locationToIndex(e.getPoint());
                if (index != -1) {
                    switch(e.getClickCount()) {
                        case 1:
                            //there is no need for using loop, if indexes are equal
                            String checkBoxText = getModel().getElementAt(index).getText();
                            System.out.println("AAA:"+checkBoxText);
                            if(index < listOfTasks.size() && checkBoxText.equals(listOfTasks.get(index).getName())) {
                                showTaskDetails(listOfTasks.get(index));
                            }
                            else if(checkBoxText.equals(text.getString("addNewTask"))) {
                                addNewTask();
                            }
                            else {
                                for(Task task : listOfTasks) {
                                    if(checkBoxText.equals(task.getName())) {
                                        showTaskDetails(task);
                                    }
                                }
                            }

                            break;
                        case 2:
                            JCheckBox checkbox = (JCheckBox) getModel().getElementAt(index);
                            checkbox.setSelected(!checkbox.isSelected());
                            repaint();
                            break;
                        default:
                    }
                }
            }
        });
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void showTaskDetails(Task task) {
        int taskMenuWidth = (int)(config.windowWidth()*0.25);
        int taskMenuHeight = config.windowHeight();
        TaskMenu taskMenu = TaskMenu.getTaskMenu(task);
        taskMenu.setBounds(config.windowWidth()-taskMenuWidth,
                0,
                taskMenuWidth,
                taskMenuHeight);
        taskMenu.init();
    }

    private void addNewTask() {
        JTextField taskName = new JTextField();
        Object[] fields = {
                text.getString("newTaskPrompt"),
                " ",
                text.getString("taskNameInput"), taskName,
        };

        int buttonClicked = JOptionPane.showConfirmDialog(this, fields, text.getString("newTaskPrompt"),
                JOptionPane.OK_CANCEL_OPTION);
        if(buttonClicked == JOptionPane.DEFAULT_OPTION && !taskName.getText().equals("")) {
            //TODO add new task here
        }
        else {
            showErrorMessage();
        }
    }

    private void showErrorMessage() {
        JOptionPane.showMessageDialog(this, text.getString("addingTaskFailed"));
    }
    private CheckBoxList(ListModel<JCheckBox> model){
        this();
        setModel(model);
        JCheckBox newTaskButton = new JCheckBox(text.getString("addNewTask"));
        this.model.addElement(newTaskButton);
    }

    @Override
    public void setModel(ListModel<JCheckBox> model) {
        super.setModel(model);
        this.model = (DefaultListModel<JCheckBox>)model;
    }

    public static CheckBoxList getNewList() {
        DefaultListModel<JCheckBox> model = new DefaultListModel<>();
        return new CheckBoxList(model);
    }

    public void addElement(Task task) {
        if(model.getSize() > 0)
            model.removeElementAt(model.getSize()-1); //remove "add new task" button so this button will be the last one
        listOfTasks.add(task);
        model.addElement(new JCheckBox(task.getName()));
        JCheckBox newTaskButton = new JCheckBox(text.getString("addNewTask"));
        this.model.addElement(newTaskButton);
    }

    protected class CellRenderer implements ListCellRenderer<JCheckBox> {

        public Component getListCellRendererComponent(
                JList<? extends JCheckBox> list, JCheckBox value, int index,
                boolean isSelected, boolean cellHasFocus) {
            JCheckBox checkbox = value;

            //Drawing checkbox, change the appearance here
            checkbox.setBackground(isSelected ? getSelectionBackground()
                    : getBackground());
            checkbox.setForeground(isSelected ? getSelectionForeground()
                    : getForeground());
            checkbox.setEnabled(isEnabled());
            checkbox.setFont(getFont());
            checkbox.setFocusPainted(false);
            checkbox.setBorderPainted(true);
            checkbox.setBorder(isSelected ? UIManager
                    .getBorder("List.focusCellHighlightBorder") : noFocusBorder);
            Icon normal = createImageIcon("/img/checkbox-outline-blank.svg.png", "checkbox-outline", checkbox.getFont().getSize());
            Icon selected = createImageIcon("/img/checkbox-outline.svg.png", "checkbox-outline-blank", checkbox.getFont().getSize());

            checkbox.setIcon(normal);
            checkbox.setSelectedIcon(selected);

            return checkbox;
        }
    }


    private ImageIcon createImageIcon(String path,
                                      String description, int size) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            //TODO is there a simpler way?
            ImageIcon icon = new ImageIcon(imgURL, description);
            Image img = icon.getImage().getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(img);

        } else {
            //TODO should be in LOG
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}