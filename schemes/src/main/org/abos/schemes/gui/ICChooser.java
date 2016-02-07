// LICENSE
package org.abos.schemes.gui;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.abos.schemes.InformationComponent;
import org.abos.schemes.InformationScheme;

/**
 * A dialog to select an information component of a given scheme
 * with help of a search field.
 * @author Sebastian Koch
 * @version 1.0.1
 * @since 1.0.0
 *
 * @see SelectiveICListModel
 */
@SuppressWarnings("serial")
public class ICChooser extends JDialog {
    
    /**
     * The scheme the components are selected from.
     * 
     * @since 1.0.0
     */
    protected InformationScheme<? extends InformationComponent> scheme = null;
    
    /**
     * The selective information component list model to filter the
     * scheme components.
     * 
     * @since 1.0.0
     */
    protected SelectiveICListModel listModel = null;
    
    /**
     * The information component that was selected by this dialog.
     * 
     * @since 1.0.0
     */
    protected InformationComponent selectedIC = null;

    /**
     * The label to describe the search field.
     * 
     * @since 1.0.0
     */
    protected JLabel labSearch = null;
    
    /**
     * The search field.
     * 
     * @since 1.0.0
     */
    protected JTextField txtSearch = null;

    /**
     * A check box to indicate if the search text should be taken as regular
     * expression.
     * 
     * @since 1.0.0
     * 
     * @see SelectiveICListModel#setRegex(boolean)
     */
    protected JCheckBox chkRegex = null;
    
    /**
     * A check box to indicate if the search text should be taken as case
     * sensitive.
     * 
     * @since 1.0.0
     * 
     * @see SelectiveICListModel#setCaseSensitive(boolean)
     */
    protected JCheckBox chkCaseSensitive = null;
    
    /**
     * A panel containing those two check boxes.
     * 
     * @since 1.0.0
     */
    protected JPanel pnlCheckBoxes = null;
    
    /**
     * The JList to display the search results.
     * 
     * @since 1.0.0
     */
    protected JList<InformationComponent> list = null;
    
    /**
     * The scroll pane to set the list into.
     * 
     * @since 1.0.0
     */
    protected JScrollPane scrList = null;
    
    /**
     * A button to cancel the selection process and close the dialog.
     * 
     * @since 1.0.0
     * 
     * @see #actCancel
     */
    protected JButton btnCancel = null;
    
    /**
     * A button to finish the selection process and close the dialog.
     * 
     * @since 1.0.0
     * 
     * @see #actChoose
     * @see #setSelectedIC(InformationComponent)
     */
    protected JButton btnChoose = null;
    
    /**
     * A panel containing these two buttons.
     * 
     * @since 1.0.0
     */
    protected JPanel pnlButtons = null;
    
    /**
     * The grid bag layout of the dialog.
     * 
     * @since 1.0.0
     */
    protected GridBagLayout gbl = null;
    
    /**
     * A self reference for inner anonymous classes.
     * 
     * @since 1.0.0
     */
    protected ICChooser that;
    
    /**
     * Constructs a new non-modal dialog with title "Choose Component".
     * @param owner the owner of the dialog
     * @param scheme the information scheme to search through
     * 
     * @since 1.0.0
     * 
     * @see #ICChooser(Frame, boolean, InformationScheme)
     * @see #ICChooser(Frame, String, boolean, InformationScheme)
     */
    public ICChooser(Frame owner,
            InformationScheme<? extends InformationComponent> scheme) {
        this(owner, "Choose Component", false, scheme);
    }

    /**
     * Constructs a new dialog with title "Choose Component".
     * @param owner the owner of the dialog
     * @param modal if the dialog should be modal
     * @param scheme the information scheme to search through
     * 
     * @since 1.0.0
     * 
     * @see #ICChooser(Frame, InformationScheme)
     * @see #ICChooser(Frame, String, boolean, InformationScheme)
     */
    public ICChooser(Frame owner, boolean modal,
            InformationScheme<? extends InformationComponent> scheme) {
        this(owner, "Choose Component", modal, scheme);
    }

    /**
     * Constructs a new dialog with the given title.
     * @param owner the owner of the dialog
     * @param title the title of the dialog
     * @param modal if the dialog should be modal
     * @param scheme the information scheme to search through
     * 
     * @since 1.0.0
     * 
     * @see #ICChooser(Frame, InformationScheme)
     * @see #ICChooser(Frame, boolean, InformationScheme)
     */
    public ICChooser(Frame owner, String title, boolean modal,
            InformationScheme<? extends InformationComponent> scheme) {
        super(owner, title, modal);
        that = this;
        this.scheme = scheme;
        this.listModel = new SelectiveICListModel(scheme);
        initComponents();
        initActions();
        initLayout();
        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * The action to cancel the selection process, and hide and reset the 
     * dialog. The selected information component will be set to 
     * <code>null</code>.
     * 
     * @since 1.0.0
     * 
     * @see #btnCancel
     */
    protected Action actCancel = new AbstractAction("Cancel") {
        @Override public void actionPerformed(ActionEvent e) {
            that.setSelectedIC(null);
            that.setVisible(false);
            that.reset();
        }
    };
    
    /**
     * The action to finish the selection process, and hide and reset the 
     * dialog. The selected information component will be set to whatever
     * component was selected in {@link list}. But if no component was selected,
     * the user will get an error message and the dialog stays.
     * 
     * @since 1.0.0
     * 
     * @see #btnChoose
     * @see #setSelectedIC(InformationComponent)
     */
    protected Action actChoose = new AbstractAction("Choose") {
        @Override public void actionPerformed(ActionEvent e) {
            if (list.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(that, "No component selected!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            that.setSelectedIC(list.getSelectedValue());
            that.setVisible(false);
            that.reset();
        }
    };
    
    /**
     * Returns the selected information component.
     * @return the selected information component
     * 
     * @since 1.0.0
     */
    public InformationComponent getSelectedIC() {
        return selectedIC;
    }

    /**
     * Sets the selected information component to <code>selectedIC</code>.
     * @param selectedIC the new selected information component
     * 
     * @since 1.0.0
     */
    public void setSelectedIC(InformationComponent selectedIC) {
        this.selectedIC = selectedIC;
    }
    
    /*
     * 
     * (non-JavaDoc)
     *
     * @see java.awt.Dialog#setVisible(boolean)
     */
    /**
     * {@inheritDoc}<br>
     * If set to <code>true</code>, will let {@link #txtSearch} request focus.  
     * @since 1.0.1
     */
    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b)
            txtSearch.requestFocus();
    }

    /**
     * Resets the dialog, so {@link #txtSearch} will be emptied and 
     * {@link #list} displays all components again.
     * 
     * @version 1.0.1
     * @since 1.0.0
     */
    public void reset() {
        txtSearch.setText(""); // doesn't clear the model
        listModel.setSearchString("");
        listModel.refreshSelection();
        list.getSelectionModel().clearSelection();
        pack();
    }
    
    /**
     * Initializes the components including the listeners and sets their
     * behavior.
     * 
     * @since 1.0.0
     */
    protected void initComponents() {
        labSearch = new JLabel("Search");
        txtSearch = new JTextField("");
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) {
                listModel.setSearchString(txtSearch.getText());
            }

            @Override public void removeUpdate(DocumentEvent e) {
                listModel.setSearchString(txtSearch.getText());
            }
            
            @Override public void changedUpdate(DocumentEvent e) {
                return;
            }
        });
        chkRegex = new JCheckBox("Regex", false);
        chkRegex.addChangeListener(new ChangeListener() {
            @Override public void stateChanged(ChangeEvent e) {
                if (chkRegex.isSelected())
                    chkCaseSensitive.setSelected(false);
                listModel.setRegex(chkRegex.isSelected());
            }
        });
        chkCaseSensitive = new JCheckBox("Case sensitive", false);
        chkCaseSensitive.addChangeListener(new ChangeListener() {
            @Override public void stateChanged(ChangeEvent e) {
                if (chkCaseSensitive.isSelected())
                    chkRegex.setSelected(false);
                listModel.setCaseSensitive(chkCaseSensitive.isSelected());
            }
        });
        pnlCheckBoxes = 
            GUIFactory.createButtonPanel(chkRegex, chkCaseSensitive);
        list = new JList<InformationComponent>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrList = new JScrollPane(list);
        btnCancel = new JButton(actCancel);
        btnChoose = new JButton(actChoose);
        pnlButtons = GUIFactory.createButtonPanel(btnCancel, btnChoose);
    }
    
    /**
     * Initializes the actions and their behavior.
     * 
     * @since 1.0.0
     */
    protected void initActions() {
        actCancel.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        actChoose.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);
    }
    
    /**
     * Initializes the layout.
     * 
     * @since 1.0.0
     */
    protected void initLayout() {
        gbl = new GridBagLayout();
        setLayout(gbl);
        gbl.addLayoutComponent(labSearch, 
            GUIFactory.createLeftJLabelConstraint(0, 0, 1));
        add(labSearch);
        gbl.addLayoutComponent(txtSearch, 
            GUIFactory.createRightJTextFieldConstraint(1, 0, 1));
        add(txtSearch);
        gbl.addLayoutComponent(pnlCheckBoxes, 
            GUIFactory.createCenteredButtonPanelConstrait(0, 1, 2));
        add(pnlCheckBoxes);
        gbl.addLayoutComponent(scrList, 
            GUIFactory.createCenteredJListConstraint(0, 2, 2));
        add(scrList);
        gbl.addLayoutComponent(pnlButtons, 
            GUIFactory.createCenteredButtonPanelConstrait(0, 3, 2));
        add(pnlButtons);
    }
    
    /**
     * A small GUI test method to check if it looks right and if basic 
     * functionality works.
     * @param args no relevance
     */
    public static void main(String[] args) {
        javax.swing.JFrame f = new javax.swing.JFrame();
        org.abos.schemes.ArrayInformationScheme<InformationComponent> scheme = 
            new org.abos.schemes.ArrayInformationScheme<InformationComponent>();
        scheme.add(new InformationComponent("Hallo"));
        scheme.add(new InformationComponent("Welt"));
        scheme.add(new InformationComponent("Wie geht es dir?"));
        ICChooser chooser = new ICChooser(f, scheme);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        chooser.setVisible(true);
    }

}
