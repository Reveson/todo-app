package view;

import app.AppConfig;
import org.aeonbits.owner.ConfigFactory;
import org.jdesktop.swingx.JXLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class TaskNote extends JComponent {
    private String text;
    private String author;
    private Instant creationDate;
    private JXLabel usernameArea;
    private JXLabel dateArea;
    private JXLabel textArea;
    private AppConfig config = ConfigFactory.create(AppConfig.class);

    public TaskNote(String author, Instant date, String text) {
        this.author = author;
        this.creationDate = date;
        this.text = text;
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        usernameArea = new JXLabel("<html><u>"+author+"</u></html>");
        dateArea = new JXLabel(formatedDate(creationDate));
        dateArea.setForeground(Color.lightGray);
        textArea = new JXLabel(text);
        textArea.setLineWrap(true);
        add(usernameArea, BorderLayout.PAGE_START);
        add(dateArea, BorderLayout.BEFORE_LINE_BEGINS);
        add(textArea, BorderLayout.PAGE_END);
    }

    private String formatedDate(Instant date) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( new Locale(config.language(), config.country()) )
                        .withZone( ZoneId.systemDefault() );
        return formatter.format(date);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        textArea.setText(text);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        usernameArea.setText("<html><u>"+author+"</u></html>");
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
        dateArea.setText(formatedDate(creationDate));
    }
}
