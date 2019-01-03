package view;

import app.NoSuchListElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.function.Consumer;

public abstract class MenuList {

    Logger logger = LoggerFactory.getLogger(getClass());
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
        logger.info("Initializing new list.");
        this.fields = Arrays.copyOf(fields, fields.length);
        actions = new Consumer[this.fields.length];
        list = new JList(fields);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Font currentFont = list.getFont();
        list.setFont(new Font(currentFont.getName(), currentFont.getStyle(), 4*6));
        addMouseListener();
        panel.add(list);
//        panel.repaint();
        logger.debug("New list with fields: {} added to JPanel.", (Object)fields);
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

    private void addMouseListener() {
        list.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int index = list.locationToIndex(e.getPoint());
                if (index != -1) {
//                    for(int i = 0; i < 2; i++) {
//                        if(i != index) {
//                            actions[i].accept(false);
//                        }
//                    }
                    for(int i = 0; i < 2; i++) {
                        Boolean selected = list.isSelectedIndex(i);
                        actions[i].accept(selected);
                    }
                }
            }
        });
        logger.debug("Added mouse listener.");

//        list.addListSelectionListener((e) -> {
//            if(!e.getValueIsAdjusting()) {
//                int index = e.getFirstIndex();
//                if(list.isSelectedIndex(index)) {
//                    index = e.getLastIndex();
//                }
//                if(!list.isSelectedIndex(index)) {
//                    actions[index].accept(false);
//                }
//
//            }
//        });
        logger.debug("Added list selection listener.");
    }

    protected void discard() {
        list.setVisible(false);
        logger.info("list elements: "+list.getModel().getElementAt(0));
        logger.info("Removed list from JPanel.");
        //TODO to be deleted
    }

    protected void show() {
        list.setVisible(true);
        logger.info("Added list to JPanel.");
    }


    public void setAction(String field, Consumer<Boolean> action) {
        for(int i = 0; i < fields.length; i++) {
            if(field.compareTo(fields[i]) == 0) {
                actions[i] = action;
                logger.debug("New action successfully added to {}.", field);
                return;
            }
        }
        throw new NoSuchListElementException("There's no \""+field+"\" element in the list.");
    }

}
