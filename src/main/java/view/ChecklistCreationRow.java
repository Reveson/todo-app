package view;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXTextField;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ChecklistCreationRow extends JComponent {
    private JTextField textfield;
    private JButton xButton;

    public ChecklistCreationRow() {
        textfield = new JTextField();
        xButton = new JButton("X");
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        xButton.setEnabled(false);
        this.add(textfield);
        this.add(xButton);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x,y,width,height);

        Font oldFont = textfield.getFont();
        Font scaledFont = new Font(oldFont.getName(), oldFont.getStyle(), height/2);
        textfield.setFont(scaledFont);

        xButton.setSize(xButton.getWidth(),height);
        xButton.setMaximumSize(xButton.getSize());
    }

    public JTextField getTextfield() {
        return textfield;
    }

    public JButton getxButton() {
        return xButton;
    }

}
