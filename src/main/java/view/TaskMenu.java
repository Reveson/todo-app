package view;

import org.jdesktop.swingx.JXComboBox;

import javax.swing.*;
import java.awt.*;

public class TaskMenu extends JPanel {
    private JXComboBox deadlineBox;
    private JXComboBox timeNeededBox;
    private JXComboBox RepeatEveryBox;
    private JXComboBox projectBox;
    private JXComboBox categoryBox;
    private JXComboBox priorityBox;
    private JButton textButton;
    private JButton checklistButton;
    private JTextArea textInput;

    public TaskMenu() {
        //TODO this needs to be removed, its just for visual testing
        setBackground(Color.gray);
    }

}
