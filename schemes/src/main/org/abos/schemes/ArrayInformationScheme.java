// LICENSE
package org.abos.schemes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * An implementation of the {@link InformationScheme}. By using scheme 
 * components that carry information (subclasses of 
 * {@link InformationComponent}), this extension of {@link ArrayScheme}
 * can be searched through by Strings, printed and hierarchically ordered.
 * By implementation of the appropriate interfaces, XML import/export 
 * and DOT export are supported, too. <br>
 * This class is not thread-safe.
 * 
 * @author Sebastian Koch
 * @version 1.0.0
 * @since 1.0.0
 * 
 * @see ArrayScheme
 * @see InformationComponent
 */
@SuppressWarnings("serial")
public class ArrayInformationScheme<E extends InformationComponent> 
extends ArrayScheme<E> implements InformationScheme<E>, 
XMLSerializable, XMLSchemeConstants, DOTWriter {

    /**
     * Constructs an empty array information scheme with the specified initial 
     * capacity.
     * @param  initialCapacity the initial capacity of the scheme
     * @throws IllegalArgumentException if the specified initial capacity
     * is negative
     * 
     * @since 1.0.0
     */
    public ArrayInformationScheme(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs an empty array information scheme with 
     * an initial capacity of ten.
     * 
     * @since 1.0.0
     */
    public ArrayInformationScheme() {}

    /**
     * Constructs an array information scheme containing the elements of the 
     * specified collection, in the order they are returned by the collection's
     * iterator.
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     * 
     * @since 1.0.0
     */
    public ArrayInformationScheme(Collection<? extends E> c) {
        super(c);
    }
    
    /**
     * Saves the information scheme as XML to a given writer by using the
     * methods given by {@link XMLSerializable}. <br>
     * No exception will be thrown if this scheme is not hierarchically ordered
     * at the point of saving, but if the data is loaded later with
     * {@link #load(Reader)}, a {@link SchemeDependencyException} will 
     * probably be thrown.
     * @param wr the writer to save the scheme to
     * @throws XMLStreamException
     * 
     * @since 1.0.0
     * 
     * @see #writeXML(IntendedXMLStreamWriter)
     * @see #load(Reader)
     */
    // Javadoc complete throws (NPE?)
    public void save(Writer wr) throws XMLStreamException {
        XMLOutputFactory factory = XMLOutputFactory.newInstance(); 
        XMLStreamWriter sw = factory.createXMLStreamWriter(wr);
        IntendedXMLStreamWriter isw = new IntendedXMLStreamWriter(sw);
        isw.writeStartDocument();
        writeXML(isw);
        isw.writeEndDocument();
        isw.close();
        sw.close();
    }
    
    /**
     * Saves the information scheme as XML to a given file by using the
     * methods given by {@link XMLSerializable}. <br>
     * No exception will be thrown if this scheme is not hierarchically ordered
     * at the point of saving, but if the data is loaded later with
     * {@link #load(Reader)}, a {@link SchemeDependencyException} will 
     * probably be thrown.
     * @param file the file to save the scheme to
     * @throws FileNotFoundException
     * @throws IOException 
     * @throws XMLStreamException
     * 
     * @since 1.0.0
     * 
     * @see #save(Writer)
     * @see #writeXML(IntendedXMLStreamWriter)
     * @see #load(File)
     */
    // Javadoc complete throws (NPE?)
    public void save(File file) throws IOException, XMLStreamException {
        FileWriter fw = new FileWriter(file);
        save(fw);
        fw.close();
    }
    
    /**
     * Saves the information scheme as XML to a given file by using the
     * methods given by {@link XMLSerializable}. <br>
     * No exception will be thrown if this scheme is not hierarchically ordered
     * at the point of saving, but if the data is loaded later with
     * {@link #load(Reader)}, a {@link SchemeDependencyException} will 
     * probably be thrown.
     * @param pathToFile the path to the file to save the scheme to
     * @throws FileNotFoundException
     * @throws IOException 
     * @throws NullPointerException If the <code>pathToFile</code> argument is
     * <code>null</code>.
     * @throws XMLStreamException
     * 
     * @since 1.0.0
     * 
     * @see #save(File)
     * @see #writeXML(IntendedXMLStreamWriter)
     * @see #load(File)
     */
    // Javadoc complete throws
    public void save(String pathToFile) throws IOException, XMLStreamException {
        save(new File(pathToFile));
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
        sw.writeStartElement(XML_SCHEME);
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
     */
    @Override
    public void writeXMLContent(IntendedXMLStreamWriter sw)
            throws XMLStreamException {
        sw.writeStartElement(XML_NODES);
        for (InformationComponent ic : this) {
            ic.writeXML(sw);
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
    
    /**
     * Loads data given as XML from a given reader to the information scheme by 
     * using the methods given by {@link XMLSerializable}.
     * @param rd the reader to load data for the scheme from
     * @throws SchemeDependencyException
     * @throws XMLStreamException
     * 
     * @since 1.0.0
     * 
     * @see #readXML(XMLStreamReader, InformationScheme)
     * @see #save(Writer)
     */
    // Javadoc throws? NPE?
    public void load(Reader rd) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser = factory.createXMLStreamReader(rd);
        if (parser.getEventType() != XMLStreamConstants.START_DOCUMENT)
            throw new XMLStreamException(XML_ERR_EXPECTED_START_DOCUMENT);
        parser.nextTag();
        readXML(parser, null);
        if (parser.getEventType() != XMLStreamConstants.END_DOCUMENT)
            throw new XMLStreamException(XML_ERR_EXPECTED_END_DOCUMENT);
        parser.close();
    }
    
    /**
     * Loads data given as XML from a given file to the information scheme by 
     * using the methods given by {@link XMLSerializable}.
     * @param file the file to load data for the scheme from
     * @throws FileNotFoundException
     * @throws IOException
     * @throws SchemeDependencyException
     * @throws XMLStreamException
     * 
     * @since 1.0.0
     * 
     * @see #load(Reader)
     * @see #readXML(XMLStreamReader, InformationScheme)
     * @see #save(File)
     */
    // Javadoc throws? NPE?
    public void load(File file) throws IOException, XMLStreamException {
        FileReader fr = new FileReader(file);
        load(fr);
        fr.close();
    }
    
    /**
     * Loads data given as XML from a given file to the information scheme by 
     * using the methods given by {@link XMLSerializable}.
     * @param pathToFile the path to the file to load data for the scheme from
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NullPointerException If the <code>pathToFile</code> argument is
     * <code>null</code>.
     * @throws SchemeDependencyException
     * @throws XMLStreamException
     * 
     * @since 1.0.0
     * 
     * @see #load(File)
     * @see #readXML(XMLStreamReader, InformationScheme)
     * @see #save(String)
     */
    // Javadoc throws?
    public void load(String pathToFile) throws IOException, XMLStreamException {
        load(new File(pathToFile));
    }

    /*
     * (non-JavaDoc)
     * 
     * @see
     * org.abos.schemes.XMLSerializable#readXML(javax.xml.stream.
     * XMLStreamReader, org.abos.schemes.Scheme)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void readXML(XMLStreamReader parser, InformationScheme<?> context)
    throws XMLStreamException {
        readXMLStart(parser, context);
        readXMLContent(parser, context);
        readXMLEnd(parser, context);
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.XMLSerializable#readXMLStart(javax.xml.stream.
     * XMLStreamReader)
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
        if (!parser.getLocalName().equals(XML_SCHEME))
            throw new XMLStreamException(XML_ERR_UNEXPECTED_ELEMENT + " " + 
                XML_SCHEME + " tag expected!");
        parser.nextTag();
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.XMLSerializable#readXMLContent(javax.xml.stream.
     * XMLStreamReader, org.abos.schemes.Scheme)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void readXMLContent(XMLStreamReader parser, 
    InformationScheme<?> context) throws XMLStreamException {
        // must be on right element already
        if (parser.getEventType() != XMLStreamConstants.START_ELEMENT)
            throw new XMLStreamException(XML_ERR_EXPECTED_START_ELEMENT);
        if (!parser.getLocalName().equals(XML_NODES))
            throw new XMLStreamException(XML_ERR_UNEXPECTED_ELEMENT + " " + 
                XML_NODES + " tag expected!");
        parser.nextTag();
        while (parser.getEventType() == XMLStreamConstants.START_ELEMENT &&
                parser.getLocalName().equals(XML_NODE)) {
            InformationComponent ic = new InformationComponent();
            ic.readXML(parser, this);
            add((E)ic);//XXX export this somehow
        }
        parser.nextTag();
    }

    /*
     * (non-JavaDoc)
     * 
     * @see
     * org.abos.schemes.XMLSerializable#readXMLEnd(javax.xml.stream.
     * XMLStreamReader)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void readXMLEnd(XMLStreamReader parser, 
    InformationScheme<?> context) throws XMLStreamException {
        // must be on right element already
        if (parser.getEventType() != XMLStreamConstants.END_ELEMENT)
            throw new XMLStreamException(XML_ERR_EXPECTED_END_ELEMENT);
        if (!parser.getLocalName().equals(XML_SCHEME))
            throw new XMLStreamException(XML_ERR_UNEXPECTED_ELEMENT + " " + 
                    XML_NODES + " tag expected!");
        parser.next();
        //parser.nextTag(); // XXX needs to be properly documented or coded
    }

    
    
    /*
     * remember to keep getByRegex in check when making changes here
     */
    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.InformationScheme#getByString(java.lang.String, 
     * boolean)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked") // XXX proper documentation and error warning
    @Override
    public List<E> getByString(String name, boolean fifo) {
        LinkedList<E> result = new LinkedList<E>();
        if (fifo) { // all elements of scheme
            for (E e : this) {
                if (e.getName() != null && e.getName().equals(name))
                    result.add(e);
            }
        } else { // all elements connected to roots
            HashSet<E> toCheck = new HashSet<E>(roots);
            HashSet<E> checked = new HashSet<E>();
            E check = null;
            Iterator<SchemeComponent> it = null;
            E e = null;
            while (!toCheck.isEmpty()) {
                // check one element
                check = toCheck.iterator().next();
                if (check.getName() == null && check.getName().equals(name))
                    result.add(check);
                checked.add(check);
                it = check.childrenIterator();
                // add its children to search
                while (it.hasNext()) {
                    e = (E)it.next();
                    if (!checked.contains(e))
                        toCheck.add(e);
                } //-> while (children to look through)
            } //-> while (elements to check)
        } //-> if (search starting from the roots)
        return result;
    }

    
    
    /*
     * remember to keep getByString in check when making changes here
     */
    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.InformationScheme#getByRegex(java.lang.String, 
     * boolean)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked") // XXX proper documentation and error warning
    @Override
    public List<E> getByRegex(String regex, boolean fifo) {
        LinkedList<E> result = new LinkedList<E>();
        if (fifo) { // all elements of scheme
            for (E e : this) {
                if (e.getName() != null && e.getName().matches(regex))
                    result.add(e);
            }
        } else { // all elements connected to roots
            HashSet<E> toCheck = new HashSet<E>(roots);
            HashSet<E> checked = new HashSet<E>();
            E check = null;
            Iterator<SchemeComponent> it = null;
            E e = null;
            while (!toCheck.isEmpty()) {
                // check one element
                check = toCheck.iterator().next();
                if (check.getName() == null && check.getName().matches(regex))
                    result.add(check);
                checked.add(check);
                it = check.childrenIterator();
                // add its children to search
                while (it.hasNext()) {
                    e = (E)it.next();
                    if (!checked.contains(e))
                        toCheck.add(e);
                } //-> while (children to look through)
            } //-> while (elements to check)
        } //-> if (search starting from the roots)
        return result;
    }
    
    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.InformationScheme#halfsortHierachially()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    public void halfsortHierarchically() {
        Object[] arr = this.toArray();
        Arrays.sort(arr);
        this.clear();
        for (Object ic : arr)
            add((E)ic); // XXX code better document better
    }

    /*
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.InformationScheme#rootsToString()
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public String rootsToString() {
        StringBuilder s = new StringBuilder();
        for (E e : roots) {
            s.append(e.getName());
            s.append('\n');
            s.append(e.descendantsToString());
        }
        return s.toString();
    }

    /* 
     * (non-JavaDoc)
     * 
     * @see org.abos.schemes.DOTWriter#writeDOT(java.io.Writer)
     */
    /**
     * {@inheritDoc}
     * @since 1.0.0
     */
    @Override
    public void writeDOT(Writer wr) throws IOException {
        BufferedWriter bw = new BufferedWriter(wr);
        bw.write(DOT_START);
        for (E ic : this) {
            bw.write(DOT_TAB);
            bw.write(DOT_QUOTES);
            bw.write(ic.getName());
            bw.write(DOT_QUOTES);
            Iterator<SchemeComponent> children = ic.childrenIterator();
            // write children down
            if (!children.hasNext()) {
                bw.write(DOT_EOL);
                continue;
            }
            bw.write(DOT_CHILDREN_START);
            while (true) {
                bw.write(DOT_QUOTES);
                bw.write(((E)children.next()).getName());
                bw.write(DOT_QUOTES);
                if (children.hasNext())
                    bw.write(DOT_CHILDREN_SEP);
                else {
                    bw.write(DOT_CHILDREN_END);
                    break;
                }
            }
        }
        bw.write(DOT_END);
        bw.close();
    }

}
