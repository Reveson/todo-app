import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
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
        setFocusable(true);
        setDoubleBuffered(true);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setAlignmentY(Component.TOP_ALIGNMENT);
//        setLayout(new FlowLayout(FlowLayout.LEFT));
//        setLayout(new GridBagLayout());
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
