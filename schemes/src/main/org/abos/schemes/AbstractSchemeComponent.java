// LICENSE
package org.abos.schemes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * An abstract implementation of the {@link SchemeComponent} interface,
 * providing meaningful methods and an empty constructor. However, no data
 * except the family is carried, so this class does not overwrite 
 * {@link #equals(Object)} or {@link #hashCode()}, furthermore {@link #clone()}
 * will still throw an <code>CloneNotSupportedException</code>. <br>
 * This class is not thread-safe.
 * 
 * @author Sebastian Koch
 * @version 1.0.0
 * @since 1.0.0
 * 
 * @see SchemeComponent
 */
public abstract class AbstractSchemeComponent implements SchemeComponent {
	
	/**
	 * The parents of this component.
	 * 
	 * @since 1.0.0
	 */
	protected List<SchemeComponent> parents;
	
	/**
	 * The children of this component.
	 * 
	 * @since 1.0.0
	 */
	protected List<SchemeComponent> children;
	
	/**
	 * Creates an empty component. Initializes {@link #parents} and
	 * {@link #children} with an empty <code>LinkedList</code> of type
	 * {@link SchemeComponent}.
	 * 
	 * @since 1.0.0
	 */
	public AbstractSchemeComponent() {
	    parents = new LinkedList<SchemeComponent>();
        children = new LinkedList<SchemeComponent>();
	}

    /* 
	 * (non-JavaDoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	// still throws an exception as documented in the class documentation
	@Override
	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}

	/* 
	 * (non-JavaDoc)
	 * 
	 * @see org.abos.schemes.SchemeComponent#isParentOf(org.abos.schemes.
	 * SchemeComponent)
	 */
	/**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
	public boolean isParentOf(SchemeComponent c) {
		return children.contains(c);
	}

	/* 
	 * (non-JavaDoc)
	 * 
	 * @see org.abos.schemes.SchemeComponent#isChildOf(org.abos.schemes.
	 * SchemeComponent)
	 */
	/**
	 * {@inheritDoc}
	 * @since 1.0.0
	 */
	@Override
	public boolean isChildOf(SchemeComponent c) {
		return parents.contains(c);
	}
	
	/**
	 * Checks if a given component is in a certain collection,
	 * or in a (recursive) super collection thereof. This method provides the
	 * functionality used by {@link #isAncestorOf(SchemeComponent)} and
	 * {@link #isDescendantOf(SchemeComponent)}, because they are very similar
	 * to each other, since the concept of parents and children in schemes is
	 * basically the same.
	 * 
	 * @param c the scheme component to compare. Can also be 
	 * <code>this</code>. 
	 * @param superCollection the collection of scheme components to start 
	 * checking from.
	 * @param type The type of collection to further search through if found
	 * within a given scheme component.
	 * @return <code>true</code> if the component <code>c</code> could be found
	 * in <code>superCollection</code> or any super collection of type 
	 * <code>type</code> thereof. 
	 * 
	 * @since 1.0.0
	 * 
	 * @see #iterator(int)
	 * @see #isAncestorOf(SchemeComponent)
	 * @see #isDescendantOf(SchemeComponent)
	 */
	// Javadoc throws in case of invalid type
	protected boolean isInSuperCollectionOf(SchemeComponent c, 
	        Collection<? extends SchemeComponent> superCollection, int type) {
        if (c == null || superCollection == null)
            return false;
        HashSet<SchemeComponent> checked = new HashSet<SchemeComponent>();
        HashSet<SchemeComponent> toCheck = new HashSet<SchemeComponent>();
        SchemeComponent checking = null; // component currently in check
        Iterator<? extends SchemeComponent> it = null; // to get more components
        SchemeComponent next = null;
        toCheck.addAll(superCollection); 
        // check complete super collection
        while (!toCheck.isEmpty()) {
            // get a component to check
            checking = toCheck.iterator().next(); 
            if (c.equals(checking))
                return true;
            // get more components of right type to check through
            it = checking.iterator(type); 
            while (it.hasNext()) {
                next = it.next();
                if (!checked.contains(next))
                    toCheck.add(next);
            }
            // check of current component is completed
            toCheck.remove(checking);
            checked.add(checking);
        }
        return false;
	}

	/* 
	 * (non-JavaDoc)
	 * 
	 * @see org.abos.schemes.SchemeComponent#isAncestorOf(org.abos.schemes.
	 * SchemeComponent)
	 */
	/**
	 * {@inheritDoc}
	 * @since 1.0.0
	 */
	@Override
	public boolean isAncestorOf(SchemeComponent c) {
	    return isInSuperCollectionOf(c, children, CHILD);
	}

	/* 
	 * (non-JavaDoc)
	 * 
	 * @see org.abos.schemes.SchemeComponent#isDescendantOf(org.abos.schemes.
	 * SchemeComponent)
	 */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
	@Override
	public boolean isDescendantOf(SchemeComponent c) {
        return isInSuperCollectionOf(c, parents, PARENT);
	}

	/* 
	 * (non-JavaDoc)
	 * 
	 * @see org.abos.schemes.SchemeComponent#isRoot()
	 */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
	@Override
	public boolean isRoot() {
		return parents.isEmpty();
	}

	/* 
	 * (non-JavaDoc)
	 * 
	 * @see org.abos.schemes.SchemeComponent#isLeaf()
	 */
	/**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
	public boolean isLeaf() {
		return children.isEmpty();
	}

	/* 
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.SchemeComponent#hasValidFamily()
	 */
	/**
	 * {@inheritDoc}
	 * @since 1.0.0
	 */
	@Override
	public boolean hasValidFamily() {
	    // check for cycles
		if (isAncestorOf(this) || isDescendantOf(this))
			return false;
		// check for 1-1-pairings with parents and children
		// TODO Tests for this
		for (SchemeComponent c : parents)
			if (!c.isParentOf(this))
				return false;
		for (SchemeComponent c : children)
			if (!c.isChildOf(this))
				return false;
		return true;
	}
	
	/*
     * (non-JavaDoc)
     *
     * @see org.abos.schemes.SchemeComponent#forceFamilyTogether()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
	@Override
	public void forceFamilyTogether() {
		for (SchemeComponent c : parents)
			if (!c.isParentOf(this))
				c.addChild(this);
		for (SchemeComponent c : children)
			if (!c.isChildOf(this))
				c.addParent(this);
	}

	/*
     * (non-JavaDoc)
     *
     * @see org.abos.schemes.SchemeComponent#addParent(org.abos.schemes.
     * SchemeComponent)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
	@Override
	public boolean addParent(SchemeComponent c) {
		if (c == null)
			return false;
		return parents.add(c);
	}

	/*
     * (non-JavaDoc)
     *
     * @see org.abos.schemes.SchemeComponent#addChild(org.abos.schemes.
     * SchemeComponent)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
	@Override
	public boolean addChild(SchemeComponent c) {
		if (c == null)
			return false;
		return children.add(c);
	}

	/*
     * (non-JavaDoc)
     *
     * @see org.abos.schemes.SchemeComponent#removeParent(org.abos.schemes.
     * SchemeComponent)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
	@Override
	public boolean removeParent(SchemeComponent c) {
		return parents.remove(c);
	}

	/*
     * (non-JavaDoc)
     *
     * @see org.abos.schemes.SchemeComponent#removeChild(org.abos.schemes.
     * SchemeComponent)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
	@Override
	public boolean removeChild(SchemeComponent c) {
		return children.remove(c);
	}
	
	/**
	 * Returns an iterator based on the given type.
	 * @param type indicates what the iterator about to be returned will 
	 * iterate through. In this class only the two types 
	 * {@link SchemeComponent#PARENT} and {@link SchemeComponent#CHILD}
	 * are known, but subclasses might introduce other types as well.
	 * @return // Javadoc
	 * @throws IndexOutOfBoundsException If the given type is invalid. 
	 * 
	 * @since 1.0.0
	 * 
	 * @see #isInSuperCollectionOf(SchemeComponent, Collection, int)
     * @see SchemeComponent#PARENT
     * @see SchemeComponent#CHILD
	 */
	// Javadoc throws on invalid type
	public Iterator<? extends SchemeComponent> iterator(int type) {
	    if (type == PARENT)
	        return parentsIterator();
	    if (type == CHILD)
	        return childrenIterator();
	    throw new IndexOutOfBoundsException("Illegal iterator type!");
	}
	
	/*
     * (non-JavaDoc)
     *
     * @see org.abos.schemes.SchemeComponent#parentIterator()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
	@Override
	public Iterator<SchemeComponent> parentsIterator() {
		return parents.iterator();
	}
	
	/*
     * (non-JavaDoc)
     *
     * @see org.abos.schemes.SchemeComponent#childrenIterator()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
	@Override
	public Iterator<SchemeComponent> childrenIterator() {
		return children.iterator();
	}
	
	/*
     * (non-JavaDoc)
     *
     * @see org.abos.schemes.SchemeComponent#deepEquals(SchemeComponent)
     */
    /**
     * {@inheritDoc} <br>
     * This method is realized by using the <code>deepEquals</code> methods
     * of {@link ArrayScheme}.
     * 
     * @since 1.0.0
     * 
     * 
     * @see ArrayScheme#deepEqualsRecursiveDown(SchemeComponent, SchemeComponent, HashSet)
     * @see ArrayScheme#deepCopyRecursiveUp(SchemeComponent, SchemeComponent, ArrayScheme, java.util.HashMap)
     */
	// Javadoc Change params according to final form of the given methods in ArrayScheme
    @Override
	public boolean deepEquals(SchemeComponent other)  {
        if (other == null)
            return false;
        if (!this.equals(other))
            return false;
        // use deepEquals of ArrayScheme
        ArrayScheme<AbstractSchemeComponent> as = 
                new ArrayScheme<AbstractSchemeComponent>();
        if (!as.deepEqualsRecursiveDown(this, other, 
                new HashSet<SchemeComponent>()))
            return false;
        return as.deepEqualsRecursiveUp(this, other, 
                new HashSet<SchemeComponent>());
    }
    
}
