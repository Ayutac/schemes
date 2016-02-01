// LICENSE 
package org.abos.schemes;

import java.util.Collection;
import java.util.List;

/**
 * A scheme is a collection of scheme components which can have several parents
 * and children. These parents and children are not necessarily included in
 * this scheme, such components are called associated to this scheme.<br>
 * A scheme is called valid if all its components have valid families.<br>
 * A scheme is called type-consistent of type <code>E</code> if all its 
 * components and associated components are of type <code>E</code>.<br>
 * A scheme is called self-contained if it has no associated components.
 * 
 * @author Sebastian Koch
 * @version 1.0.0
 * @since 1.0.0
 * 
 * @see SchemeComponent
 */
public interface Scheme<E extends SchemeComponent> extends Collection<E> {

    /**
	 * Returns all scheme components of this scheme which have no parents.
	 * When another root is added to the scheme, this method should return
	 * the same list extended by the new root, so the list is
	 * ordered chronologically by the time roots were added, unless
	 * families of components are changed and the roots are validated anew.
	 * @return a list of the roots
	 * 
	 * @since 1.0.0
     * 
     * @see #validateRootsAndLeaves()
	 */
	public List<E> getRoots();
	
	/**
	 * Returns the root of this scheme with index <code>index</code>.
	 * @param index the index of the root
	 * @return the specified root
	 * 
	 * @since 1.0.0
	 */
	public E getRoot(int index);
	
	/**
	 * Returns all scheme components of this scheme which have no children.
	 * When another leaf is added to the scheme, this method should return
	 * the same list extended by the new root, so the list is
	 * ordered chronologically by the time leaves were added, unless
     * families of components are changed and the leaves are validated anew.
	 * @return a list of the leaves
	 * 
	 * @since 1.0.0
	 * 
	 * @see #validateRootsAndLeaves()
	 */
	public List<E> getLeaves();
	
	/**
	 * Returns the leaf of this scheme with index <code>index</code>.
	 * @param index the index of the leaf
	 * @return the specified leaf
	 * 
	 * @since 1.0.0
	 */
	public E getLeaf(int index);
	
	/**
	 * Returns <code>true</code> if this scheme contains an ancestor of 
	 * <code>e</code>.
	 * @param e the element to look up
	 * @return <code>true</code> if this scheme contains an ancestor of 
	 * <code>e</code>.
	 * 
	 * @since 1.0.0
	 */
	public boolean containsAncestorOf(E e);
	
	/**
	 * Returns <code>true</code> if this scheme contains a descendant of 
     * <code>e</code>.
	 * @param e the element to look up
	 * @return <code>true</code> if this scheme contains a descendant of 
	 * <code>e</code>.
	 * 
	 * @since 1.0.0
	 */
	public boolean containsDescendantOf(E e);

	/**
	 * Returns <code>true</code> if <code>e</code> is in this scheme or – if 
	 * <code>includeAncestors</code> is <code>true</code> – if one of 
	 * <code>e</code>'s ancestors is in the scheme.
	 * @param e the element to look up
	 * @param includeAncestors If the ancestors should be included in the
	 * lookup.
	 * @return <code>contains(e) || 
	 * (includeAncestors ? containsAncestorOf(e) : false)</code>
	 * 
	 * @since 1.0.0
	 */
	public boolean contains(E e, boolean includeAncestors);
	
	/**
	 * Returns <code>true</code> if <code>e</code> is in this scheme or – if 
	 * <code>includeAncestors</code> is <code>true</code> – if one of 
	 * <code>e</code>'s ancestors is in the scheme or – if
	 * <code>includeDescendants</code> is <code>true</code> – if one of 
	 * <code>e</code>'s descendants is in the scheme. 
	 * @param e the element to look up
	 * @param includeAncestors If the ancestors should be included in the
	 * lookup.
	 * @param includeDescendants If the descendants should be included in the
	 * lookup.
	 * @return {@code contains(e) || 
	 * (includeAncestors ? containsAncestorOf(e) : false) ||
	 * (includeDescendants ? containsDescendantOf(e) : false)}
	 * 
	 * @since 1.0.0
	 */
	public boolean contains(E e, 
			boolean includeAncestors, boolean includeDescendants);
    
    /**
     * Compares if two schemes have the same components (calling
     * {@link SchemeComponent#equals(Object)}) and the right relations between
     * them. 
     * @param scheme the other scheme to compare with this one
     * @return <code>true</code> if the two schemes contain the same components 
     * with
     * the same relations.
     * 
     * @since 1.0.0
     */
    public boolean deepEquals(Scheme<E> scheme);
    
    /**
     * Creates a deep copy of this scheme. Components will be cloned and their
     * references corrected.
     * @return A deep copy of this scheme.
     * @throws CloneNotSupportedException If the cloning of an underlying
     * element is not supported.
     * 
     * @since 1.0.0
     */
    public Scheme<E> deepCopy() throws CloneNotSupportedException;
	
	/**
	 * Returns <code>true</code> if all components of this scheme have a valid 
	 * family.
	 * @return If the families of all components are valid.
	 * 
	 * @since 1.0.0
	 * 
	 * @see SchemeComponent#hasValidFamily()
	 */
	public boolean allFamiliesValid();
	
	/**
	 * When a scheme component is added and its parents children are changed
	 * afterwards, {@link #getRoots()}, {@link #getRoot()}, {@link #getLeaves()} 
	 * and {@link #getLeaf()} would return false results. Calling this method
	 * will ensure that these functions will return right results again.
	 * 
	 * @since 1.0.0
	 */
	public void validateRootsAndLeaves();
	
	// TODO add method to check integrity of types
	
	// TODO check if there are any associated components.
	
}
