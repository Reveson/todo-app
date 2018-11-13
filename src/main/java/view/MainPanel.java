package view;

import app.AppConfig;
import org.aeonbits.owner.ConfigFactory;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXMonthView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        //TODO
//        ChecklistCreationRow checklistRow = new ChecklistCreationRow();
//        checklistRow.setBounds(400,400,200,50);
//        add(checklistRow);
        ChecklistCreation cList = new ChecklistCreation();
        add(cList);
        cList.setBounds(400,400,200,30);
    }
}
