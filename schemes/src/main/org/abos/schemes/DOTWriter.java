// LICENSE
package org.abos.schemes;

import java.io.IOException;
import java.io.Writer;

/**
 * An interface providing the method {@link #writeDOT(Writer)} to write a DOC
 * document to a writer and several constants as kind of a style guide.
 * 
 * @author Sebastian Koch
 * @version 1.0.0
 * @since 1.0.0
 */
public interface DOTWriter {
    
    /**
     * The end of line in the DOT document.
     * 
     * @since 1.0.0
     */
    public final static String DOT_EOL = "\r\n";
    
    /**
     * The start of the DOT document.
     * 
     * @since 1.0.0
     * @see #DOT_END
     */
    public final static String DOT_START = "digraph G {"+DOT_EOL;
    
    /**
     * The indentation in the DOT document.
     * 
     * @since 1.0.0
     */
    public final static String DOT_TAB = "  ";
    
    /**
     * The quotes in the DOT document.
     * 
     * @since 1.0.0
     */
    public final static String DOT_QUOTES = "\"";
    
    /**
     * The end of the DOT document.
     * 
     * @since 1.0.0
     * @see #DOT_START
     */
    public final static String DOT_END = "}";
    
    /**
     * The start of (several) children in the DOT document.
     * 
     * @since 1.0.0
     * @see #DOT_CHILDREN_SEP
     * @see #DOT_CHILDREN_END
     */
    public final static String DOT_CHILDREN_START = " -> {";
    
    /**
     * The separator between multiple children in the DOT document.
     * 
     * @since 1.0.0
     * @see #DOT_CHILDREN_START
     * @see #DOT_CHILDREN_END
     */
    public final static String DOT_CHILDREN_SEP = "; ";
    
    /**
     * The end of (several) children in the DOT document.
     * 
     * @since 1.0.0
     * @see #DOT_CHILDREN_START
     * @see #DOT_CHILDREN_SEP
     */
    public final static String DOT_CHILDREN_END = "}"+DOT_EOL; 
    
    /**
     * Writes a well-formatted DOT document to a writer.
     * @param wr the writer to write the DOT document to
     * @throws IOException if an i/o error occurs
     * 
     * @since 1.0.0
     */
    public void writeDOT(Writer wr) throws IOException;

}
