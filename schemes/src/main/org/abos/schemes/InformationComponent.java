// LICENSE
package org.abos.schemes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * An extension of {@link AbstractSchemeComponent} implementing
 * {@link Information} and therefore adding content to the scheme component,
 * thus meaningful overriding {@link #equals(Object)}, {@link #hashCode()}
 * and {@link #clone()}.
 * By implementation of the appropriate interfaces, XML import/export 
 * is supported, too. DOT support is given via 
 * {@link ArrayInformationScheme}. <br>
 * To avoid dependency exception during reading from XML, this class implements
 * the {@link java.lang.Comparable} interface, but due to the nature of 
 * scheme components not forming a total order with the relation on 
 * ancestors and descendants, the third part of the contract of
 * <code>Comparable</code> is violated. To be more precise, there can be
 * <tt>x, y, z</tt> such that <tt>x.compareTo(y)==0</tt> but
 * <tt>sgn(x.compareTo(z)) != sgn(y.compareTo(z))</tt>. Furthermore, symmetry
 * and transitivity can only promised to be fulfilled if 
 * <tt>hasValidFamily() == true</tt>.<br>
 * This class is not thread-safe.
 * 
 * @author Sebastian Koch
 * @version 1.0.0
 * @since 1.0.0
 * 
 * @see AbstractSchemeComponent
 * @see ArrayInformationScheme
 * @see Information
 * @see java.lang.Comparable
 */
public class InformationComponent extends AbstractSchemeComponent 
implements Information, XMLSerializable, XMLSchemeConstants, 
Comparable<InformationComponent> {
    
    /**
     * The name of this component.
     * 
     * @since 1.0.0
     */
    protected String name;

    /**
     * The description of this component.
     * 
     * @since 1.0.0
     */
    protected String description;
    
    /**
     * Creates an information component with empty name and description.
     * 
     * @since 1.0.0
     */
    public InformationComponent() {
        this("", "");
    }

    /**
     * Creates a named information component with empty description.
     * @param name the name of the component
     * 
     * @since 1.0.0
     */
    public InformationComponent(String name) {
        this(name, "");
    }
    
    /**
     * Creates a named information component with a description.
     * @param name the name of the component
     * @param description the description of the component
     * 
     * @since 1.0.0
     */
    public InformationComponent(String name, String description) {
        // note that parents and children will be initialized 
        this.name = name;
        this.description = description;
    }

    /* 
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.Information#getName()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public String getName() {
        return name;
    }

    /* 
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.Information#getDescription()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public String getDescription() {
        return description;
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.XMLSerializable#writeXML(org.abos.schemes.
     * IntendedXMLStreamWriter)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeXML(IntendedXMLStreamWriter sw) throws XMLStreamException {
        writeXMLStart(sw);
        writeXMLContent(sw);
        writeXMLEnd(sw);
    }
    
    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.XMLSerializable#writeXMLStart(org.abos.schemes.
     * IntendedXMLStreamWriter)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeXMLStart(IntendedXMLStreamWriter sw) 
    throws XMLStreamException {
        sw.writeStartElement(XML_NODE);
    }
    
    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.XMLSerializable#writeXMLContent(org.abos.schemes.
     * IntendedXMLStreamWriter)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     * 
     * @see #writeXMLInformation(IntendedXMLStreamWriter)
     * @see #writeXMLFamily(IntendedXMLStreamWriter)
     */
    @Override
    public void writeXMLContent(IntendedXMLStreamWriter sw) 
    throws XMLStreamException {
        writeXMLInformation(sw);
        writeXMLFamily(sw);
    }
    
    /**
     * Writes the information of this scheme component (that means its data
     * that isn't family) to the specified writer.
     * @param sw the intended stream writer to write the data to
     * @throws XMLStreamException
     * 
     * @since 1.0.0
     */
    // Javadoc throws (NPE?)
    protected void writeXMLInformation(IntendedXMLStreamWriter sw) 
    throws XMLStreamException {
        sw.writeFullElement(XML_NAME, getName());
        sw.writeStartElement(XML_DESCRIPTION);
          sw.writeCharacters(getDescription());
        sw.writeEndElement();
    }
    
    /**
     * Writes the family of this scheme component to the specified writer.
     * @param sw the intended stream writer to write the data to
     * @throws ClassCastException If any parent isn't an {@link Information}.
     * @throws XMLStreamException
     * 
     * @since 1.0.0
     * 
     * @see Information
     */
    // Javadoc throws (NPE?)
    protected void writeXMLFamily(IntendedXMLStreamWriter sw)
    throws XMLStreamException {
        sw.writeStartElement(XML_PARENTS);
        for (SchemeComponent sc : parents) { // XXX needs proper documentation
            sw.writeFullElement(XML_PARENT, ((Information)sc).getName());
        }
        sw.writeEndElement();
    }
    
    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.XMLSerializable#writeXMLEnd(org.abos.schemes.
     * IntendedXMLStreamWriter)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeXMLEnd(IntendedXMLStreamWriter sw) 
    throws XMLStreamException {
        sw.writeEndElement();
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.XMLSerializable#readXML(javax.xml.stream.
     * XMLStreamReader, org.abos.schemes.InformationScheme)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void readXML(XMLStreamReader parser, 
    InformationScheme<?> context) throws XMLStreamException {
        readXMLStart(parser, context);
        readXMLContent(parser, context);
        readXMLEnd(parser, context);
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.XMLSerializable#readXMLStart(javax.xml.stream.
     * XMLStreamReader, org.abos.schemes.InformationScheme)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void readXMLStart(XMLStreamReader parser, 
    InformationScheme<?> context) throws XMLStreamException {
        // must be on right element already
        if (parser.getEventType() != XMLStreamConstants.START_ELEMENT)
            throw new XMLStreamException(XML_ERR_EXPECTED_START_ELEMENT);
        if (!parser.getLocalName().equals(XML_NODE))
            throw new XMLStreamException(XML_ERR_UNEXPECTED_ELEMENT + " " + 
                XML_NODE + " tag expected!");
        parser.nextTag();
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.XMLSerializable#readXMLContent(javax.xml.stream.
     * XMLStreamReader, org.abos.schemes.InformationScheme)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     * 
     * @see #readXMLInformation(XMLStreamReader)
     * @see #readXMLFamily(XMLStreamReader, InformationScheme)
     */
    @Override
    public void readXMLContent(XMLStreamReader parser, 
    InformationScheme<?> context) throws XMLStreamException {
        readXMLInformation(parser);
        readXMLFamily(parser, context);
    }
    
    /**
     * Reads information from a given xml stream reader into this component.
     * @param parser the xml stream reader to read from
     * @throws XMLStreamException
     * 
     * @since 1.0.0
     */
    // Javadoc throws (NPE?)
    protected void readXMLInformation(XMLStreamReader parser) 
    throws XMLStreamException {
        // name of the node
        String s = parser.getElementText();
        if (!parser.getLocalName().equals(XML_NAME))
            throw new XMLStreamException(XML_ERR_UNEXPECTED_ELEMENT + " " + 
                    XML_NAME + " tag expected!");
        this.name = s;
        parser.nextTag();
        
        // description of the node
        s = parser.getElementText().trim();
        if (!parser.getLocalName().equals(XML_DESCRIPTION))
            throw new XMLStreamException(XML_ERR_UNEXPECTED_ELEMENT + " " + 
                    XML_DESCRIPTION + " tag expected!");
        this.description = s;
        parser.nextTag();
    }
    
    /**
     * Reads the family from a given xml stream reader into this component.
     * @param parser the xml stream reader to read from
     * @param context the scheme to look through for parents 
     * @throws SchemeDependencyException If a certain dependency couldn't be
     * fulfilled with the given <code>context</code>.
     * @throws XMLStreamException
     * 
     * @since 1.0.0
     */
    // Javadoc throws (NPE?)
    protected void readXMLFamily(XMLStreamReader parser, 
    InformationScheme<?> context) throws XMLStreamException {
        // parents of the node
        if (parser.getEventType() != XMLStreamConstants.START_ELEMENT)
            throw new XMLStreamException(XML_ERR_EXPECTED_START_ELEMENT);
        if (!parser.getLocalName().equals(XML_PARENTS))
            throw new XMLStreamException(XML_ERR_UNEXPECTED_ELEMENT + " " + 
                    XML_PARENTS + " tag expected!");
        
        // read in parents
        HashSet<String> parents = new HashSet<String>();
        while (true) {
            parser.nextTag();
            if (parser.getLocalName().equals(XML_PARENTS)) { 
                if (parser.getEventType() != XMLStreamConstants.END_ELEMENT)
                    throw new XMLStreamException(XML_ERR_EXPECTED_END_ELEMENT);
                break;
            }
            // add parent as string
            if (!parents.add(parser.getElementText()))
                // if parent string already there
                ; // XXX exception?
            if (!parser.getLocalName().equals(XML_PARENT))
                throw new XMLStreamException(XML_ERR_UNEXPECTED_ELEMENT + " " + 
                    XML_PARENT + " tag expected!");
        }
        
        // check if parents are valid
        if (parents.contains(null))
            ; // XXX exception? can it even happen?
        List<? extends SchemeComponent> ppL; // "possible parents list"
        for (String s : parents) {
            ppL = context.getByString(s, true);
            if (ppL.isEmpty())
                throw new SchemeDependencyException("Parent "+s+" missing!");
            this.parents.add(ppL.get(0)); // Javadoc XXX needs to be properly documented
        }
        this.forceFamilyTogether();
        parser.nextTag(); // XXX because next method will expect this
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.XMLSerializable#readXMLEnd(javax.xml.stream.
     * XMLStreamReader, org.abos.schemes.InformationScheme)
     */
    @Override
    public void readXMLEnd(XMLStreamReader parser, 
    InformationScheme<?> context) throws XMLStreamException {
        // must be on right element already
        if (parser.getEventType() != XMLStreamConstants.END_ELEMENT)
            throw new XMLStreamException(XML_ERR_EXPECTED_END_ELEMENT);
        if (!parser.getLocalName().equals(XML_NODE))
            throw new XMLStreamException(XML_ERR_UNEXPECTED_ELEMENT + " " + 
                XML_NODE + " tag expected!");
        parser.nextTag();
    }

    /*
     * (non-JavaDoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
               + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /* 
     * (non-JavaDoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        InformationComponent other = (InformationComponent) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
    /**
     * Returns a string describing the descendants of this component.
     * @return a string describing the descendants of this component
     * @since 1.0.0
     */
    public String descendantsToString() {
        StringBuilder s = new StringBuilder();
        int level = 0;
        ArrayList<Iterator<SchemeComponent>> its = 
                new ArrayList<Iterator<SchemeComponent>>();
        HashMap<SchemeComponent, Integer> marked = 
            new HashMap<SchemeComponent, Integer>();
        SchemeComponent c = null;
        Iterator<SchemeComponent> it = childrenIterator();
        // add that we have this one earlier
        while (it.hasNext())
            marked.put(it.next(), level);
        its.add(childrenIterator());
        /*
         * We go into each branch of every child to add then recursively.
         */
        while (!its.isEmpty()) {
            if (!its.get(level).hasNext()) {
                its.remove(level);
                level--;
                continue;
            }
            c = its.get(level).next();
            // draw tree
            for (int i = 0; i < level; i++) s.append(' ');
            if (its.get(level).hasNext())
                s.append('├');
            else
                s.append('└');
            s.append((c instanceof Information) ? 
                ((Information)c).getName() : c.toString());
            // ensure children will be printed too
            if (marked.get(c) == null) { // (should never happen)
                marked.put(c, level);
            }
            else if (marked.get(c) != level) { // will be / has been added
                if (!c.isLeaf()) // no endless circles
                    s.append(" → ...");
            }
            else { // not added yet, but is on this level
                marked.put(c, -1);
                if (!c.isLeaf()) {
                    its.add(c.childrenIterator());
                    level++;
                    it = c.childrenIterator();
                    while (it.hasNext()) {
                        c = it.next();
                        if (marked.get(c) == null)
                            marked.put(c, level);
                    }
                } //-> if !c.isLeaf()
            }
            s.append('\n');
        }
        return s.toString();
    }
    
    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.AbstractSchemeComponent#clone()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new InformationComponent(name, description);
    }

    /**
     * <b>For a description what this method will do, see
     * {@link InformationComponent}. For a description what it should do,
     * see below.</b><br><br>
     * {@inheritDoc}
     * @since 1.0.0
     * 
     * @see InformationComponent
     */
    @Override
    public int compareTo(InformationComponent o) {
        if (this.isAncestorOf(o) || o.isDescendantOf(this))
            return -1;
        if (this.isDescendantOf(o) || o.isAncestorOf(this))
            return 1;
        return 0;
    }
    
    /*
     * (non-JavaDoc)
     *
     * @see java.lang.Object#toString()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    public String toString() {
        return getName();
    }

}
