// LICENSE
package org.abos.schemes;

import java.util.List;

/**
 * An extension of {@link Scheme} to work with scheme components carrying
 * {@link Information}, namely {@link InformationComponent}s. Methods are
 * given to search for certain components based on the names they carry
 * and to convert them into a single String. By providing a list, the components
 * can be ordered to support the XML serialization of the information 
 * components.
 * 
 * @author Sebastian Koch
 * @version 1.0.1
 * @since 1.0.0
 * 
 * @see InformationComponent
 * @see Scheme
 */
public interface InformationScheme<E extends InformationComponent> 
extends Scheme<E>, List<E> {

    /**
     * Returns all components of this scheme which equal the given string. If
     * {@code fifo} is true, all elements of this scheme will be searched 
     * through in the order of this scheme's iterator. Else all elements 
     * connected to the roots will be searched through.
     * @param name the string to use. Equality with the name will be tested.
     * @param fifo Decides which elements will be searched through.
     * @return A list containing all matching elements.
     * 
     * @since 1.0.0
     * 
     * @see InformationComponent#getName()
     */
    public List<E> getByString(String name, boolean fifo);

    /**
     * Returns all components of this scheme which match the given regex. If
     * {@code fifo} is true, all elements of this scheme will be searched 
     * through in the order of this scheme's iterator. Else all elements 
     * connected to the roots will be searched through.
     * @param regex the regular expression to use. Will be matched with the 
     * name.
     * @param fifo Decides which elements will be searched through.
     * @return A list containing all matching elements. The list may be empty,
     * if there weren't any matches, or <code>null</code> if the regex was
     * invalid.
     * 
     * @version 1.0.1
     * @since 1.0.0
     * 
     * @see InformationComponent#getName()
     */
    public List<E> getByRegex(String regex, boolean fifo);
    
    /**
     * Sorts the content of the scheme hierarchically using the
     * <code>compareTo</code> method of {@link InformationScheme}. 
     * However since the components are not totally ordered, the resulting 
     * order of the sort may differ depending on order of insertion. Only the
     * hierarchy can be ensured (and that only if 
     * <code>allFamiliesValid() == true</code>), that means, whenever
     * <code>a.isAncestorOf(b) == true</code> then <code>(indexOf(a) &lt; 
     * indexOf(b)) == true</code> and vice versa.
     * 
     * @since 1.0.0
     * 
     * @see #allFamiliesValid()
     * @see #indexOf(Object)
     * @see InformationComponent#compareTo(InformationComponent)
     * @see InformationComponent#isAncestorOf(SchemeComponent)
     * @see InformationComponent#isDescendantOf(SchemeComponent)
     */
    public void halfsortHierarchically();
  
    /**
     * Returns a string containing all components visually hierarchically
     * ordered except complete cycles. If 
     * <code>allFamiliesValid() == true</code> then the complete scheme plus
     * associated components will be displayed.
     * @return a string representing this scheme to some extend.
     * 
     * @since 1.0.0
     * 
     * @see #allFamiliesValid()
     */
    // Javadoc explain associated components, maybe add additional methods from scheme
    public String rootsToString();

}
