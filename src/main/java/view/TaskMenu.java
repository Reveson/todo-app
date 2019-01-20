package view;

import app.AppConfig;
import app.WrongDateFormatException;
import controller.DataManager;
import model.Project;
import model.Task;
import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskMenu extends JPanel {

    private static TaskMenu taskMenu;
    private static Task task;

    private JLabel taskName;

    private JButton deadlineBox;
    private JButton timeNeededBox;
    private JButton repeatEveryBox;
    private JButton projectBox;
    private JButton categoryBox;
    private JButton priorityBox;

    private JButton editButton;
    private JButton deleteButton;

    private JButton textButton;
    private JButton checklistButton;
    private JTextArea textInput;
    private ChecklistCreation checklistCreation;
    private JButton taskInfoAddButton;

    private int upperMenuYCoordEnd;
    private int updateDeleteCoordEnd;

    private AppConfig config = ConfigFactory.create(AppConfig.class);
    private ResourceBundle text = ResourceBundle
            .getBundle("lang", new Locale(config.language(), config.country()));
    Logger logger = LoggerFactory.getLogger(MainPanel.class);

    public static TaskMenu getTaskMenu(Task task) {
        if (TaskMenu.task != task || TaskMenu.task == null) { //this is correct, it should be the same (!) object, not just equal
            if(taskMenu != null)
                MainPanel.getMainPanel().remove(TaskMenu.taskMenu);
            TaskMenu.taskMenu = new TaskMenu(task);
            MainPanel.getMainPanel().add(taskMenu);
            MainPanel.getMainPanel().revalidate();
            MainPanel.getMainPanel().repaint();
        }
        return taskMenu;
    }

    private TaskMenu(Task task) {
        //TODO check if null
        TaskMenu.task = task;
        //TODO this needs to be removed, its just for visual testing
        setBackground(Color.gray);
        this.setLayout(null);
    }

    public void init() {
        initTitle();
        initUpperMenu();
        initUpdateDeleteMenu();
        initMiddleMenu();
    }

    private void initTitle() {
        String htmlText = "<html><div style='text-align: center;'>" + task.getName() + "</div></html>";
        taskName = new JLabel(htmlText, SwingConstants.CENTER);
        int menuWidth = this.getWidth();
        int yBorder = (int)(menuWidth*0.15);
        Font oldFont = taskName.getFont();
        //TODO scale this font according to project name lenght
        Font scaledFont = new Font(oldFont.getName(), oldFont.getStyle(), (int)(yBorder*0.8));
        taskName.setBounds(0,0,menuWidth,yBorder);
        taskName.setFont(scaledFont);
        this.add(taskName);
    }

    private void initUpperMenu() {
        deadlineBox = new JButton(text.getString("deadline"));
        timeNeededBox = new JButton(text.getString("timeNeeded"));
        repeatEveryBox = new JButton(text.getString("repeatEvery"));
        projectBox = new JButton(text.getString("project"));
        categoryBox = new JButton(text.getString("category"));
        priorityBox = new JButton(text.getString("priority"));



        int menuWidth = this.getWidth();
        int componentWidth = (int)(menuWidth*0.34);
        int componentHeight = (int)(menuWidth*0.1);
        int firstColumnX = (int)(menuWidth*0.15);
        int gap = menuWidth - (componentWidth + firstColumnX)*2;
        int secondColumnX = firstColumnX+componentWidth+gap;
        int firstRowY = firstColumnX;
        int secondRowY = firstRowY+componentHeight+gap;
        int thirdRowY = secondRowY+componentHeight+gap;
        upperMenuYCoordEnd = thirdRowY + componentHeight;

        deadlineBox.setBounds(firstColumnX, firstRowY, componentWidth, componentHeight);
        timeNeededBox.setBounds(firstColumnX, secondRowY, componentWidth, componentHeight);
        repeatEveryBox.setBounds(firstColumnX, thirdRowY, componentWidth, componentHeight);

        projectBox.setBounds(secondColumnX, firstRowY, componentWidth, componentHeight);
        categoryBox.setBounds(secondColumnX, secondRowY, componentWidth, componentHeight);
        priorityBox.setBounds(secondColumnX, thirdRowY, componentWidth, componentHeight);

        setListenersForUpperMenu();

        this.add(deadlineBox);
        this.add(timeNeededBox);
        this.add(repeatEveryBox);
        this.add(projectBox);
        this.add(categoryBox);
        this.add(priorityBox);
    }

    private void initUpdateDeleteMenu() {
        editButton = new JButton(text.getString("editButton"));
        deleteButton = new JButton(text.getString("deleteButton"));

        int menuWidth = this.getWidth();
        int componentWidth = (int)(menuWidth*0.40);
        int componentHeight = (int)(menuWidth*0.09);
        int menuYCoord = upperMenuYCoordEnd + componentHeight;
//        int menuXCoord = (menuWidth - componentWidth*2)/2;
        int menuXCoord = (int)((menuWidth/2 - componentWidth)*0.9);
        updateDeleteCoordEnd = menuYCoord+componentHeight;

        editButton.setBounds(menuXCoord, menuYCoord, componentWidth, componentHeight);
        deleteButton.setBounds(menuWidth-menuXCoord-componentWidth, menuYCoord, componentWidth, componentHeight);

        setListenersForUpdateDeleteMenu();

        this.add(editButton);
        this.add(deleteButton);
    }

    public void setListenersForUpdateDeleteMenu() {
        editButton.addActionListener(this::editButtonListener);
        deleteButton.addActionListener(this::deleteButtonListener);
    }

    private void editButtonListener(ActionEvent e) {
        JTextField textField = new JTextField();
        Object[] fields = {
                text.getString("askForNewName"),
                textField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, text.getString("editButton"), JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION) {
            if(textField.getText() != null && !textField.getText().equals("")) {
                DataManager.getManager().getTaskController().updateTask(task, textField.getText());
            }
        }

    }

    private void deleteButtonListener(ActionEvent e) {
        Object[] fields = {
                text.getString("confirmDelete"),
        };

        int result = JOptionPane.showConfirmDialog(this, fields, text.getString("deleteButton"), JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION) {
            DataManager.getManager().getTaskController().deleteTask(task);
        }
    }

    private void initMiddleMenu() {
        textButton = new JButton(text.getString("taskMenuTxtButton"));
        checklistButton = new JButton(text.getString("taskMenuListButton"));
        taskInfoAddButton = new JButton();
        textInput = new JTextArea();
        checklistCreation = new ChecklistCreation(taskInfoAddButton);


        int menuWidth = this.getWidth();
        int componentWidth = (int)(menuWidth*0.45);
        int componentHeight = (int)(menuWidth*0.08);
        int menuYCoord = updateDeleteCoordEnd + componentHeight;
        int menuXCoord = (menuWidth - componentWidth*2)/2;

        textButton.setBounds(menuXCoord, menuYCoord, componentWidth, componentHeight);
        checklistButton.setBounds(menuXCoord+componentWidth, menuYCoord, componentWidth, componentHeight);

        textInput.setBounds(menuXCoord,menuYCoord+componentHeight, componentWidth*2, componentHeight*4);
        textInput.setLineWrap(true);
        textInput.setVisible(false);

        checklistCreation.setBounds(menuXCoord,menuYCoord+componentHeight, componentWidth*2, componentHeight);
        checklistCreation.setVisible(false);

        taskInfoAddButton.setBounds(menuXCoord+(int)(1.3*componentWidth), menuYCoord, (int)(componentWidth*0.7), componentHeight);
        taskInfoAddButton.setText(text.getString("taskMenuInfoApply"));
        taskInfoAddButton.setVisible(false);

        this.add(textButton);
        this.add(checklistButton);
        this.add(textInput);
        this.add(checklistCreation);
        this.add(taskInfoAddButton);

        setListenersForMiddleMenu();

    }

    private void setListenersForMiddleMenu() {
        textButton.addActionListener(this::textButtonListener);
        checklistButton.addActionListener(this::checklistButtonListener);
        taskInfoAddButton.addActionListener(this::taskInfoAddButtonListener);
    }

    private void textButtonListener(ActionEvent e) {
        if(textInput.isVisible()) {
            textInput.setVisible(false);
            taskInfoAddButton.setVisible(false);

        }
        else {
            checklistCreation.setVisible(false);
            textInput.setVisible(true);
            taskInfoAddButton.setVisible(true);
            taskInfoAddButton.setLocation(taskInfoAddButton.getX(), textInput.getY()+textInput.getHeight());
        }
    }

    private void checklistButtonListener(ActionEvent e) {
        if(checklistCreation.isVisible()) {
            checklistCreation.setVisible(false);
            taskInfoAddButton.setVisible(false);
        }
        else {
            textInput.setVisible(false);
            checklistCreation.setVisible(true);
            taskInfoAddButton.setLocation(taskInfoAddButton.getX(), checklistCreation.getY()+checklistCreation.getHeight());
            taskInfoAddButton.setVisible(true);

        }

    }

    private void taskInfoAddButtonListener(ActionEvent e) {
        if(checklistCreation.isVisible()) { //add checklist
            ArrayList<String> list = checklistCreation.getChecklistFields();
            if(list.size() > 0) {
                DataManager.getManager().getTaskController().addChecklist(task, list);
                checklistCreation.reset();
                checklistButtonListener(null);
            }
        }
        else if(textInput.isVisible())  { //add comment
            String text = textInput.getText();
            if(text != null && !text.equals("")) {
                DataManager.getManager().getTaskController().addComment(task, text);
                textInput.setText("");
                textButtonListener(null); //to hide the input
            }
        }
    }

    private void setListenersForUpperMenu() {
        deadlineBox.addActionListener(this::deadlineBoxListener);
        timeNeededBox.addActionListener(this::timeNeededBoxListener);
        repeatEveryBox.addActionListener(this::repeatEveryBoxListener);
        projectBox.addActionListener(this::projectBoxListener);
        categoryBox.addActionListener(this::categoryBoxListener);
        priorityBox.addActionListener(this::priorityBoxListener);
    }

    private void deadlineBoxListener(ActionEvent e) {
        String deadline = showPopupDialog(text.getString("deadline"),text.getString("deadlineInstruction"), null);
        Date deadlineDate; //TODO
        boolean passedRightOrExited = false;
        while(!passedRightOrExited) {
            try {
                passedRightOrExited = true;
                if(deadline != null) { //it happens when user clicks "cancel"
                    deadlineDate = getTypedInDate(deadline);
                    DataManager.getManager().getTaskController().setDeadline(task, deadlineDate);

                }
            }
            catch (WrongDateFormatException exception) {
                logger.debug(exception.getMessage());
                passedRightOrExited = false;
                deadline = showPopupDialog(text.getString("deadline"),exception.getType().getDescription(), null);
            }

        }

    }
    private void timeNeededBoxListener(ActionEvent e) {
        String timeNeeded = showPopupDialog(text.getString("timeNeeded"),text.getString("timeNeededInstruction"), null);
        if(timeNeeded != null ) {
            try {
                int time = Integer.valueOf(timeNeeded);
                DataManager.getManager().getTaskController().setTimeNeeded(task, time);
            }
            catch (NumberFormatException ne) {
                JOptionPane.showMessageDialog(this, text.getString("wrongNumberFormat"));
            }
        }

    }
    private void repeatEveryBoxListener(ActionEvent e) {
        JTextField startDate = new JTextField();
        JTextField repeatDate = new JTextField();
        Object[] fields = {
                text.getString("repeatEveryInstruction"),
                " ",
                "start Date:", startDate,
                "Repeat Date:", repeatDate
        };

        JOptionPane.showConfirmDialog(this, fields, text.getString("repeatEvery"), JOptionPane.OK_CANCEL_OPTION);
        //TODO calculate here when task should be repeated
    }

    private void projectBoxListener(ActionEvent e) {
        String[] projectsNames = null; //TODO
        ArrayList<Project> projectList = new ArrayList<>(DataManager.getManager().getProjectController().getList());
        projectsNames = new String[projectList.size()];
        int i  = 0;
        for(Project project : projectList) {
            projectsNames[i++] = project.getName();
        }
        String chosenProject = showPopupDialog(text.getString("project"),null, projectsNames);
        if(chosenProject != null) {
            DataManager.getManager().getTaskController().setProject(task, chosenProject);
        }
    }
    private void categoryBoxListener(ActionEvent e) {
        String[] categories = null; //TODO
        categories = new String[] {"cat1", "category2", "dsfsdf", "categoryyyy"}; //TODO do usunięcia
        String chosenCategory = showPopupDialog(text.getString("category"),null, categories);
        if(chosenCategory != null) {
            DataManager.getManager().getTaskController().setCategory(task, chosenCategory);
        }

    }
    private void priorityBoxListener(ActionEvent e) {
        String[] priorities = new String[] {"★", "★★", "★★★", "★★★★", "★★★★★"};
        String chosenPriority = showPopupDialog(text.getString("priority"),null, priorities);
        if(chosenPriority != null) {
            int priorityLevel = chosenPriority.length();
            DataManager.getManager().getTaskController().setPriority(task, priorityLevel);
        }
    }


    private String showPopupDialog(String parent, String instruction, String[] options) {
        return (String)JOptionPane.showInputDialog(
                this,
                instruction,
                parent,
                 JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
    }

    private Date getTypedInDate(String date) throws WrongDateFormatException {
        String regex = "^ *(\\d{1,2}):(\\d{2})( +(\\d{1,2})-(\\d{1,2})-(\\d{4}))? *$";
        Matcher matcher = Pattern.compile(regex).matcher(date);
        if(matcher.find()) {
            int hours = Integer.valueOf(matcher.group(1));
            int minutes = Integer.valueOf(matcher.group(2));
            String s;
            Integer day = (s = matcher.group(4)) != null ? Integer.valueOf(s) : null;
            Integer month = (s = matcher.group(5)) != null ? Integer.valueOf(s) : null;
            Integer year = (s = matcher.group(6)) != null ? Integer.valueOf(s) : null;

            if(hours > 24 || minutes > 60) {
                throw new WrongDateFormatException(WrongDateFormatException.DateFormatException.WRONG_TIME);
            }
            if(day != null) {
                if(day > 31 || month > 12) {
                    throw new WrongDateFormatException(WrongDateFormatException.DateFormatException.WRONG_DATE);
                }
            }

            Date deadline;
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, 0);

            if(day != null) { //it mean that user has set time AND(!) date
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);
            }

            deadline = calendar.getTime();

            if(deadline.before(new Date())) { //deadline must be after "now"
                throw new WrongDateFormatException(WrongDateFormatException.DateFormatException.DATE_BEFORE_NOW);
            }

            return deadline;

        }
        throw new WrongDateFormatException(WrongDateFormatException.DateFormatException.WRONG_FORMAT);
    }

}
