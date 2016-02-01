// LICENSE
package org.abos.schemes;

/**
 * Represents information of any kind.
 * 
 * @author Sebastian Koch
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Information {
	
	/**
	 * Returns the name or title of the information.
	 * @return the name or title of the information
	 */
	public String getName();
	
	/**
	 * Returns a description for the information.
	 * @return the description for the information
	 */
	public String getDescription();

}
