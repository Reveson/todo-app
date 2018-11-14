package view;

import app.AppConfig;
import org.aeonbits.owner.ConfigFactory;
import org.jdesktop.swingx.JXLabel;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.List;

public class TaskChecklist extends JComponent {
    private String author;
    private Instant creationDate;
    private String[] list;
    private JXLabel usernameArea;
    private JXLabel dateArea;
    private List<JCheckBox> checkboxList;
    private AppConfig config = ConfigFactory.create(AppConfig.class);

    public TaskChecklist(String author, Instant date, String[] list) {
        this.author = author;
        this.creationDate = date;
        this.list = Arrays.copyOf(list, list.length);
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        usernameArea = new JXLabel("<html><u>"+author+"</u></html>");
        dateArea = new JXLabel(formatedDate(creationDate));
        dateArea.setForeground(Color.lightGray);
        add(usernameArea, BorderLayout.PAGE_START);
        add(dateArea, BorderLayout.BEFORE_LINE_BEGINS);
        initializeList(this.list);
    }

    private String formatedDate(Instant date) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( new Locale(config.language(), config.country()) )
                        .withZone( ZoneId.systemDefault() );
        return formatter.format(date);
    }

    private void initializeList(String[] list) {
        checkboxList = new ArrayList<>();
        JComponent checkListComp = new JComponent() {};
        checkListComp.setLayout(new BoxLayout(checkListComp, BoxLayout.Y_AXIS));
        for(String elem : list) {
            JCheckBox cBox = new JCheckBox(elem);
            checkboxList.add(cBox);
            checkListComp.add(cBox);
        }
        this.add(checkListComp, BorderLayout.PAGE_END);

    }
}
