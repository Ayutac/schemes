package org.abos.schemes.gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.xml.stream.XMLStreamException;

import org.abos.schemes.ArrayInformationScheme;
import org.abos.schemes.InformationComponent;
import org.abos.schemes.SchemeComponent;

/**
 * A simple editor to create and change information component schemes. The
 * editor is implemented in such a way that one can subclass this class and
 * create a custom editor for subclasses of {@link InformationScheme}.
 * @author Sebastian Koch
 * 
 * @version 1.0.0
 * @since 1.0.0
 * 
 * @see org.abos.schemes.ArrayInformationScheme
 */
@SuppressWarnings("serial")
public class ICSchemeEditor extends JFrame {
    
    /**
     * The grid bag layout of this editor.
     * 
     * @since 1.0.0
     * 
     * @see #initLayout()
     */
    protected GridBagLayout gbl = null;
    
    /**
     * The underlying scheme of this editor.
     * 
     * @since 1.0.0
     */
    protected ArrayInformationScheme<InformationComponent> scheme = null;
    
    /**
     * The information component that can be changed right now. However,
     * changes will only be visible if {@link #saveChanges()}
     * was called before.
     * 
     * @since 1.0.0
     * 
     * @see #getActiveIC()
     * @see #setActiveIC(InformationComponent)
     */
    protected InformationComponent activeIC = null;
    
    /**
     * The label for the name text field.
     * 
     * @since 1.0.0
     * 
     * @see #txtName
     */
    protected JLabel labName = null;
    
    /**
     * The text field to change the name of the {@link #activeIC}.
     * 
     * @since 1.0.0
     */
    protected JTextField txtName = null;
    
    /**
     * The label for the description text area.
     * 
     * @since 1.0.0
     * 
     * @see #txtDescription
     */
    protected JLabel labDescription = null;
    
    /**
     * The text area to change the description of {@link #activeIC}.
     * 
     * @since 1.0.0
     * 
     * @see #scrDescription
     */
    protected JTextArea txtDescription = null;
    
    /* TODO
     * Add a scroll pane around the text area with constant size.
     */
    /**
     * The scroll pane for the description text area.
     * 
     * @since 1.0.0
     * 
     * @see #txtDescription
     */
    protected JScrollPane scrDescription = null;
    
    /**
     * The label for the parents list.
     * 
     * @since 1.0.0
     * 
     * @see #listParents
     */
    protected JLabel labParents = null;
    
    /**
     * A list of parents for {@link #activeIC}.
     * 
     * @since 1.0.0
     * 
     * @see #scrParents
     */
    protected ModifiableICList listParents = null;
    
    /**
     * The scroll pane for the parents list.
     * 
     * @since 1.0.0
     * 
     * @see #listParents
     */
    protected JScrollPane scrParents = null;
    
    /**
     * The label for the children list.
     * 
     * @since 1.0.0
     * 
     * @see #listChildren
     */
    protected JLabel labChildren = null;
    
    /**
     * A list of children for {@link #activeIC}.
     * 
     * @since 1.0.0
     * 
     * @see #scrChildren
     */
    protected ModifiableICList listChildren = null;
    
    /**
     * The scroll pane for the children list.
     * 
     * @since 1.0.0
     * 
     * @see #listChildren
     */
    protected JScrollPane scrChildren = null;
    
    /**
     * The button to discard changes to {@link #activeIC}.
     * 
     * @since 1.0.0
     * 
     * @see #actDiscardChanges
     * @see #discardChanges()
     */
    protected JButton btnDiscardChanges = null;
    
    /**
     * The button to save changes to {@link #activeIC}.
     * 
     * @since 1.0.0
     * 
     * @see #actSaveChanges
     * @see #saveChanges()
     */
    protected JButton btnSaveChanges = null;
    
    /**
     * A panel to group the buttons to save/discard changes to 
     * {@link #activeIC}.
     * 
     * @since 1.0.0
     * 
     * @see #btnDiscardChanges
     * @see #btnSaveChanges
     */
    protected JPanel pnlChangeButtons = null;
    
    /**
     * The menu bar of this editor.
     * 
     * @since 1.0.0
     * 
     * @see #initMenu()
     */
    protected JMenuBar menu = null;
    
    /**
     * The menu for the complete scheme.
     * 
     * @since 1.0.0
     */
    protected JMenu menuScheme = null;
    
    /**
     * The menu for the {@link #activeIC}.
     * 
     * @since 1.0.0
     */
    protected JMenu menuComponent = null;
    
    /**
     * A sub menu for the parents of {@link #activeIC}.
     * 
     * @since 1.0.0
     * @see #listParents
     */
    protected JMenu subMenuParents = null;
    
    /**
     * A sub menu for the children of {@link #activeIC}.
     * 
     * @since 1.0.0
     * @see #listChildren
     */
    protected JMenu subMenuChildren = null;
    
    /**
     * The dialog to save or open schemes.
     * 
     * @since 1.0.0
     * 
     * @see #save()
     * @see #open()
     */
    protected JFileChooser saveOpenDialog = null;
    
    /**
     * The dialog to export schemes as DOT file.
     * 
     * @since 1.0.0
     * 
     * @see #export()
     */
    protected JFileChooser exportDialog = null;
    
    /**
     * The ICChooser used by this editor.
     * 
     * @since 1.0.0
     */
    protected ICChooser icChooser = null;
    
    /**
     * Constructs a new scheme editor with an empty scheme and no selected
     * or editable component.
     * 
     * @since 1.0.0
     * 
     * @see #initComponents()
     * @see #initActions()
     * @see #initMenu()
     * @see #initLayout()
     */
    public ICSchemeEditor() {
        super("ICSchemeEditor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        scheme = new ArrayInformationScheme<InformationComponent>();
        icChooser = new ICChooser(this, true, scheme);
        saveOpenDialog = new JFileChooser(); // TODO Filter
        exportDialog = new JFileChooser(); // TODO Filter
        initComponents();
        initActions();
        initMenu();
        initLayout();
        pack();
//        txtDescription.setText("");
        setLocationRelativeTo(null);
        setActiveIC(null);
    }
    
    /**
     * The action to save the scheme.
     * 
     * @since 1.0.0
     * 
     * @see #save()
     */
    protected Action actSave = new AbstractAction("Save") {
        @Override public void actionPerformed(ActionEvent e) {
            save();
        }
    };
    
    /**
     * The action to open a scheme.
     * 
     * @since 1.0.0
     * 
     * @see #open()
     */
    protected Action actOpen = new AbstractAction("Open") {
        @Override public void actionPerformed(ActionEvent e) {
            open();
        }
    };
    
    /**
     * The action to export the scheme as DOT file.
     * 
     * @since 1.0.0
     * 
     * @see #export()
     */
    protected Action actExport = new AbstractAction("Export") {
        @Override public void actionPerformed(ActionEvent e) {
            export();
        }
    };
    
    /**
     * The action to create a new information component.
     * 
     * @since 1.0.0
     * 
     * @see #newIC()
     */
    protected Action actNewIC = new AbstractAction("New component") {
        @Override public void actionPerformed(ActionEvent e) {
            newIC();
        }
    };
    
    /**
     * The action to switch to a different information component.
     * 
     * @since 1.0.0
     * 
     * @see #switchIC()
     */
    protected Action actSwitchIC = new AbstractAction("Switch component") {
        @Override public void actionPerformed(ActionEvent e) {
            switchIC();
        }
    };
    
    /**
     * The action to discard the changes to {@link #activeIC}.
     * 
     * @since 1.0.0
     * 
     * @see #btnDiscardChanges
     * @see #discardChanges()
     */
    protected Action actDiscardChanges = new AbstractAction("Discard Changes") {
        @Override public void actionPerformed(ActionEvent e) {
            discardChanges();
        }
    };

    /**
     * The action to save the changes to {@link #activeIC}.
     * 
     * @since 1.0.0
     * 
     * @see #btnSaveChanges
     * @see #saveChanges()
     */
    protected Action actSaveChanges = new AbstractAction("Save Changes") {
        @Override public void actionPerformed(ActionEvent e) {
            saveChanges();
        }
    };
    
    /**
     * Saves the scheme.
     * 
     * @since 1.0.0
     * 
     * @see #actSave
     */
    public void save() {
        if (scheme == null)
            return; // TODO Exception
        if (JFileChooser.APPROVE_OPTION == saveOpenDialog.showSaveDialog(this))
            try {
                scheme.save(saveOpenDialog.getSelectedFile());
            } catch (IOException | XMLStreamException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
    }
    
    /**
     * Opens a scheme.
     * 
     * FIXME In the current implementation, the scheme to be opened is
     * just added to the scheme of the editor. This behavior should be
     * changed as soon as fail saves are implemented.
     * 
     * @since 1.0.0
     * 
     * @see #actOpen
     */
    public void open() {
        if (scheme == null)
            return; // TODO Exception
        if (JFileChooser.APPROVE_OPTION == saveOpenDialog.showSaveDialog(this))
            try {
                scheme.load(saveOpenDialog.getSelectedFile());
                icChooser.reset();
            } catch (IOException | XMLStreamException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
    }
    
    /**
     * Exports the scheme as DOT file.
     * 
     * @since 1.0.0
     * 
     * @see #actExport
     */
    public void export() {
        if (scheme == null)
            return; // TODO Exception
        if (JFileChooser.APPROVE_OPTION == exportDialog.showSaveDialog(this))
            /* TODO
             * have to add an export method to the scheme
             */
            try {
                scheme.export(exportDialog.getSelectedFile());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
    }
    
    /**
     * Constructs a new information component to be edited here.
     * 
     * @since 1.0.0
     * 
     * @see #actNewIC
     */
    public void newIC() {
        setActiveIC(new InformationComponent());
    }
    
    /**
     * Switches {@link #activeIC} to a different, already existing component
     * of the current scheme
     * 
     * @since 1.0.0
     * 
     * @see #actSwitchIC
     */
    public void switchIC() {
        icChooser.setSelectedIC(null);
        icChooser.setVisible(true);
        InformationComponent chosen = icChooser.getSelectedIC();
        if (chosen == null)
            return;
        setActiveIC(chosen);
    }
    
    // TODO deleteIC()
    
    /**
     * Deletes the changes to {@link #activeIC} by resetting it.
     * 
     * @since 1.0.0
     * 
     * @see #btnDiscardChanges
     */
    public void discardChanges() {
        setActiveIC(getActiveIC());
    }
    
    /**
     * Saves the changes to {@link #activeIC}.
     * 
     * @since 1.0.0
     * 
     * @see #btnSaveChanges
     */
    public void saveChanges() {
        // prepare removing old component
        activeIC.forceFamilyApart();
        int index = scheme.indexOf(activeIC);
        // add new component
        activeIC = new InformationComponent(txtName.getText(), 
            txtDescription.getText());
        Iterator<InformationComponent> it = listParents.getEntries().iterator();
        while (it.hasNext()) {
            activeIC.addParent(it.next());
        }
        it = listChildren.getEntries().iterator();
        while (it.hasNext()) {
            activeIC.addChild(it.next());
        }
        activeIC.forceFamilyTogether();
        if (index > -1)
            scheme.set(index, activeIC);
        else
            scheme.add(activeIC);
        icChooser.reset();
    }
    
    /**
     * Resets the form of the editor, setting {@link #activeIC} to
     * <code>null</code> and disabling the form fields.
     * 
     * @since 1.0.0
     * 
     * @see #setActiveIC(InformationComponent)
     * @see #setComponentEditorEnabled(boolean)
     */
    public void reset() {
        setActiveIC(null);
    }
    
    /**
     * Enables or disables the form fields of this editor.
     * @param enabled If <code>true</code>, the focus will be requested for
     * the name text field.
     * 
     * @since 1.0.0
     */
    public void setComponentEditorEnabled(boolean enabled) {
        txtName.setEnabled(enabled);
        txtDescription.setEnabled(enabled);
        scrParents.setEnabled(enabled);
        scrChildren.setEnabled(enabled);
        actDiscardChanges.setEnabled(enabled);
        actSaveChanges.setEnabled(enabled);
        subMenuParents.setEnabled(false);//enabled);
        subMenuChildren.setEnabled(false);//enabled);
        if (enabled)
            txtName.requestFocus();
    }
    
    /**
     * Returns the scheme.
     * @return the scheme
     */
    public ArrayInformationScheme<? extends InformationComponent> getScheme() {
        return scheme;
    }

    /**
     * Returns the activeIC.
     * @return the activeIC
     */
    public InformationComponent getActiveIC() {
        return activeIC;
    }

    /**
     * Sets the scheme to <code>scheme</code>.
     * @param scheme the new scheme
     */
    public void setScheme(ArrayInformationScheme<InformationComponent> scheme) {
        this.scheme = scheme;
    }

    /**
     * Sets the activeIC to <code>activeIC</code>.
     * @param activeIC the new activeIC. If <code>null</code>, the editor
     * will be reseted.
     * 
     * @see #reset()
     */
    public void setActiveIC(InformationComponent activeIC) {
        if (activeIC == null) {
            this.activeIC = null;
            txtName.setText("");
            txtDescription.setText("");
            listParents.setContextIC(null);
            listChildren.setContextIC(null);
            setComponentEditorEnabled(false);
        }
        else {
            try {
                this.activeIC = (InformationComponent)activeIC.clone();
            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
            txtName.setText(this.activeIC.getName());
            txtDescription.setText(this.activeIC.getDescription());
            listParents.setContextIC(activeIC);
            listChildren.setContextIC(activeIC);
            setComponentEditorEnabled(true);
        }
    }

    /**
     * Initializes the components.
     * 
     * @since 1.0.0
     */
    protected void initComponents() {
        labName = new JLabel("Name:");
        txtName = new JTextField("");
        labDescription = new JLabel("Description:");
        txtDescription = new JTextArea("\n\n\n\n");
        txtDescription.setLineWrap(true);
//        txtDescription.setColumns(4);
//        txtDescription.setMinimumSize(new Dimension(
//            4*txtDescription.getMinimumSize().height,
//            txtDescription.getMinimumSize().width));
        scrDescription = new JScrollPane(txtDescription);
        labParents = new JLabel("Parents:");
        listParents = new ModifiableICList(SchemeComponent.PARENT, icChooser);
        scrParents = new JScrollPane(listParents);
        scrParents.setComponentPopupMenu(listParents.getPopupMenu());
        listParents.setInheritsPopupMenu(true);
        labName.setComponentPopupMenu(listParents.getPopupMenu());
        labChildren = new JLabel("Children:");
        listChildren = new ModifiableICList(SchemeComponent.CHILD, icChooser);
        scrChildren = new JScrollPane(listChildren);
        scrChildren.setComponentPopupMenu(listChildren.getPopupMenu());
        listChildren.setInheritsPopupMenu(true);
        btnDiscardChanges = new JButton(actDiscardChanges);
        btnSaveChanges = new JButton(actSaveChanges);
        pnlChangeButtons = GUIFactory.createButtonPanel(
            btnDiscardChanges, btnSaveChanges);
    }
    
    /**
     * Initializes the actions.
     * 
     * @since 1.0.0
     */
    protected void initActions() {
        actSave.putValue(Action.ACCELERATOR_KEY, 
            KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        actSave.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        actOpen.putValue(Action.ACCELERATOR_KEY, 
            KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        actOpen.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
        actExport.putValue(Action.ACCELERATOR_KEY, 
            KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        actExport.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
        actNewIC.putValue(Action.ACCELERATOR_KEY, 
            KeyStroke.getKeyStroke(KeyEvent.VK_N, 
                ActionEvent.CTRL_MASK+ActionEvent.ALT_MASK));
        actSwitchIC.putValue(Action.ACCELERATOR_KEY, 
            KeyStroke.getKeyStroke(KeyEvent.VK_W, 
                ActionEvent.CTRL_MASK+ActionEvent.ALT_MASK));
        actDiscardChanges.putValue(Action.ACCELERATOR_KEY, 
            KeyStroke.getKeyStroke(KeyEvent.VK_D, 
                ActionEvent.CTRL_MASK+ActionEvent.ALT_MASK));
        actDiscardChanges.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
        actSaveChanges.putValue(Action.ACCELERATOR_KEY, 
            KeyStroke.getKeyStroke(KeyEvent.VK_S, 
                ActionEvent.CTRL_MASK+ActionEvent.ALT_MASK));
        actSaveChanges.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
    }
    
    /**
     * Initializes the menu.
     * 
     * @since 1.0.0
     */
    protected void initMenu() {
        menu = new JMenuBar();
        menuScheme = new JMenu("Scheme");
        menuScheme.add(actOpen);
        menuScheme.add(actSave);
        menuScheme.add(actExport);
        menu.add(menuScheme);
        menuComponent = new JMenu("Component");
        menuComponent.add(actNewIC);
        menuComponent.add(actSwitchIC);
        menuComponent.add(actSaveChanges);
        menuComponent.add(actDiscardChanges);
        subMenuParents = new JMenu("Parents...");
        subMenuParents.add(listParents.getPopupMenu()); // FIXME
        menuComponent.add(subMenuParents);
        subMenuChildren = new JMenu("Children...");
        subMenuChildren.add(listChildren.getPopupMenu()); // FIXME
        menuComponent.add(subMenuChildren);
        menu.add(menuComponent);
        setJMenuBar(menu);
    }
    
    /**
     * Initializes the layout.
     * 
     * @since 1.0.0
     */
    protected void initLayout() {
        gbl = new GridBagLayout();
        setLayout(gbl);
        gbl.addLayoutComponent(labName, 
            GUIFactory.createLeftJLabelConstraint(0, 0, 1));
        add(labName);
        gbl.addLayoutComponent(txtName, 
            GUIFactory.createRightJTextFieldConstraint(1, 0, 1));
        add(txtName);
        gbl.addLayoutComponent(labDescription, 
            GUIFactory.createLeftJLabelConstraint(0, 1, 1));
        add(labDescription);
        gbl.addLayoutComponent(txtDescription, 
            GUIFactory.createRightJTextFieldConstraint(1, 1, 1));
        add(txtDescription);
        gbl.addLayoutComponent(labParents, 
            GUIFactory.createCenteredJLabelConstraint(0, 2, 2));
        add(labParents);
        gbl.addLayoutComponent(scrParents, 
            GUIFactory.createCenteredJListConstraint(0, 3, 2));
        add(scrParents);
        gbl.addLayoutComponent(labChildren, 
            GUIFactory.createCenteredJLabelConstraint(0, 4, 2));
        add(labChildren);
        gbl.addLayoutComponent(scrChildren, 
            GUIFactory.createCenteredJListConstraint(0, 5, 2));
        add(scrChildren);
        gbl.addLayoutComponent(pnlChangeButtons, 
            GUIFactory.createCenteredButtonPanelConstrait(0, 6, 2));
        add(pnlChangeButtons);
    }
    
    /**
     * A small GUI test method to check if it looks right and if basic 
     * functionality works.
     * @param args no relevance
     */
    public static void main(String[] args) {
        ICSchemeEditor ed = new ICSchemeEditor();
        ed.setVisible(true);
    }

}
