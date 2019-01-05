package view;

import model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class TaskList extends JPanel {

    private CheckBoxList checkBoxList;
    private JScrollPane scrollPane;
    private List<Task> taskList;

    public TaskList(List<Task> taskList) {

        this.taskList = taskList;

        initAllComponents();
        initAllListFields();
//
//        JCheckBox newCheckBox = new JCheckBox("testing here");
//
//        model.addElement(newCheckBox);
//        model.addElement(new JCheckBox("Checkbox1"));
//        model.addElement(new JCheckBox("Checkbox2"));
//        model.addElement(new JCheckBox("Checkbox3"));


        this.add(scrollPane);
        this.setLayout(null);
    }


    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x,y,width,height);

        Font oldFont = checkBoxList.getFont();
        Font scaledFont = new Font(oldFont.getName(), oldFont.getStyle(), height/15);
        checkBoxList.setFont(scaledFont);

        checkBoxList.setBounds(0  ,0,width,(int)(scaledFont.getSize()*(checkBoxList.getModel().getSize()*1.3)));
        scrollPane.setBounds(0,0,width,height);
    }

    private void initAllComponents() {
        checkBoxList = CheckBoxList.getNewList();

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
        if(taskList != null && taskList.size() > 0) {
            for(Task task : taskList) {
                checkBoxList.addElement(task);
                //TODO add functionallity to view task details
            }
        }
    }

}
