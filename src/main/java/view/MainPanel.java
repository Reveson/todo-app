package view;

import app.AppConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import controller.TaskController;
import model.Task;
import org.aeonbits.owner.ConfigFactory;
import org.jdesktop.swingx.JXMonthView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainPanel extends JPanel {
    Logger logger = LoggerFactory.getLogger(MainPanel.class);
    AppConfig config = ConfigFactory.create(AppConfig.class);
    private ResourceBundle text = ResourceBundle
            .getBundle("lang", new Locale(config.language(), config.country()));

    private static MainPanel mainPanel;

    private MainPanel() {

        initWindow();
        initSideMenuList();
        initCalendar();
        initUserBtn();
        initListHide();
    }

    public static MainPanel getMainPanel() {
        if(mainPanel == null) {
            mainPanel = new MainPanel();
        }
        return mainPanel;
    }

    private void initWindow() {
        setPreferredSize(new Dimension(config.windowWidth(), config.windowHeight()));
        logger.debug("Window dimensions set to {} x {}.", config.windowWidth(), config.windowHeight());
        setLayout(null);
    }

    private void initSideMenuList() {
        SideMenuList.initList(this).setListDimensions(0,0, config.windowWidth()/6, (int)(config.windowHeight()*0.6));
    }

    private void initCalendar() {
        //TODO
//        JXDatePicker picker = new JXDatePicker();
//        picker.setDate(Calendar.getInstance().getTime());
//        picker.setFormats(new SimpleDateFormat("dd~MM~yyyy"));
//        this.add(picker);
//        picker.setBounds(500,500,200,40);
        JXMonthView calendar = new JXMonthView();
        calendar.setLocale(new Locale(config.language(), config.country()));
        calendar.setDaysOfTheWeek(text.getString("daysOfWeak").split(","));
        calendar.setBounds(0,SideMenuList.getList().getHeight(),SideMenuList.getList().getWidth(),180);
        calendar.setBoxPaddingX(5);
        calendar.setSelectionDate(Calendar.getInstance().getTime());
        this.add(calendar);
    }


    private void initUserBtn() {
        //TODO make a user button, that redirects to user panel


        //TODO To be removed, just testing here
//        ChecklistCreation cList = new ChecklistCreation();
//        add(cList);
//        cList.setBounds(400,400,200,30);
//
//        TaskNote note = new TaskNote("Username", Instant.now(), config.lorem());
//        add(note);
//        note.setBounds(700,200,300,250);
//
//        TaskChecklist list =
//                new TaskChecklist("Username",
//                        Instant.now(),
//                        new String[] {"field1", "field2", "field3", "fielddd4"});
//        add(list);
//        list.setBounds(300,50,300,250);
//
//        Task task2 = new Task();
//        task2.setName("Project X");
//        TaskMenu taskMenu = new TaskMenu(task2);
//        int taskMenuWidth = (int)(config.windowWidth()*0.25);
//        int taskMenuHeight = config.windowHeight();
//        taskMenu.setBounds(config.windowWidth()-taskMenuWidth,
//                0,
//                taskMenuWidth,
//                taskMenuHeight);
//        taskMenu.init();
//        add(taskMenu);

//        ArrayList<Task> taskList = new ArrayList<>();
//        for(int i = 0; i < 4; i++) {
//            Task taskToAdd = new Task();
//            taskToAdd.setName("zadanie "+(i+1));
//            taskList.add(taskToAdd);
//        }
//        TaskList task = new TaskList(taskList);
//        int taskWidth = (int)(config.windowWidth() - taskMenuWidth - config.windowWidth()/6);
//        int taskHeight = config.windowHeight();
//        add(task);
//
//        task.setBounds(config.windowWidth()/6,
//                0,
//                taskWidth,
//                taskHeight);


    }

    private void initListHide() {
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if(event instanceof MouseEvent){
                    MouseEvent evt = (MouseEvent)event;
                    if(evt.getID() == MouseEvent.MOUSE_CLICKED){
                        System.out.println("mouse clicked at: " + evt.getPoint());
                        ProjectsList.getList().checkIfShouldBeClosed(evt.getPoint());
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
    }
}
