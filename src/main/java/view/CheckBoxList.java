package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;

@SuppressWarnings("serial")
public class CheckBoxList extends JList<JCheckBox> {
    protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

    public CheckBoxList() {
        setCellRenderer(new CellRenderer());
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int index = locationToIndex(e.getPoint());
                if (index != -1) {
                    JCheckBox checkbox = (JCheckBox) getModel().getElementAt(index);
                    checkbox.setSelected(!checkbox.isSelected());
                    repaint();
                }
            }
        });
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public CheckBoxList(ListModel<JCheckBox> model){
        this();
        setModel(model);
    }

    protected class CellRenderer implements ListCellRenderer<JCheckBox> {

        public Component getListCellRendererComponent(
                JList<? extends JCheckBox> list, JCheckBox value, int index,
                boolean isSelected, boolean cellHasFocus) {
            JCheckBox checkbox = value;

            //Drawing checkbox, change the appearance here
            checkbox.setBackground(isSelected ? getSelectionBackground()
                    : getBackground());
            checkbox.setForeground(isSelected ? getSelectionForeground()
                    : getForeground());
            checkbox.setEnabled(isEnabled());
            checkbox.setFont(getFont());
            checkbox.setFocusPainted(false);
            checkbox.setBorderPainted(true);
            checkbox.setBorder(isSelected ? UIManager
                    .getBorder("List.focusCellHighlightBorder") : noFocusBorder);
            Icon normal = createImageIcon("/img/checkbox-outline-blank.svg.png", "checkbox-outline", checkbox.getFont().getSize());
            Icon selected = createImageIcon("/img/checkbox-outline.svg.png", "checkbox-outline-blank", checkbox.getFont().getSize());

            checkbox.setIcon(normal);
            checkbox.setSelectedIcon(selected);

            return checkbox;
        }
    }


    private ImageIcon createImageIcon(String path,
                                      String description, int size) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            //TODO is there a simpler way?
            ImageIcon icon = new ImageIcon(imgURL, description);
            Image img = icon.getImage().getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(img);

        } else {
            //TODO should be in LOG
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}