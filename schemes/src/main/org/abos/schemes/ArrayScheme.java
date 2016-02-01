// LICENSE
package org.abos.schemes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Resizable-array implementation of the {@link Scheme} interface using an
 * {@link ArrayList}. <code>null</code>-entries are not permitted and will
 * simply be ignored.<br>
 * This class is not thread-safe.
 * 
 * @author Sebastian Koch
 * @version 1.0.0
 * @since 1.0.0
 * 
 * @see Scheme
 * @see AbstractSchemeComponent
 */
@SuppressWarnings("serial")
public class ArrayScheme<E extends SchemeComponent> extends ArrayList<E>
implements Scheme<E> {

    /**
     * The roots of this scheme.
     * @since 1.0.0
     */
    protected final ArrayList<E> roots;

    /**
     * The leaves of this scheme.
     * @since 1.0.0
     */
    protected final ArrayList<E> leaves;

    /**
     * Constructs an empty array scheme with the specified initial capacity.
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity 
     * is negative
     * 
     * @since 1.0.0
     */
    public ArrayScheme(int initialCapacity) {
        super(initialCapacity);
        roots = new ArrayList<E>();
        leaves = new ArrayList<E>();
    }

    /**
     * Constructs an empty array information scheme with 
     * an initial capacity of ten.
     * 
     * @since 1.0.0
     */
    public ArrayScheme() {
        super();
        roots = new ArrayList<E>();
        leaves = new ArrayList<E>();
    }

    /**
     * Constructs an array scheme containing the elements of the 
     * specified collection, in the order they are returned by the collection's
     * iterator.
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     * 
     * @since 1.0.0
     */
    public ArrayScheme(Collection<? extends E> c) {
        super(c);
        roots = new ArrayList<E>();
        leaves = new ArrayList<E>();
        validateRootsAndLeaves();
    }

    /**
     * Adds a scheme component to the roots or leaves, depending on it 
     * being a root or a leaf or both.
     * @param e the scheme component to add
     * 
     * @since 1.0.0
     */
    protected void addRootOrLeaf(E e) {
        if (e.isRoot())
            roots.add(e);
        if (e.isLeaf())
            leaves.add(e);
    }

    /**
     * Removes a scheme component from the roots or leaves, if it is any of it.
     * @param e the scheme component to remove
     * 
     * @since 1.0.0
     */
    protected void removeRootOrLeaf(E e) {
        roots.remove(e);
        leaves.remove(e);
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.Scheme#getRoots()
     */
    /**
     * {@inheritDoc} If a component is added by
     * {@link #add(int, SchemeComponent)} or changed by
     * {@link #set(int, SchemeComponent)} and if it's a root, it will still be
     * added at the end of the list of roots.
     * 
     * @since 1.0.0
     */
    @Override
    public List<E> getRoots() {
        return new ArrayList<E>(roots);
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.Scheme#getRoot()
     */
    /**
     * {@inheritDoc} If a component is added by
     * {@link #add(int, SchemeComponent)} or changed by
     * {@link #set(int, SchemeComponent)} and if it's a root, it will still be
     * added at the end of the list of roots.
     * 
     * @since 1.0.0
     */
    @Override
    public E getRoot(int index) {
        return roots.get(index);
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.Scheme#getLeaves()
     */
    /**
     * {@inheritDoc} If a component is added by
     * {@link #add(int, SchemeComponent)} or changed by
     * {@link #set(int, SchemeComponent)} and if it's a leaf, it will still be
     * added at the end of the list of leaves.
     * 
     * @since 1.0.0
     */
    @Override
    public List<E> getLeaves() {
        return new ArrayList<E>(leaves);
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.Scheme#getLeaf()
     */
    /**
     * {@inheritDoc} If a component is added by
     * {@link #add(int, SchemeComponent)} or changed by
     * {@link #set(int, SchemeComponent)} and if it's a leaf, it will still be
     * added at the end of the list of leaves.
     * 
     * @since 1.0.0
     */
    @Override
    public E getLeaf(int index) {
        return leaves.get(index);
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.Scheme#containsAncestorOf(org.abos.schemes.
     * SchemeComponent)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public boolean containsAncestorOf(E e) {
        for (E element : this)
            if (element.isAncestorOf(e))
                return true;
        return false;
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.Scheme#containsDescendantOf(org.abos.schemes.
     * SchemeComponent)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public boolean containsDescendantOf(E e) {
        for (E element : this)
            if (element.isDescendantOf(e))
                return true;
        return false;
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.Scheme#contains(org.abos.schemes.SchemeComponent,
     * boolean)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public boolean contains(E e, boolean includeAncestors) {
        return contains(e)
                || (includeAncestors ? containsAncestorOf(e) : false);
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.Scheme#contains(org.abos.schemes.SchemeComponent,
     * boolean, boolean)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public boolean contains(E e, boolean includeAncestors,
            boolean includeDescendants) {
        return contains(e)
                || (includeAncestors ? containsAncestorOf(e) : false)
                || (includeDescendants ? containsDescendantOf(e) : false);
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.Scheme#allFamiliesValid()
     */
    /**
     * {@inheritDoc} This implementation realizes the before-mentioned 
     * suggestion.
     * @since 1.0.0
     */
    @Override
    public boolean allFamiliesValid() {
        for (E e : this)
            if (!e.hasValidFamily())
                return false;
        return true;
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.Scheme#validateRootsAndLeaves()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void validateRootsAndLeaves() {
        roots.clear();
        leaves.clear();
        for (E e : this) {
            addRootOrLeaf(e);
        }
    }

    /*
     * (non-JavaDoc)
     * 
     * @see java.util.ArrayList#add(java.lang.Object)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public boolean add(E e) {
        boolean change = super.add(e);
        if (change)
            addRootOrLeaf(e);
        return change;
    }

    /*
     * @see java.util.ArrayList#add(int, java.lang.Object)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void add(int index, E element) {
        int size = this.size();
        super.add(index, element);
        if (this.size() > size)
            addRootOrLeaf(element);
    }

    /*
     * (non-JavaDoc)
     * 
     * @see java.util.ArrayList#addAll(java.util.Collection)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean change = super.addAll(c);
        if (change) {
            for (E e : c)
                addRootOrLeaf(e);
        }
        return change;
    }

    /*
     * (non-JavaDoc)
     * 
     * @see java.util.ArrayList#addAll(int, java.util.Collection)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean change = super.addAll(index, c);
        if (change) {
            Object[] a = c.toArray();
            for (int i = index; i < a.length; i++) {
                addRootOrLeaf((E) (a[i]));
            }
        }
        return change;
    }

    /*
     * (non-JavaDoc)
     * 
     * @see java.util.ArrayList#set(int, java.lang.Object)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public E set(int index, E element) {
        E e = super.set(index, element);
        if (e != null)
            removeRootOrLeaf(e);
        return e;
    }

    /*
     * (non-JavaDoc)
     * 
     * @see java.util.ArrayList#remove(java.lang.Object)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked") // successful removal implies the type
    @Override
    public boolean remove(Object o) {
        boolean change = super.remove(o);
        if (change)
            removeRootOrLeaf((E) o);
        return change;
    }

    /*
     * (non-JavaDoc)
     * 
     * @see java.util.ArrayList#remove(int)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public E remove(int index) {
        E obj = super.remove(index);
        if (obj != null) {
            removeRootOrLeaf(obj);
        }
        return obj;
    }

    /*
     * (non-JavaDoc)
     * 
     * @see java.util.ArrayList#removeAll(java.util.Collection)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    //@SuppressWarnings("unchecked") // FIXME make safe
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean change = super.removeAll(c);
        if (change) {
            for (Object o : c) {
                if (o instanceof SchemeComponent)
                    removeRootOrLeaf((E) o);
            }
        }
        return change;
    }

    /*
     * (non-JavaDoc)
     * 
     * @see java.util.ArrayList#retainAll(java.util.Collection)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean change = super.retainAll(c);
        if (change) {
            for (E e : this) {
                if (!c.contains(e)) {
                    removeRootOrLeaf(e);
                }
            }
        }
        return change;
    }

    // FIXME replaceAll and removeIf should be added too

    /*
     * (non-JavaDoc)
     * 
     * @see java.util.ArrayList#clear()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void clear() {
        super.clear();
        roots.clear();
        leaves.clear();
    }

    // TODO check and JavaDoc check for the deepEquals and deepCopy
    
    /**
     * Recursively checks if two schemes are equal by checking component (and
     * its children) for component (and its children).
     * 
     * @param ownComp
     *            A component from this scheme.
     * @param otherComp
     *            A component from the other scheme.
     * @param checked
     *            A HashMap to prevent cycles.
     * @return True if the descendants of the two given components are
     *         identical.
     */
    boolean deepEqualsRecursiveDown(SchemeComponent ownComp,
            SchemeComponent otherComp, HashSet<SchemeComponent> checked) {
        Iterator<SchemeComponent> owit = ownComp.childrenIterator();
        Iterator<SchemeComponent> otit = otherComp.childrenIterator();
        // check size
        while (owit.hasNext() && otit.hasNext()) {
            owit.next();
            otit.next();
        }
        if (owit.hasNext() || otit.hasNext())
            return false;
        // check all components of own iterator
        owit = ownComp.childrenIterator();
        SchemeComponent owel = null;
        SchemeComponent otel = null;
        boolean hit = false;
        while (owit.hasNext()) {
            owel = (SchemeComponent) owit.next();
            if (checked.contains(owel))
                continue;
            hit = false;
            // look for corresponding component
            otit = otherComp.childrenIterator();
            while (otit.hasNext()) {
                otel = (SchemeComponent) otit.next();
                if (owel.equals(otel)) {
                    hit = true;
                    break;
                }
            }
            if (!hit)
                return false;
            checked.add(owel);
            hit = deepEqualsRecursiveDown(owel, otel, checked);
            if (!hit)
                return false;
        }
        return true;
    }

    /**
     * Recursively checks if two schemes are equal by checking component (and
     * its children) for component (and its children).
     * 
     * @param ownComp
     *            A component from this scheme.
     * @param otherComp
     *            A component from the other scheme.
     * @param checked
     *            A HashMap to prevent cycles.
     * @return True if the descendants of the two given components are
     *         identical.
     */
    boolean deepEqualsRecursiveUp(SchemeComponent ownComp,
            SchemeComponent otherComp, HashSet<SchemeComponent> checked) {
        Iterator<SchemeComponent> owit = ownComp.parentsIterator();
        Iterator<SchemeComponent> otit = otherComp.parentsIterator();
        // check size
        while (owit.hasNext() && otit.hasNext()) {
            owit.next();
            otit.next();
        }
        if (owit.hasNext() || otit.hasNext())
            return false;
        // check all components of own iterator
        owit = ownComp.parentsIterator();
        SchemeComponent owel = null;
        SchemeComponent otel = null;
        boolean hit = false;
        while (owit.hasNext()) {
            owel = (SchemeComponent) owit.next();
            if (checked.contains(owel))
                continue;
            hit = false;
            // look for corresponding component
            otit = otherComp.parentsIterator();
            while (otit.hasNext()) {
                otel = (SchemeComponent) otit.next();
                if (owel.equals(otel)) {
                    hit = true;
                    break;
                }
            }
            if (!hit)
                return false;
            checked.add(owel);
            hit = deepEqualsRecursiveUp(owel, otel, checked);
            if (!hit)
                return false;
        }
        return true;
    }

    /*
     * (non-JavaDoc)
     * 
     * @see tug.main.knowledge.Scheme#deepEquals(tug.main.knowledge.Scheme)
     */
    /**
     * {@inheritDoc} <br>
     * By now, this method will fail to work if one of the schemes has closed
     * cycles (i.e. elements that are not connected to a root or a leave).
     */
    @Override
    public boolean deepEquals(Scheme<E> scheme) {
        if (scheme == null)
            return false;
        // check children
        AbstractSchemeComponent ownRoot = new AbstractSchemeComponent() {
        };
        for (E e : roots)
            ownRoot.addChild(e);
        AbstractSchemeComponent otherRoot = new AbstractSchemeComponent() {
        };
        for (E e : scheme.getRoots())
            otherRoot.addChild(e);
        if (!deepEqualsRecursiveDown(ownRoot, otherRoot,
                new HashSet<SchemeComponent>(this.size())))
            return false;
        // check parents
        ownRoot = new AbstractSchemeComponent() {
        };
        for (E e : leaves)
            ownRoot.addParent(e);
        otherRoot = new AbstractSchemeComponent() {
        };
        for (E e : scheme.getLeaves())
            otherRoot.addParent(e);
        return deepEqualsRecursiveUp(ownRoot, otherRoot,
                new HashSet<SchemeComponent>(this.size()));
    }

    @SuppressWarnings("unchecked")
    void deepCopyRecursiveDown(SchemeComponent oldComp, SchemeComponent newComp,
            ArrayScheme<E> newScheme, 
            HashMap<SchemeComponent, SchemeComponent> checked) 
            throws CloneNotSupportedException {
        Iterator<SchemeComponent> oldIt = oldComp.childrenIterator();
        SchemeComponent oldElem = null;
        SchemeComponent newElem = null;
        while (oldIt.hasNext()) {
            oldElem = oldIt.next();
            // look if parent should be added to a child 
            if (checked.containsKey(oldElem)) { 
                // if child has been added before
                newElem = checked.get(oldElem);
                if (!newComp.isParentOf(newElem))
                    newComp.addChild(newElem);
                if (!newElem.isChildOf(newComp))
                    newElem.addParent(newComp);
            } // completely new element
            else {
                newElem = (SchemeComponent) oldElem.clone();
                newElem.addParent(newComp);
                newComp.addChild(newElem);
                checked.put(oldElem, newElem); // basically the same element
                if (this.contains(oldElem)) 
                    // if contained, will be eventually iterated over
                    newScheme.add((E) newElem);
                else // else we don't want to miss a spot
                    deepCopyRecursiveUp(oldElem, newElem, newScheme, checked);
                // regarding missed spots: secure unsafed parents
                // FIXME This looks like redundant code and could possibly be made cleaner.
                // FIXME Also improve the recursiveUp method
//                Iterator<SchemeComponent> oldParentIt = 
//                    oldElem.parentsIterator();
//                SchemeComponent oldParent = null;
//                SchemeComponent newParent = null;
//                while (oldParentIt.hasNext()) {
//                    oldParent = oldParentIt.next();
//                    if (!this.contains(oldParent) && 
//                        !checked.containsKey(oldParent)) {
//                        newParent = (SchemeComponent) oldParent.clone();
//                        newParent.addChild(newElem);
//                        newElem.addParent(newParent);
//                        checked.put(oldParent, newParent); // basically the same element
//                        deepCopyRecursiveUp(oldParent, newParent, 
//                            newScheme, checked);
//                    }
//                }
                deepCopyRecursiveDown(oldElem, newElem, newScheme, checked);
            }
        } // -> while (go through children of old element)
    }

    @SuppressWarnings("unchecked")
    void deepCopyRecursiveUp(SchemeComponent oldComp, SchemeComponent newComp,
            ArrayScheme<E> newScheme, 
            HashMap<SchemeComponent, SchemeComponent> checked) 
            throws CloneNotSupportedException {
        Iterator<SchemeComponent> oldIt = oldComp.parentsIterator();
        SchemeComponent oldElem = null;
        SchemeComponent newElem = null;
        while (oldIt.hasNext()) {
            oldElem = oldIt.next();
            // look if child should be added to a parent
            if (checked.containsKey(oldElem)) { 
                // if parent has been added before
                newElem = checked.get(oldElem);
                if (!newComp.isChildOf(newElem))
                    newComp.addParent(newElem);
                if (!newElem.isParentOf(newComp))
                    newElem.addChild(newComp);
            } // completely new element
            else {
                newElem = (SchemeComponent) oldElem.clone();
                newElem.addChild(newComp);
                newComp.addParent(newElem);
                if (this.contains(oldElem))
                    // if contained, will eventually be iterated over
                    newScheme.add((E) newElem);
                else // else we don't want to miss a spot
                    deepCopyRecursiveDown(oldElem, newElem, newScheme, checked);
                checked.put(oldElem, newElem); // basically the same element
                deepCopyRecursiveUp(oldElem, newElem, newScheme, checked);
            }
        } // -> while (go through parents of old element)
    }

    /*
     * (non-JavaDoc)
     * 
     * @see tug.main.knowledge.Scheme#deepCopy()
     */
    /**
     * {@inheritDoc} <br>
     * TODO By now, this method will work unpredictable if the scheme has closed 
     * cycles (i.e. elements that are not connected to a root or a leave) or if 
     * there are components outside of the scheme which are connected to
     * components inside the scheme.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Scheme<E> deepCopy() throws CloneNotSupportedException {
        ArrayScheme<E> copy = new ArrayScheme<E>(this.size());
        E newRoot = null;
        HashMap<SchemeComponent, SchemeComponent> checked = 
            new HashMap<SchemeComponent, SchemeComponent>(this.size());
        for (E root : roots) {
            newRoot = (E) root.clone();
            deepCopyRecursiveDown(root, newRoot, copy, checked);
            copy.add(newRoot);
        }
        copy.validateRootsAndLeaves();
        return copy;
    }

    /*
     * (non-JavaDoc)
     * 
     * @see java.lang.Object#hashCode()
     * 
     * @Override public int hashCode() { final int prime = 31; int result =
     * super.hashCode(); result = prime * result + ((leaves == null) ? 0 :
     * leaves.hashCode()); result = prime * result + ((roots == null) ? 0 :
     * roots.hashCode()); return result; }
     * 
     * 
     * (non-JavaDoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @Override public boolean equals(Object obj) { if (this == obj) return
     * true; if (!super.equals(obj)) return false; if (getClass() !=
     * obj.getClass()) return false; ArrayScheme other = (ArrayScheme) obj; if
     * (leaves == null) { if (other.leaves != null) return false; } else if
     * (!leaves.equals(other.leaves)) return false; if (roots == null) { if
     * (other.roots != null) return false; } else if
     * (!roots.equals(other.roots)) return false; return true; }
     */

}
