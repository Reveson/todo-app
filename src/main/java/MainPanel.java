import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    Logger logger = LoggerFactory.getLogger(MainPanel.class);
    AppConfig config = ConfigFactory.create(AppConfig.class);

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
        SideMenuList.initList(this).setListDimensions(0,0, config.windowWidth()/6, config.windowHeight()/2);
    }

    private void initCalendar() {
        //TODO
    }

    private void initUserBtn() {
        //TODO
    }
}
