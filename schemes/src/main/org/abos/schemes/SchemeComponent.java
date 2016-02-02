// LICENSE
package org.abos.schemes;

import java.util.Iterator;

/**
 * This is a component of a scheme. Components can have multiple parents
 * or children.
 * 
 * @author Sebastian Koch
 * @version 1.1.0
 * @since 1.0.0
 */
public interface SchemeComponent extends Cloneable {

    /**
     * A constant to be associated with the parents of this component.
     * 
     * @since 1.0.0
     * 
     * @see #parents
     * @see #isDescendantOf(SchemeComponent)
     * @see #iterator(int)
     */
    public static final int PARENT = 1;

    /**
     * A constant to be associated with the children of this component.
     * 
     * @since 1.0.0
     * 
     * @see #children
     * @see #isAncestorOf(SchemeComponent)
     * @see #iterator(int)
     */
    public static final int CHILD = 2;
    
    /**
     * Tells if this scheme component is a parent of another scheme component.
     * @param c the scheme component to check
     * @return <code>true</code> if this component is a parent of 
     * <code>c</code>
     * 
     * @since 1.0.0
     */
    public boolean isParentOf(SchemeComponent c);
    
    /**
     * Tells if this scheme component is a child of another scheme component.
     * @param c the scheme component to check
     * @return <code>true</code> if this component is a child of <code>c</code>
     * 
     * @since 1.0.0
     */
    public boolean isChildOf(SchemeComponent c);
    
    /**
     * Tells if this scheme component is an ancestor of another scheme 
     * component. This will be found out by looking at the children of this 
     * component.
     * @param c the scheme component to check
     * @return <code>true</code> if this component is an ancestor of 
     * <code>c</code>
     * 
     * @since 1.0.0
     * 
     * @see #isChildOf(SchemeComponent)
     * @see #childrenIterator()
     */
    public boolean isAncestorOf(SchemeComponent c);
    
    /**
     * Tells if this scheme component is a descendant of another scheme 
     * component. This will be found out by looking at the parents of this 
     * component.
     * @param c the scheme component to check
     * @return <code>true</code> if this component is a descendant of 
     * <code>c</code>
     * 
     * @since 1.0.0
     * 
     * @see #isParentOf(SchemeComponent)
     * @see #parentsIterator()
     */
    public boolean isDescendantOf(SchemeComponent c);
    
    /**
     * Returns <code>true</code> if this scheme component has no parents.
     * @return <code>true</code> if this scheme component has no parents.
     * 
     * @since 1.0.0
     */
    public boolean isRoot();
    
    /**
     * Returns <code>true</code> if this scheme component has no children.
     * @return <code>true</code> if this scheme component has no children.
     * 
     * @since 1.0.0
     */
    public boolean isLeaf();
    
    /**
     * Returns <code>true</code> if this scheme component has a valid family.
     * A scheme component <code>c</code> is taken to be valid, if
     * <code>c.isAncestorOf(c) || c.isDescendantOf(c) == false</code> and every
     * parent of <code>c</code> recognizes it as its child and every child of
     * <code>c</code> recognizes it as its parent.
     * 
     * @since 1.0.0
     */
    public boolean hasValidFamily();
    
    /**
     * Adds a given scheme component as parent of this component. This method
     * does NOT change <code>c</code>.
     * @param c the new parent
     * @return <code>true</code> if the addition was successful.
     * 
     * @since 1.0.0
     */
    public boolean addParent(SchemeComponent c);
    
    /**
     * Adds a given scheme component as child of this component. This method
     * does NOT change <code>c</code>.
     * @param c the new child
     * @return <code>true</code> if the addition was successful.
     * 
     * @since 1.0.0
     */
    public boolean addChild(SchemeComponent c);
    
    /**
     * Removes a given scheme component from the parents of this component. 
     * This method does NOT change <code>c</code>.
     * @param c the parent to be removed
     * @return <code>true</code> if the removal was successful.
     * 
     * @since 1.0.0
     */
    public boolean removeParent(SchemeComponent c);
    
    /**
     * Removes a given scheme component from the children of this component. 
     * This method does NOT change <code>c</code>.
     * @param c the child to be removed
     * @return <code>true</code> if the removal was successful.
     * 
     * @since 1.0.0
     */
    public boolean removeChild(SchemeComponent c);
    
    /**
     * The add/remove Parent/Child methods do not change the new/old component,
     * in other words they don't add/remove a Child/Parent accordingly. This 
     * method will change all parents and children into accepting this 
     * component.
     * 
     * @since 1.0.0
     * 
     * @see #forceFamilyApart()
     */
    public void forceFamilyTogether();

    /**
     * The add/remove Parent/Child methods do not change the new/old component,
     * in other words they don't add/remove a Child/Parent accordingly. This 
     * method will change all parents and children into denying this 
     * component.
     * 
     * @since 1.1.0
     * 
     * @see #forceFamilyTogether()
     */
    public void forceFamilyApart();

    /**
     * Returns an iterator depending on the argument.
     * @param type The type of the iterator.
     * @return The iterator of the given type.
     * @throws IndexOutOfBoundsException If <code>type</code> refers to an undefined
     * index.
     * 
     * @since 1.0.0
     * 
     * @see #PARENT
     * @see #CHILD
     * @see #parentsIterator()
     * @see #childrenIterator()
     */
    public Iterator<? extends SchemeComponent> iterator(int type);
    
    /**
     * Returns an iterator for the parents.
     * @return an iterator for the parents
     * 
     * @since 1.0.0
     */
    public Iterator<SchemeComponent> parentsIterator();

    /**
     * Returns an iterator for the children.
     * @return an iterator for the children
     * 
     * @since 1.0.0
     */
    public Iterator<SchemeComponent> childrenIterator();
    
    /**
     * Returns a plain copy of the scheme component without relations.
     * @return a plain copy of the scheme component
     * 
     * @since 1.0.0
     */
    public Object clone() throws CloneNotSupportedException;
    
    /**
     * Returns <code>true</code> if both this and the other component are equal 
     * and have equivalent relationships.
     * @param other the other component to compare
     * @return <code>true</code> if both components are deeply equal.
     * 
     * @since 1.0.0
     */
    public boolean deepEquals(SchemeComponent other);

}
