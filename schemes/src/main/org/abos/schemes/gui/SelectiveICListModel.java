// LICENSE
package org.abos.schemes.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import org.abos.schemes.InformationComponent;
import org.abos.schemes.InformationScheme;

/**
 * A list model to get a selection of all components of a specified scheme.
 * Works with a usual JList.
 * 
 * @author Sebastian Koch
 * @version 1.0.0
 * @since 1.0.0
 * 
 * @see javax.swing.JList
 */
@SuppressWarnings("serial")
public class SelectiveICListModel 
extends AbstractListModel<InformationComponent> {

    /**
     * Characters that need to be escaped in regular expressions.
     * 
     * @since 1.0.0
     */
    protected final static char[] escapes = new char[]
        {'.','*','?','+','\\','(',')','[',']','{','}',',','-','^','&','$','|'};
    
    /**
     * The information scheme to select components from. This is usually not
     * changed while the model is in use.
     * 
     * @since 1.0.0
     */
    protected InformationScheme<? extends InformationComponent> scheme = null;
    
    /**
     * The selection of the scheme to be displayed.
     * 
     * @since 1.0.0
     * 
     * @see #refreshSelection()
     */
    protected List<? extends InformationComponent> selection = null;
    
    /**
     * If the search string should be handled as regular expression.
     * 
     * @since 1.0.0
     * 
     * @see #setRegex(boolean)
     */
    protected boolean regex = false;
    
    /**
     * If the search string should be handled as case sensitive.
     * 
     * @since 1.0.0
     * 
     * @see #setCaseSensitive(boolean)
     */
    protected boolean caseSensitive = false;
    
    /**
     * The search string to filter the {@link #scheme} to a {@link selection}.
     * 
     * @since 1.0.0
     */
    protected String searchString = null;
    
    /**
     * Constructs a new list model using the given scheme.
     * @param scheme the information scheme to select components from.
     * @throws NullPointerException If <code>scheme</code> refers to
     * <code>null</code>.
     * 
     * @since 1.0.0
     */
    public SelectiveICListModel(
            InformationScheme<? extends InformationComponent> scheme) {
        if (scheme == null) {
            throw new NullPointerException("scheme can't be null!");
        }
        this.scheme = scheme;
        this.selection = new ArrayList<InformationComponent>(scheme);
    }
    
    /**
     * Refreshes the {@link selection} and therefore what will be displayed
     * by any <code>JList</code> using this model.
     * 
     * @since 1.0.0
     */
    public void refreshSelection() {
        int oldSize = getSize();
        // refresh selection
        if (searchString == null || searchString.equals("")) {
            selection = new ArrayList<InformationComponent>(scheme);
        }
        else if (regex) {
            selection = scheme.getByRegex(searchString, true);
        }
        else {
            StringBuilder s = new StringBuilder();
            s.append(".*");
            if (caseSensitive)
                s.append(compileStringToRegex(searchString));
            else
                s.append(compileCaseInsensitiveStringToRegex(searchString));
            s.append(".*");
            selection = scheme.getByRegex(s.toString(), true);
        }
        if (selection == null) { // if invalid search string
            selection = new ArrayList<InformationComponent>(scheme);
        }
        // notify changes
        int newSize = this.getSize();
        if (oldSize < newSize) {
            fireContentsChanged(this, 0, oldSize-1);
            fireIntervalAdded(this, oldSize, newSize);
        }
        else { 
            fireContentsChanged(this, 0, newSize-1);
            if (oldSize > newSize) 
                fireIntervalRemoved(this, newSize, oldSize);
        }
    }
    
    /**
     * Compiles a string to a regular expression that matches any string
     * the given string is a substring of. Escape characters will be
     * properly escaped.
     * @param searchString the string to compile
     * @return a string serving as regular expression that matches any string
     * the given string is a substring of. 
     * @throws NullPointerException If <code>searchhString</code> refers to
     * <code>null</code>.
     * 
     * @since 1.0.0
     */
    public String compileStringToRegex(String searchString) {
        StringBuilder s = new StringBuilder();
        char c;
        for (int i = 0; i < searchString.length(); i++) {
            c = searchString.charAt(i);
            // may add escape symbol
            for (char d : escapes) {
                if (c == d) {
                    s.append('\\');
                    break;
                }
            }
            // then add character
            s.append(c);
        } //-> for (each character in string)
        return s.toString();
    }
    
    /**
     * Compiles a string to a regular expression that matches any string
     * the given string is a substring of, case insensitive. Escape characters 
     * will be properly escaped.
     * @param searchString the string to compile
     * @return a string serving as regular expression that matches any string
     * the given string is a substring of, not regarding if letters are capital
     * or not.  
     * @throws NullPointerException If <code>searchhString</code> refers to
     * <code>null</code>.
     * 
     * @since 1.0.0
     */
    public String compileCaseInsensitiveStringToRegex(String searchString) {
        StringBuilder s = new StringBuilder();
        char c;
        for (int i = 0; i < searchString.length(); i++) {
            c = searchString.charAt(i);
            if (Character.isLowerCase(c) || Character.isUpperCase(c)) {
                s.append('[');
                s.append(Character.toLowerCase(c));
                s.append(Character.toUpperCase(c));
                s.append(']');
            }
            else { // if not letter
                // may add escape symbol
                for (char d : escapes) {
                    if (c == d) {
                        s.append('\\');
                        break;
                    }
                }
                // then add character
                s.append(c);
            } //-> if not (letter)
        } //-> for (each character in string)
        return s.toString();
    }

    /* (non-JavaDoc)
     * @see javax.swing.ListModel#getSize()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public int getSize() {
        return selection.size();
    }

    /* (non-JavaDoc)
     * @see javax.swing.ListModel#getElementAt(int)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public InformationComponent getElementAt(int index) {
        return selection.get(index);
    }

    /**
     * Returns if the search string should be handled as regular expression.
     * @return if the search string should be handled as regular expression
     */
    public boolean isRegex() {
        return regex;
    }

    /**
     * Returns if the search string should be handled case sensitive.
     * @return if the search string should be handled case sensitive
     */
    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    /**
     * Returns the search string.
     * @return the search string
     * 
     * @since 1.0.0
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * Sets if the search string should be handled as regular expression to 
     * <code>regex</code>. This and <code>caseSensitive</code> can't be true
     * at the same time.
     * @param regex If <code>true</code>, search strings will be handled as 
     * regular expressions.
     * 
     * @since 1.0.0
     * 
     * @see #setCaseSensitive(boolean)
     */
    public void setRegex(boolean regex) {
        boolean change = this.regex != regex;
        this.regex = regex;
        if (regex)
            this.caseSensitive = false;
        if (change)
            refreshSelection();
    }

    /**
     * Sets if the search string should be handled case sensitive to 
     * <code>caseSensitive</code>. This and <code>regex</code> can't be true
     * at the same time.
     * @param regex If <code>true</code>, search strings will be handled 
     * case sensitive.
     * 
     * @since 1.0.0
     * 
     * @see #setRegex(boolean)
     * @see #compileStringToRegex(String)
     * @see #compileCaseInsensitiveStringToRegex(String)
     */
    public void setCaseSensitive(boolean caseSensitive) {
        boolean change = this.caseSensitive != caseSensitive;
        this.caseSensitive = caseSensitive;
        if (caseSensitive)
            this.regex = false;
        if (change)
            refreshSelection();
    }

    /**
     * Sets the search string to <code>searchString</code>.
     * @param searchString the new search string
     * 
     * @since 1.0.0
     */
    public void setSearchString(String searchString) {
        boolean change = (searchString == null ? 
            (this.searchString == null ? false : true) : 
            !searchString.equals(this.searchString));
        this.searchString = searchString;
        if (change)
            refreshSelection();
    }

}
