// LICENSE
package org.abos.schemes;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Marks a class as serializable through XML.
 * 
 * @author Sebastian Koch
 * @version 1.0.0
 * @since 1.0.0
 */
public interface XMLSerializable {

    public final static String XML_ERR_EXPECTED_START_DOCUMENT =
        "Unexpected XML start!";

    public final static String XML_ERR_EXPECTED_END_DOCUMENT =
        "Unexpected XML end!";

    public final static String XML_ERR_UNEXPECTED_ELEMENT =
        "Unexpected XML element!";
    
    public final static String XML_ERR_EXPECTED_START_ELEMENT =
        XML_ERR_UNEXPECTED_ELEMENT + " Start element expected";

    public final static String XML_ERR_EXPECTED_END_ELEMENT =
        XML_ERR_UNEXPECTED_ELEMENT + " End element expected";
    
    // Javadoc
    
    /**
     * Calls {@link #writeXMLStart(IntendedXMLStreamWriter)},
     * {@link #writeXMLContent(IntendedXMLStreamWriter)},
     * {@link #writeXMLEnd(IntendedXMLStreamWriter)} in that order.
     * @param sw
     * @throws XMLStreamException 
     */
    public void writeXML(IntendedXMLStreamWriter sw) throws XMLStreamException;
    
    public void writeXMLStart(IntendedXMLStreamWriter sw)
    throws XMLStreamException;
    
    public void writeXMLContent(IntendedXMLStreamWriter sw)
    throws XMLStreamException;
    
    public void writeXMLEnd(IntendedXMLStreamWriter sw)
    throws XMLStreamException;
    
    public void readXML(XMLStreamReader parser, 
    InformationScheme<?> context) throws XMLStreamException;
    
    public void readXMLStart(XMLStreamReader parser, 
    InformationScheme<?> context) throws XMLStreamException;
    
    public void readXMLContent(XMLStreamReader parser, 
    InformationScheme<?> context) throws XMLStreamException;
    
    public void readXMLEnd(XMLStreamReader parser, 
    InformationScheme<?> context) throws XMLStreamException;
    
}
