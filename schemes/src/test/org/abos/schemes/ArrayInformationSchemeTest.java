package org.abos.schemes;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import org.abos.schemes.InformationComponent;
import org.abos.schemes.ArrayInformationScheme;
import org.junit.Test;

/**
 * @author Sebastian Koch
 *
 */
public class ArrayInformationSchemeTest {

    ArrayInformationScheme<InformationComponent> createExample1() {
        ArrayInformationScheme<InformationComponent> ais = 
            new ArrayInformationScheme<InformationComponent>();
        InformationComponent game = new InformationComponent("The game");
        ais.add(game);
        return ais;
    }
    
    ArrayInformationScheme<InformationComponent> createExample2() {
        ArrayInformationScheme<InformationComponent> ais = 
            new ArrayInformationScheme<InformationComponent>();
        InformationComponent mom = new InformationComponent("Mutter");
        InformationComponent dad = new InformationComponent("Vater");
        InformationComponent bro = new InformationComponent("Bruder");
        InformationComponent sis = new InformationComponent("Schwester");
        mom.addChild(bro);
        mom.addChild(sis);
        dad.addChild(bro);
        dad.addChild(sis);
        mom.forceFamilyTogether();
        dad.forceFamilyTogether();
        ais.add(mom);
        ais.add(dad);
        ais.add(bro);
        ais.add(sis);
        return ais;
    }
    
    ArrayInformationScheme<InformationComponent> createExample3() {
        ArrayInformationScheme<InformationComponent> ais = 
            new ArrayInformationScheme<InformationComponent>();
        InformationComponent lit = new InformationComponent("Literature");
        InformationComponent aut = new InformationComponent("Authors");
        InformationComponent lino = new InformationComponent("Light Novels");
        InformationComponent isin = new InformationComponent("Nisio Isin");
        InformationComponent dnln = new InformationComponent("DN (LN)");
        lit.addChild(aut);
        lit.addChild(lino);
        lit.forceFamilyTogether();
        aut.addChild(isin);
        aut.forceFamilyTogether();
        dnln.addParent(isin);
        dnln.addParent(lino);
        dnln.forceFamilyTogether();
        ais.add(lit);
        ais.add(aut);
        ais.add(lino);
        ais.add(isin);
        ais.add(dnln);
        return ais;
    }
    
    @Test
    public void deepEqualsTest1() {
        ArrayInformationScheme<InformationComponent> ais1 = createExample3();
        ArrayInformationScheme<InformationComponent> ais2 = createExample3();
        assertTrue(ais1.deepEquals(ais2));
    }
    
    @Test
    public void deepEqualsTest2() {
        ArrayInformationScheme<InformationComponent> ais1 = createExample3();
        ArrayInformationScheme<InformationComponent> ais2 = createExample3();
        ais2.remove(0);
        assertFalse(ais1.deepEquals(ais2));
    }
    
    @Test
    public void deepEqualsTest3() {
        ArrayInformationScheme<InformationComponent> ais = createExample3();
        assertTrue(ais.deepEquals(ais));
    }
    
    @Test
    public void deepCopyTest1() throws CloneNotSupportedException {
        ArrayInformationScheme<InformationComponent> ais = createExample1();
        assertTrue(ais.deepEquals(ais.deepCopy()));
    }
    
    @Test
    public void deepCopyTest2() throws CloneNotSupportedException {
        ArrayInformationScheme<InformationComponent> ais1 = createExample1();
        ArrayScheme<InformationComponent> ais2 = 
            (ArrayScheme<InformationComponent>)ais1.deepCopy();
        ais2.remove(0);
        assertFalse(ais1.deepEquals(ais2.deepCopy()));
    }
    
    @Test
    public void deepCopyTest3() throws CloneNotSupportedException {
        ArrayInformationScheme<InformationComponent> ais1 = createExample1();
        ArrayScheme<InformationComponent> ais2 = 
            (ArrayScheme<InformationComponent>)ais1.deepCopy();
        InformationComponent deleted = ais1.remove(0);
        ais2.remove(deleted);
        assertTrue(ais1.deepEquals(ais2.deepCopy()));
    }
    
    @Test
    public void deepCopyTest4() throws CloneNotSupportedException {
        ArrayInformationScheme<InformationComponent> ais = createExample2();
        assertTrue(ais.deepEquals(ais.deepCopy()));
    }
    
    @Test
    public void deepCopyTest5() throws CloneNotSupportedException {
        ArrayInformationScheme<InformationComponent> ais1 = createExample2();
        ArrayScheme<InformationComponent> ais2 = 
            (ArrayScheme<InformationComponent>)ais1.deepCopy();
        ais2.remove(0);
        assertFalse(ais1.deepEquals(ais2.deepCopy()));
    }
    
    @Test
    public void deepCopyTest6() throws CloneNotSupportedException {
        ArrayInformationScheme<InformationComponent> ais1 = createExample2();
        ArrayScheme<InformationComponent> ais2 = 
            (ArrayScheme<InformationComponent>)ais1.deepCopy();
        InformationComponent deleted = ais1.remove(0);
        ais2.remove(deleted);
        assertTrue(ais1.deepEquals(ais2));
    }
    
    @Test
    public void deepCopyTest10() throws CloneNotSupportedException {
        ArrayInformationScheme<InformationComponent> ais1 = createExample2();
        ArrayScheme<InformationComponent> ais2 = 
            (ArrayScheme<InformationComponent>)ais1.deepCopy();
        InformationComponent deleted = ais1.remove(0);
        ais2.remove(deleted);
        assertTrue(ais1.deepEquals(ais2.deepCopy()));
    }
    
    @Test
    public void deepCopyTest11() throws CloneNotSupportedException {
        ArrayInformationScheme<InformationComponent> ais1 = createExample3();
        ArrayScheme<InformationComponent> ais2 = 
            (ArrayScheme<InformationComponent>)ais1.deepCopy().deepCopy();
        assertTrue(ais1.deepEquals(ais2));
    }
    
    @Test
    public void deepCopyTest7() throws CloneNotSupportedException {
        ArrayInformationScheme<InformationComponent> ais = createExample3();
        assertTrue(ais.deepEquals(ais.deepCopy()));
    }
    
    @Test
    public void deepCopyTest8() throws CloneNotSupportedException {
        ArrayInformationScheme<InformationComponent> ais1 = createExample3();
        ArrayScheme<InformationComponent> ais2 = 
            (ArrayScheme<InformationComponent>)ais1.deepCopy();
        ais2.remove(0);
        assertFalse(ais1.deepEquals(ais2.deepCopy()));
    }
    
    @Test
    public void deepCopyTest9() throws CloneNotSupportedException {
        ArrayInformationScheme<InformationComponent> ais1 = createExample3();
        ArrayScheme<InformationComponent> ais2 = 
            (ArrayScheme<InformationComponent>)ais1.deepCopy();
        InformationComponent deleted = ais1.remove(0);
        ais2.remove(deleted);
        assertTrue(ais1.deepEquals(ais2));
    }
    
    @Test
    public void displayTest() {
        ArrayInformationScheme<InformationComponent> ais = createExample1();
        System.out.println("First example:");
        System.out.println(ais.rootsToString());
        ais = createExample2();
        System.out.println("Second example:");
        System.out.println(ais.rootsToString());
        ais = createExample3();
        System.out.println("Third example:");
        System.out.println(ais.rootsToString());
        assertTrue(true);
    }
    
    @Test
    public void writeXMLTest() throws XMLStreamException {//throws IOException {
        System.out.println("XML-Test:");
        ArrayInformationScheme<InformationComponent> ais = createExample3();
        XMLOutputFactory factory = XMLOutputFactory.newInstance(); 
        IntendedXMLStreamWriter sw = 
            new IntendedXMLStreamWriter(factory.createXMLStreamWriter(
                    System.out));
        ais.writeXML(sw);
        sw.close();
        System.out.println();
        assertTrue(true);
    }
    
    @Test
    public void writeReadXMLTest1() throws XMLStreamException {
        ArrayInformationScheme<InformationComponent> ais1 = createExample3();
        StringWriter xmlOut = new StringWriter();
        ais1.save(xmlOut);
        ArrayInformationScheme<InformationComponent> ais2 =
            new ArrayInformationScheme<InformationComponent>(ais1.size());
        StringReader xmlIn = new StringReader(xmlOut.toString());
        ais2.load(xmlIn);
        assertTrue(ais1.equals(ais2));
    }
    
    @Test(expected=SchemeDependencyException.class)
    public void writeReadXMLTest2() throws XMLStreamException {
        ArrayInformationScheme<InformationComponent> ais1 = createExample3();
        InformationComponent dependent = 
            (InformationComponent)ais1.getByString("Authors", true).get(0);
        ais1.remove(dependent);
        ais1.add(dependent);
        StringWriter xmlOut = new StringWriter();
        ais1.save(xmlOut);
        ArrayInformationScheme<InformationComponent> ais2 =
            new ArrayInformationScheme<InformationComponent>(ais1.size());
        StringReader xmlIn = new StringReader(xmlOut.toString());
        ais2.load(xmlIn);
        assertTrue(ais1.equals(ais2));
    }
    
    @Test
    public void writeReadXMLTest3() throws XMLStreamException {
        ArrayInformationScheme<InformationComponent> ais1 = createExample3();
        InformationComponent dependent = 
            (InformationComponent)ais1.getByString("Authors", true).get(0);
        ais1.remove(dependent);
        ais1.add(dependent);
        ais1.halfsortHierarchically();
        StringWriter xmlOut = new StringWriter();
        ais1.save(xmlOut);
        ArrayInformationScheme<InformationComponent> ais2 =
            new ArrayInformationScheme<InformationComponent>(ais1.size());
        StringReader xmlIn = new StringReader(xmlOut.toString());
        ais2.load(xmlIn);
        assertTrue(ais1.equals(ais2));
    }
    
    @Test
    public void writeDOTTest() throws IOException {
        ArrayInformationScheme<InformationComponent> ais = createExample3();
        StringWriter sw = new StringWriter();
        ais.writeDOT(sw);
        System.out.println("DOT-Test:");
        System.out.println(sw.toString());
        System.out.println();
        assertTrue(true);
    }

}
