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

    }

    private void initListHide() {
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if(event instanceof MouseEvent){
                    MouseEvent evt = (MouseEvent)event;
                    if(evt.getID() == MouseEvent.MOUSE_CLICKED){
//                        System.out.println("mouse clicked at: " + evt.getPoint());
                        ProjectsList.getList().checkIfShouldBeClosed(evt.getPoint());
                        CategoriesList.getList().checkIfShouldBeClosed(evt.getPoint());
                        PrioritiesList.getList().checkIfShouldBeClosed(evt.getPoint());
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
    }
}
