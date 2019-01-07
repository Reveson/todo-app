package view;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChecklistCreation extends JComponent {
    private List<ChecklistCreationRow> checkFields = new ArrayList<>();
    private int rowWidth, rowHeight;
    private JButton applyButton;

    public ChecklistCreation(JButton applyButton) {
        this.applyButton = applyButton;
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
        applyButton.setLocation(applyButton.getX(), applyButton.getY()+rowHeight);
        addListeners(newRow);
        SwingUtil.repaintParent(this);
    }


    private void addListeners(ChecklistCreationRow row) {
        JTextField txtField = row.getTextfield();
        JButton btn = row.getxButton();
        txtField.addActionListener((event) -> {
            addRow();
            txtField.removeActionListener(txtField.getActionListeners()[0]);
            btn.setEnabled(true);
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
            applyButton.setLocation(applyButton.getX(), applyButton.getY()-rowHeight);
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


    private class ChecklistCreationRow extends JComponent {
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
}
