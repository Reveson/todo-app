import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.function.Consumer;

public abstract class MenuList {

    private JList list;
    public final JPanel panel;
    private int width;
    private int height;
    private String[] fields;
    private Consumer[] actions;

    protected MenuList(JPanel panel, String[] fields) {
        this.panel = panel;
        init(fields);
    }

    protected void init(String[] fields) {
        this.fields = Arrays.copyOf(fields, fields.length);
        actions = new Consumer[this.fields.length];
        list = new JList(fields);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Font currentFont = list.getFont();
        list.setFont(new Font(currentFont.getName(), currentFont.getStyle(), 4*6));
        addMouseListener();
        panel.add(list);
    }

    protected void setListDimensions(int x, int y, int width, int height) {
        if(list == null) throw new NullPointerException("You need to initialize list first!");
        this.width = width;
        this.height = height;
        list.setFixedCellHeight(height/list.getModel().getSize());
        list.setBounds(x,y,width,height);
    }

    public int getWidth() {

        return width;
    }

    public int getHeight() {

        return height;
    }

    public String[] getFields() {
        return Arrays.copyOf(fields, fields.length);
    }

    protected void addMouseListener() {
        list.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int index = list.locationToIndex(e.getPoint());
                if (index != -1) {
                    Boolean selected = list.isSelectedIndex(index);
                    actions[index].accept(selected);
                }
            }
        });
        list.addListSelectionListener((e) -> {
            if(e.getValueIsAdjusting() == false) {
                int index = e.getFirstIndex();
                if(list.isSelectedIndex(index)) {
                    index = e.getLastIndex();
                }
                if(!list.isSelectedIndex(index)) {
                    System.out.println(index);
                    actions[index].accept(false);
                }

            }
        });
    }

    protected void discard() {
        panel.remove(list);
        panel.repaint();
    }


    public void setAction(String field, Consumer<Boolean> action) {
        for(int i = 0; i < fields.length; i++) {
            if(field.compareTo(fields[i]) == 0) {
                actions[i] = action;
                return;
            }
        }
        throw new NoSuchListElementException("There's no \""+field+"\" element in the list.");
    }

}
