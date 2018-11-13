package view;

import model.ChecklistRow;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChecklistCreation extends JComponent {
    private List<ChecklistCreationRow> checkFields = new ArrayList<>();
    private int rowWidth, rowHeight;

    public ChecklistCreation() {
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        this.setLayout(new FlowLayout());
        //this.setLayout(new BorderLayout());
        this.setLayout(null);
        addRow();
    }

    private void addRow() {
        ChecklistCreationRow newRow = new ChecklistCreationRow();
        newRow.setBounds(0, checkFields.size()*rowHeight,rowWidth,rowHeight);
        checkFields.add(newRow);
        this.add(newRow, BorderLayout.PAGE_END);
        this.setSize(getWidth(),getHeight()+rowHeight);
        this.setMaximumSize(this.getSize());
        addListeners(newRow);
        repaintParent(this);
    }

    private void addListeners(ChecklistCreationRow row) {
        JTextField txtField = row.getTextfield();
        JButton btn = row.getxButton();
        txtField.addActionListener((event) -> {
                    if(txtField.getText() != null || txtField.getText() != "") {
                        addRow();
                        txtField.removeActionListener(txtField.getActionListeners()[0]);
                        btn.setEnabled(true);
                    }
                });
        btn.addActionListener((event) -> {
            int indexOfRemovedRow = checkFields.indexOf(row);
            for(int i = checkFields.size()-1 ; i > indexOfRemovedRow ; i--) {
                checkFields.get(i).setLocation(checkFields.get(i-1).getLocation());
            }
            this.remove(row);
            this.repaint();
            checkFields.remove(indexOfRemovedRow);
            this.setSize(getWidth(),getHeight()-rowHeight);
        });
    }

    public void setRowSize(int width, int height) {
        rowWidth=width;
        rowHeight=height;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x,y,width,height);

        int fields = checkFields.size();
        int rowHeight = fields == 0 ? height : height/fields;
        for(int i = 0; i < fields; i++) {
            int yPos = i*rowHeight;
            checkFields.get(i).setBounds(0, yPos, width, rowHeight);
        }
        setRowSize(width, rowHeight);
    }

    private void repaintParent(JComponent component)
    {

        // Get the parent of the component.
        JComponent parentComponent = (JComponent)SwingUtilities.getAncestorOfClass(JComponent.class, component);

        // Could we find a parent?
        if (parentComponent != null)
        {
            // Repaint the parent.
            parentComponent.revalidate();
            parentComponent.repaint();
        }
        else
        {
            // Repaint the component itself.
            component.revalidate();
            component.repaint();
        }

    }
}
