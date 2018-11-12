import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    Logger logger = LoggerFactory.getLogger(MainPanel.class);
    private static MainPanel mainPanel;
    //TODO te parametry mają być z pliku properrties!
    private static int WINDOW_WIDTH = 1024;
    private static int WINDOW_HEIGHT = 720;

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
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        logger.debug("Window dimensions set to {} x {}.", WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(null);
    }

    private void initSideMenuList() {
        SideMenuList.initList(this).setListDimensions(0,0, WINDOW_WIDTH/6, WINDOW_HEIGHT/2);
    }

    private void initCalendar() {
        //TODO
    }

    private void initUserBtn() {
        //TODO
    }
}
