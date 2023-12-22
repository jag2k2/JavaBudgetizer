package com.jag2k2.importers.file;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;

public class SgmlToXmlConverterTest {

    @Test
    public void detectMarkupTest(){
        String testLine = "[Hello]";
        assertFalse(SgmlToXmlConverter.detectMarkup(testLine));

        testLine = "<Hello>";
        assertTrue(SgmlToXmlConverter.detectMarkup(testLine));

        testLine = "    <Hello>";
        assertTrue(SgmlToXmlConverter.detectMarkup(testLine));

        testLine = "</Hello>";
        assertTrue(SgmlToXmlConverter.detectMarkup(testLine));

        testLine = "    </Hello>";
        assertTrue(SgmlToXmlConverter.detectMarkup(testLine));

        testLine = "<Hello>World";
        assertTrue(SgmlToXmlConverter.detectMarkup(testLine));

        testLine = "H<ello>";
        assertFalse(SgmlToXmlConverter.detectMarkup(testLine));
    }

    @Test
    public void selfClosingTagDetector(){
        String testLine = "<Hello>World";
        assertTrue(SgmlToXmlConverter.detectSelfClosingTag(testLine));

        testLine = "     <Hello>World";
        assertTrue(SgmlToXmlConverter.detectSelfClosingTag(testLine));

        testLine = "<Hello>";
        assertFalse(SgmlToXmlConverter.detectSelfClosingTag(testLine));

        testLine = "</Hello>";
        assertFalse(SgmlToXmlConverter.detectSelfClosingTag(testLine));

        testLine = "Hello<World>";
        assertFalse(SgmlToXmlConverter.detectSelfClosingTag(testLine));
    }

    @Test
    public void openTagExtractor(){
        String testLine = "<Hello>";
        assertEquals("Hello", SgmlToXmlConverter.openTagNameExtractor(testLine));

        testLine = "     <Hello>";
        assertEquals("Hello", SgmlToXmlConverter.openTagNameExtractor(testLine));

        testLine = "<Hello>World";
        assertEquals("Hello", SgmlToXmlConverter.openTagNameExtractor(testLine));
    }

    @Test
    public void encloseSelfClosingTagTest(){
        String testLine = "<Hello>World";
        assertEquals("<Hello>World</Hello>", SgmlToXmlConverter.encloseSelfClosingTag(testLine));

        testLine = "<Hello>";
        assertEquals("<Hello>", SgmlToXmlConverter.encloseSelfClosingTag(testLine));

        testLine = "</Hello>";
        assertEquals("</Hello>", SgmlToXmlConverter.encloseSelfClosingTag(testLine));
    }

    @Test
    public void parseLineTest(){
        String testLine = "[Hello]";
        assertEquals("", SgmlToXmlConverter.parseLine(testLine));

        testLine = "<Hello>";
        assertEquals("<Hello>\n", SgmlToXmlConverter.parseLine(testLine));

        testLine = "<Hello>World";
        assertEquals("<Hello>World</Hello>\n", SgmlToXmlConverter.parseLine(testLine));

        testLine = "</Hello>";
        assertEquals("</Hello>\n", SgmlToXmlConverter.parseLine(testLine));
    }

    @Test
    public void sandbox(){
        File debitFile = new File(".\\test\\flb\\importers\\file\\test_debit_sgml.qfx");
        StringBuilder contentsAsXML = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(debitFile);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while((line = reader.readLine()) != null) {
                contentsAsXML.append(SgmlToXmlConverter.parseLine(line));
            }
            reader.close();
        } catch (Exception ex) {ex.printStackTrace();}
        System.out.println(contentsAsXML);
    }
}
