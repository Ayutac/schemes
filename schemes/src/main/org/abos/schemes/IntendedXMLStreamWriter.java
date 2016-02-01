// LICENSE
package org.abos.schemes;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * An XML stream writer that intends and insert line breaks automatically.
 * 
 * @author Sebastian Koch
 * @version 1.0.0
 * @since 1.0.0
 */
public class IntendedXMLStreamWriter implements XMLStreamWriter {

    protected XMLStreamWriter writer;
    
    /**
     * The indentation for the XML stream writer.
     * 
     * @since 1.0.0
     */
    protected StringBuilder indentation;
    
    /**
     * The tab character(s) for the XML stream writer.
     * 
     * @since 1.0.0
     */
    protected String tab = "  ";
    
    /**
     * The length of the tab.
     * 
     * @since 1.0.0
     */
    protected int tabLength = tab.length();
    
    /**
     * The line break to use in the XML stream writer.
     * 
     * @since 1.0.0
     */
    protected String linebreak = "\r\n";
    
    /**
     * Creates a new intended XML stream writer from a given XML stream writer.
     * @param writer the XML stream writer to wrap around
     * 
     * @since 1.0.0
     */
    public IntendedXMLStreamWriter(XMLStreamWriter writer) {
        this.writer = writer;
        indentation = new StringBuilder();
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeStartElement(java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeStartElement(String localName) throws XMLStreamException {
        writer.writeCharacters(indentation.toString());
        writer.writeStartElement(localName);
        indentation.append(tab);
        writer.writeCharacters(linebreak);
    }
    

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeStartElement(java.lang.String, 
     * java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeStartElement(String namespaceURI, String localName)
            throws XMLStreamException {
        writer.writeCharacters(indentation.toString());
        writer.writeStartElement(namespaceURI, localName);
        indentation.append(tab);
        writer.writeCharacters(linebreak);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeStartElement(java.lang.String, 
     * java.lang.String, java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeStartElement(String prefix, String localName,
            String namespaceURI) throws XMLStreamException {
        writer.writeCharacters(indentation.toString());
        writer.writeStartElement(prefix, localName, namespaceURI);
        indentation.append(tab);
        writer.writeCharacters(linebreak);
    }
    
    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeEmptyElement(java.lang.String, 
     * java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeEmptyElement(String namespaceURI, String localName)
            throws XMLStreamException {
        writer.writeCharacters(indentation.toString());
        writer.writeEmptyElement(namespaceURI, localName);
        writer.writeCharacters(linebreak);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeEmptyElement(java.lang.String, 
     * java.lang.String, java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeEmptyElement(String prefix, String localName,
            String namespaceURI) throws XMLStreamException {
        writer.writeCharacters(indentation.toString());
        writer.writeEmptyElement(prefix, localName, namespaceURI);
        writer.writeCharacters(linebreak);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeEmptyElement(java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeEmptyElement(String localName) throws XMLStreamException {
        writer.writeCharacters(indentation.toString());
        writer.writeEmptyElement(localName);
        writer.writeCharacters(linebreak);   
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeEndElement()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeEndElement() throws XMLStreamException {
        indentation.setLength(indentation.length()-tabLength);
        writer.writeCharacters(indentation.toString());
        writer.writeEndElement();
        writer.writeCharacters(linebreak);  
    }

    // Javadoc write
    public void writeFullElement(String localName, String text) 
            throws XMLStreamException {
        writer.writeCharacters(indentation.toString());
        writer.writeStartElement(localName);
        writer.writeCharacters(text);
        writer.writeEndElement();
        writer.writeCharacters(linebreak);
    }

    public void writeFullElement(String namespaceURI, String localName,
            String text) throws XMLStreamException {
        writer.writeCharacters(indentation.toString());
        writer.writeStartElement(namespaceURI, localName);
        writer.writeCharacters(text);
        writer.writeEndElement();
        writer.writeCharacters(linebreak);
    }

    public void writeFullElement(String prefix, String localName,
            String namespaceURI, String text) throws XMLStreamException {
        writer.writeCharacters(indentation.toString());
        writer.writeStartElement(prefix, localName, namespaceURI);
        writer.writeCharacters(text);
        writer.writeEndElement();
        writer.writeCharacters(linebreak);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeEndDocument()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeEndDocument() throws XMLStreamException {
        writer.writeEndDocument();    
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#close()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void close() throws XMLStreamException {
        writer.close();
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#flush()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void flush() throws XMLStreamException {
        writer.flush();    
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeAttribute(java.lang.String, java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeAttribute(String localName, String value)
            throws XMLStreamException {
        writer.writeAttribute(localName, value);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeAttribute(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeAttribute(String prefix, String namespaceURI,
            String localName, String value) throws XMLStreamException {
        writer.writeAttribute(prefix, namespaceURI, localName, value);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeAttribute(java.lang.String, java.lang.String, java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeAttribute(String namespaceURI, String localName,
            String value) throws XMLStreamException {
        writer.writeAttribute(namespaceURI, localName, value);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeNamespace(java.lang.String, java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeNamespace(String prefix, String namespaceURI)
            throws XMLStreamException {
        writer.writeNamespace(prefix, namespaceURI);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeDefaultNamespace(java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeDefaultNamespace(String namespaceURI)
            throws XMLStreamException {
        writer.writeDefaultNamespace(namespaceURI);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeComment(java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeComment(String data) throws XMLStreamException {
        writer.writeComment(data);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeProcessingInstruction(java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeProcessingInstruction(String target)
            throws XMLStreamException {
        writer.writeProcessingInstruction(target);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeProcessingInstruction(java.lang.String, java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeProcessingInstruction(String target, String data)
            throws XMLStreamException {
        writer.writeProcessingInstruction(target, data);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeCData(java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeCData(String data) throws XMLStreamException {
        writer.writeCharacters(indentation.toString());
        writer.writeCData(data);
        writer.writeCharacters(linebreak);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeDTD(java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeDTD(String dtd) throws XMLStreamException {
        writer.writeDTD(dtd);
        writer.writeCharacters(linebreak);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeEntityRef(java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeEntityRef(String name) throws XMLStreamException {
        writer.writeEntityRef(name);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeStartDocument()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeStartDocument() throws XMLStreamException {
        writer.writeStartDocument();
        writer.writeCharacters(linebreak);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeStartDocument(
     * java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeStartDocument(String version) throws XMLStreamException {
        writer.writeStartDocument(version);
        writer.writeCharacters(linebreak);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeStartDocument(java.lang.
     * String, java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeStartDocument(String encoding, String version)
            throws XMLStreamException {
        writer.writeStartDocument(encoding, version);
        writer.writeCharacters(linebreak);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeCharacters(java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeCharacters(String text) throws XMLStreamException {
        writer.writeCharacters(indentation.toString());
        writer.writeCharacters(text);
        writer.writeCharacters(linebreak);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#writeCharacters(char[], int, int)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeCharacters(char[] text, int start, int len)
            throws XMLStreamException {
        writer.writeCharacters(indentation.toString());
        writer.writeCharacters(text, start, len);
        writer.writeCharacters(linebreak);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#getPrefix(java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public String getPrefix(String uri) throws XMLStreamException {
        return writer.getPrefix(uri);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#setPrefix(java.lang.String, 
     * java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void setPrefix(String prefix, String uri) throws XMLStreamException {
        writer.setPrefix(prefix, uri);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#setDefaultNamespace(
     * java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void setDefaultNamespace(String uri) throws XMLStreamException {
        writer.setDefaultNamespace(uri);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#setNamespaceContext(
     * javax.xml.namespace.NamespaceContext)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void setNamespaceContext(NamespaceContext context)
            throws XMLStreamException {
        writer.setNamespaceContext(context);
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#getNamespaceContext()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public NamespaceContext getNamespaceContext() {
        return writer.getNamespaceContext();
    }

    /* 
     * (non-JavaDoc)
     *
     * @see javax.xml.stream.XMLStreamWriter#getProperty(java.lang.String)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public Object getProperty(String name) throws IllegalArgumentException {
        return writer.getProperty(name);
    }

}
