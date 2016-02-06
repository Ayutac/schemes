package org.abos.schemes.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import org.abos.schemes.InformationComponent;
import org.abos.schemes.SchemeComponent;

/**
 * A modifiable list for information components working with a default list
 * model.
 * 
 * @author Sebastian Koch
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class ModifiableICList extends JList<InformationComponent> {

    /**
     * The type of collection of the given information component to be 
     * displayed.
     * 
     * @since 1.0.0
     * 
     * @see #getCollectionType()
     * @see #setCollectionType(int)
     * @see org.abos.schemes.AbstractSchemeComponent#iterator(int)
     */
    protected int collectionType;
    
    /**
     * The information component of which a collection will be displayed,
     * for example all parents.
     * 
     * @since 1.0.0
     * 
     * @see #getContextIC()
     * @see #setContextIC(InformationComponent)
     */
    protected InformationComponent contextIC = null;
    
    /**
     * The information component chooser to be used by this ModifiableICList.
     * 
     * @since 1.0.0
     * 
     * @see #addIC()
     */
    protected ICChooser icChooser = null;
    
    /**
     * The popup menu to make changes to this list.
     * 
     * @since 1.0.0
     * 
     * @see #actAddIC
     * @see #actRemoveIC
     * @see #initPopupMenu()
     */
    protected JPopupMenu popup = null;
    
    /**
     * Constructs a new modifiable information component list with a given
     * type and an ICChooser.
     * @param collectionType indicates which collection of the component will 
     * be put in this list, for example 
     * {@link org.abos.schemes.SchemeComponent.PARENT} or
     * {@link org.abos.schemes.SchemeComponent.CHILD}
     * @param icChooser The ICChooser to be used by this list.
     * 
     * @since 1.0.0
     * 
     * @see #setCollectionType(int)
     * @see #initActions()
     * @see #initPopupMenu()
     * @see org.abos.schemes.InformationComponent
     * @see org.abos.schemes.AbstractSchemeComponent#iterator(int)
     */
    public ModifiableICList(int collectionType, ICChooser icChooser) {
        super(new DefaultListModel<InformationComponent>());
        setCollectionType(collectionType);
        this.icChooser = icChooser;
        initActions();
        initPopupMenu();
    }
    
    /**
     * The action to add an information component to this list.
     * 
     * @since 1.0.0
     * 
     * @see #addIC()
     */
    public Action actAddIC = new AbstractAction("Add component") {
        @Override public void actionPerformed(ActionEvent e) {
            addIC();
        }
    };
    
    /**
     * The action to remove an information component to this list.
     * 
     * @since 1.0.0
     * 
     * @see #removeIC()
     */
    public Action actRemoveIC = new AbstractAction("Remove component") {
        @Override public void actionPerformed(ActionEvent e) {
            removeIC();
        }
    };
    
    /**
     * Adds an information component to this list using the ICChooser of this
     * list.
     * 
     * @since 1.0.0
     * 
     * @see #actAddIC
     * @see #icChooser
     */
    public void addIC() {
        icChooser.setVisible(true);
        InformationComponent chosen = icChooser.getSelectedIC();
        if (chosen == null)
            return;
        if (isSelectionEmpty())
            getCastedModel().addElement(chosen);
        else
            getCastedModel().add(getSelectedIndex(), chosen);
    }
    
    /**
     * Removes an information component from this list.
     * 
     * @since 1.0.0
     * 
     * @see #actRemoveIC
     */
    public void removeIC() {
        if (isSelectionEmpty())
            return;
        getCastedModel().remove(getSelectedIndex());
    }
    
    /**
     * Refreshes the model based on {@link #collectionType} and 
     * {@link #contextIC}. 
     * @throws ClassCastException If any component given by the 
     * iterator (specified through <code>collectionType</code>) of the 
     * <code>contextIC</code> isn't an instance of {@link InformationComponent}.
     * @throws IndexOutOfBoundsException If <code>contextIC</code> is specified
     * and the given type is invalid.
     * 
     * @since 1.0.0
     */
    public void refreshModel() {
        if (contextIC == null) {
            getCastedModel().clear();
            return;
        }
        Iterator<? extends SchemeComponent> it = 
            contextIC.iterator(collectionType);
        DefaultListModel<InformationComponent> model =
            new DefaultListModel<InformationComponent>();
        while (it.hasNext()) {
            model.addElement((InformationComponent)it.next());
        }
        setModel(model);
    }
    
    /**
     * Returns the collectionType.
     * @return the collectionType
     * 
     * @since 1.0.0
     * 
     * @see #collectionType
     * @see #setCollectionType(int)
     */
    public int getCollectionType() {
        return collectionType;
    }
    
    /**
     * Returns the contextIC.
     * @return the contextIC
     * 
     * @since 1.0.0
     * 
     * @see #contextIC
     * @see #setContextIC(InformationComponent)
     */
    public InformationComponent getContextIC() {
        return contextIC;
    }
    
    /**
     * Sets the contextIC to <code>contextIC</code> and refreshes the model.
     * @param contextIC the new contextIC
     * @throws ClassCastException If any component given by the 
     * iterator (specified through {@link #collectionType}) of the 
     * <code>contextIC</code> isn't an instance of {@link InformationComponent}.
     * @throws IndexOutOfBoundsException If <code>contextIC</code> is specified
     * and the given type is invalid.
     * 
     * @since 1.0.0
     * 
     * @see #contextIC
     * @see #getContextIC()
     * @see #refreshModel()
     */
    public void setContextIC(InformationComponent contextIC) {
        this.contextIC = contextIC;
        refreshModel(); 
    }

    /**
     * Returns the popup menu.
     * @return the popup menu
     * 
     * @since 1.0.0
     * 
     * @see #popup
     */
    public JPopupMenu getPopupMenu() {
        return popup;
    }
    
    /**
     * Returns the model of this list casted as <code>DefaultListModel</code>
     * as needed.
     * @return the casted model of this list
     * @throws ClassCastException If the model of this list was changed after
     * the list was created and isn't of type <code>DefaultListModel</code>
     * anymore.
     * 
     * @since 1.0.0
     * 
     * @see javax.swing.DefaultListModel
     */
    public DefaultListModel<InformationComponent> getCastedModel() {
        return (DefaultListModel<InformationComponent>)getModel();
    }
    
    /**
     * Returns the entries of this list as a 
     * <code>List&lt;InformationComponent&gt;</code>.
     * @return the entries of this list
     * @throws ClassCastException If the model of this list was changed after
     * the list was created and isn't of type <code>DefaultListModel</code>
     * anymore.
     * 
     * @since 1.0.0
     * 
     * @see #getCastedModel()
     */
    public List<InformationComponent> getEntries() {
        LinkedList<InformationComponent> entries = 
            new LinkedList<InformationComponent>();
        DefaultListModel<InformationComponent> model = getCastedModel();
        for (int i = 0; i < model.size(); i++) {
            entries.add(model.get(i));
        }
        return entries;
    }
    
    /**
     * Sets the collectionType to <code>collectionType</code> and refreshes 
     * the model.
     * @param collectionType the new collectionType
     * @throws ClassCastException If any component given by the 
     * iterator (specified through <code>collectionType</code>) of the 
     * {@link #contextIC} isn't an instance of {@link InformationComponent}.
     * @throws IndexOutOfBoundsException If <code>contextIC</code> is specified
     * and the given type is invalid.
     * 
     * @since 1.0.0
     * 
     * @see #collectionType
     * @see #getCollectionType()
     * @see #refreshModel()
     */
    protected void setCollectionType(int collectionType) {
        this.collectionType = collectionType;
        refreshModel(); 
    }
    
    /**
     * Initializes the actions.
     * 
     * @since 1.0.0
     */
    protected void initActions() {
        actAddIC.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        if (collectionType == SchemeComponent.PARENT)
            actAddIC.putValue(Action.ACCELERATOR_KEY, 
                KeyStroke.getKeyStroke(KeyEvent.VK_P, 
                    ActionEvent.CTRL_MASK+ActionEvent.ALT_MASK));
        else if (collectionType == SchemeComponent.CHILD)
            actAddIC.putValue(Action.ACCELERATOR_KEY, 
                KeyStroke.getKeyStroke(KeyEvent.VK_C, 
                    ActionEvent.CTRL_MASK+ActionEvent.ALT_MASK));
        actRemoveIC.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
    }
    
    /**
     * Initializes the popup menu.
     * 
     * @since 1.0.0
     */
    protected void initPopupMenu() {
        popup = new JPopupMenu();
        popup.add(actAddIC);
        popup.add(actRemoveIC);
    }

}
