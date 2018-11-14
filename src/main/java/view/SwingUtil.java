package view;

//package com.javadocking.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;


/**
 * This class contains a collection of static utility methods for Swing.
 *
 * @author Heidi Rakels.
 */
public class SwingUtil
{

    // Public static methods.

    /**
     * Repaints the parent of the given component. If the parent is null, the component itself is repainted.
     *
     * @param   component    The component whose parent will be repainted.
     */
    public static void repaintParent(JComponent component)
    {

        // Get the parent of the component.
        JComponent parentComponent = (JComponent)SwingUtilities.getAncestorOfClass(JComponent.class, component);

        // Could we find a parent?
        if (parentComponent != null)
        {
            // Repaint the parent.
            repaint(parentComponent);
        }
        else
        {
            // Repaint the component itself.
            repaint(component);
        }

    }

    private static void repaint(JComponent c) {
        c.revalidate();
        c.repaint();
    }
}
