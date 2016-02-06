// LICENSE
package org.abos.schemes.gui;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.JPanel;

/**
 * Provides methods to create often needed GUI elements.
 * 
 * @author Sebastian Koch
 * @version 1.0.0
 * @since 1.0.0 
 */
public class GUIFactory {

    /**
     * Let no one instantiate this class.
     * 
     * @since 1.0.0
     */
    private GUIFactory() {}
    
    /**
     * Takes several buttons and puts them aligned in one row into a panel.
     * @param buttons The buttons to align. <code>null</code> entries will be
     * ignored.
     * @return a panel containing all given (meaningful) buttons.
     * 
     * @since 1.0.0
     */
    public static JPanel createButtonPanel(AbstractButton... buttons) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,0));
        for (AbstractButton btn : buttons)
            if (btn != null)
                panel.add(btn);
        return panel;
    }
    
    // TODO Think of better method names
    
    /**
     * Creates constraints for a GridBagLayout often used for labels
     * on the left side of the panel/window/frame. No weights, only scales 
     * horizontal, insets left and right, anchored at west (right).
     * @param x the x-coordinate of the component
     * @param y the y-coordinate of the component
     * @param width the width of the component
     * @return grid bag constraints fulfilling the aforementioned conditions
     * 
     * @since 1.0.0
     */
    // Javadoc invalid values?
    public static GridBagConstraints createLeftJLabelConstraint(int x, int y, 
            int width) {
        GridBagConstraints gbc = new GridBagConstraints(x, y, width,
            1, 0.0, 0.0, GridBagConstraints.EAST, 
            GridBagConstraints.HORIZONTAL, new Insets(0,1,0,1), 0, 0);
        return gbc;
    }
    
    /**
     * Creates constraints for a GridBagLayout often used for labels
     * at the center of the panel/window/frame. No weights, only scales 
     * horizontal, anchored at center, insets left and right, no padding.
     * @param x the x-coordinate of the component
     * @param y the y-coordinate of the component
     * @param width the width of the component
     * @return grid bag constraints fulfilling the aforementioned conditions
     * 
     * @since 1.0.0
     */
    // Javadoc invalid values?
    public static GridBagConstraints createCenteredJLabelConstraint(int x,  
            int y, int width) {
        GridBagConstraints gbc = new GridBagConstraints(x, y, width,
            1, 0.0, 0.0, GridBagConstraints.CENTER, 
            GridBagConstraints.NONE, new Insets(0,1,0,1), 0, 0);
        return gbc;
    }
    
    /**
     * Creates constraints for a GridBagLayout often used for text fields
     * on the right of the panel/window/frame. No weights, only scales 
     * horizontal, anchored at east (left), complete insets, no padding.
     * @param x the x-coordinate of the component
     * @param y the y-coordinate of the component
     * @param width the width of the component
     * @return grid bag constraints fulfilling the aforementioned conditions
     * 
     * @since 1.0.0
     */
    // Javadoc invalid values?
    public static GridBagConstraints createRightJTextFieldConstraint(int x,  
            int y, int width) {
        GridBagConstraints gbc = new GridBagConstraints(x, y, width,
            1, 0.0, 0.0, GridBagConstraints.WEST, 
            GridBagConstraints.HORIZONTAL, new Insets(1,1,1,1), 0, 0);
        return gbc;
    }
    
    /**
     * Creates constraints for a GridBagLayout often used for lists
     * at the center of the panel/window/frame. No weights, scales in every
     * direction, anchored at center, complete insets, no padding.
     * @param x the x-coordinate of the component
     * @param y the y-coordinate of the component
     * @param width the width of the component
     * @return grid bag constraints fulfilling the aforementioned conditions
     * 
     * @since 1.0.0
     */
    // Javadoc invalid values?
    public static GridBagConstraints createCenteredJListConstraint(int x, int y, 
            int width) {
        GridBagConstraints gbc = new GridBagConstraints(x, y, width,
            1, 0.0, 0.0, GridBagConstraints.CENTER, 
            GridBagConstraints.BOTH, new Insets(1,1,1,1), 0, 0);
        return gbc;
    }
    
    /**
     * Creates constraints for a GridBagLayout often used for panels
     * containing buttons
     * at the center of the panel/window/frame. No weights, scales 
     * horizontally, anchored at center, no insets, complete padding.
     * @param x the x-coordinate of the component
     * @param y the y-coordinate of the component
     * @param width the width of the component
     * @return grid bag constraints fulfilling the aforementioned conditions
     * 
     * @since 1.0.0
     */
    // Javadoc invalid values?
    public static GridBagConstraints createCenteredButtonPanelConstrait(int x, 
            int y, int width) {
        GridBagConstraints gbc = new GridBagConstraints(x, y, width,
                1, 0.0, 0.0, GridBagConstraints.CENTER, 
                GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 1, 1);
            return gbc;
    }

}
