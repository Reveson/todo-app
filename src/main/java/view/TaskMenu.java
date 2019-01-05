package view;

import app.AppConfig;
import app.WrongDateFormatException;
import model.Task;
import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskMenu extends JPanel {

    private Task task;
    private JLabel taskName;

    private JButton deadlineBox;
    private JButton timeNeededBox;
    private JButton repeatEveryBox;
    private JButton projectBox;
    private JButton categoryBox;
    private JButton priorityBox;

    private JButton textButton;
    private JButton checklistButton;
    private JTextArea textInput;

    private AppConfig config = ConfigFactory.create(AppConfig.class);
    private ResourceBundle text = ResourceBundle
            .getBundle("lang", new Locale(config.language(), config.country()));
    Logger logger = LoggerFactory.getLogger(MainPanel.class);

    public TaskMenu(Task task) {
        //TODO check if null
        this.task = task;
        //TODO this needs to be removed, its just for visual testing
        setBackground(Color.gray);
        this.setLayout(null);
    }

    public void initUpperMenu() {
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
        System.out.println(firstColumnX);
        int secondColumnX = firstColumnX+componentWidth+gap;
        int firstRowY = firstColumnX;
        int secondRowY = firstRowY+componentHeight+gap;
        int thirdRowY = secondRowY+componentHeight+gap;

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
        if(timeNeeded != null) {

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
    }

    private void projectBoxListener(ActionEvent e) {
        String[] projects = null; //TODO
        projects = new String[] {"pro1", "project2", "pdfgdgmndfkgdjkgn", "project 3,5"}; //TODO do usunięcia
        String chosenProject = showPopupDialog(text.getString("project"),null, projects);
        if(chosenProject != null) {

        }
    }
    private void categoryBoxListener(ActionEvent e) {
        String[] categories = null; //TODO
        categories = new String[] {"cat1", "category2", "dsfsdf", "categoryyyy"}; //TODO do usunięcia
        String chosenCategory = showPopupDialog(text.getString("category"),null, categories);
        if(chosenCategory != null) {

        }

    }
    private void priorityBoxListener(ActionEvent e) {
        String[] priorities = new String[] {"★", "★★", "★★★", "★★★★", "★★★★★"};
        String chosenPriority = showPopupDialog(text.getString("priority"),null, priorities);
        if(chosenPriority != null) {
            int priorityLevel = chosenPriority.length();
            //TODO
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
