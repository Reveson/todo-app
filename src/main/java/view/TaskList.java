package view;

import model.Project;
import model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class TaskList extends JPanel {

    private CheckBoxList checkBoxList;
    private JScrollPane scrollPane;
    private List<Task> tasks;
    private static TaskList taskList;
    private static Project project;
    private boolean withOptionNewTask = false;

    public static TaskList init(Project project) {
        TaskList.project = project;
        return initInner(project.getTasks());
    }

    public static TaskList init(List<Task> tasks) {
        TaskList.project = null;
        return initInner(tasks);
    }

    private static TaskList initInner(List<Task> tasks) {
        JPanel panel = MainPanel.getMainPanel();
        if(taskList != null) {
            panel.remove(taskList);
        }
        taskList = new TaskList(tasks);

        panel.add(taskList);
        panel.revalidate();
        panel.repaint();
        return TaskList.taskList;
    }

    public static TaskList getTaskList(Project project) {
        if(taskList != null) {
            return taskList;
        }
        throw new NullPointerException("You need to init TaskList first!");
    }

    private TaskList(List<Task> tasks) {
        if(this.project != null) {
            withOptionNewTask = true;
        }
        this.tasks = tasks;

        initAllComponents();
        initAllListFields();

        this.add(scrollPane);
        this.setLayout(null);
    }


    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x,y,width,height);
    //TODO check those font sizes
        Font oldFont = checkBoxList.getFont();
        Font scaledFont = new Font(oldFont.getName(), oldFont.getStyle(), height/15);
        checkBoxList.setFont(scaledFont);

        checkBoxList.setBounds(0  ,0,width,(int)(scaledFont.getSize()*(checkBoxList.getModel().getSize()*1.3)));
        scrollPane.setBounds(0,0,width,height);
    }

    private void initAllComponents() {
        if(withOptionNewTask) {
            checkBoxList = CheckBoxList.getNewList(project);
        }
        else {
            checkBoxList = CheckBoxList.getNewList();
        }

        JPanel panel = new JPanel();
//        panel.add(checkBoxList);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        panel.setLayout(new GridBagLayout());
        panel.add(checkBoxList, gbc);


        scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    }

    private void initAllListFields() {
        if(tasks != null && tasks.size() > 0) {
            for(Task task : tasks) {
                checkBoxList.addElement(task);
            }
        }
    }

}
