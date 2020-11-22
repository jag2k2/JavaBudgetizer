package flb.importers.ofx;

import static org.junit.jupiter.api.Assertions.*;

import flb.importers.ImportingTransactions;
import flb.importers.ofx.OfxParser;
import flb.tuples.*;
import flb.util.*;
import org.junit.jupiter.api.*;
import org.w3c.dom.Document;
import javax.xml.parsers.*;
import java.util.*;
import java.io.*;

class OfxParserTest {
    private Document debitDocument;
    private Document creditDocument;
    private List<Transaction> expected = new ArrayList<>();

    @BeforeEach
    void setUp() {
        try {
            File debitFile = new File(".\\test\\flb\\importers\\file\\test_debit_xml.qfx");
            File creditFile = new File(".\\test\\flb\\importers\\file\\test_credit_xml.qfx");
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            debitDocument = builder.parse(debitFile);
            creditDocument = builder.parse(creditFile);
        }
        catch (Exception ex) {ex.printStackTrace();}
    }

    @Test
    void parseDebitTransactions() {
        expected = ImportingTransactions.getDebitTransactions();

        List<Transaction> actual = OfxParser.parseTransactions(debitDocument);

        assertEquals(expected, actual);
    }

    @Test
    void parseCreditTransactions() {
        expected = ImportingTransactions.getCreditTransactions();

        List<Transaction> actual = OfxParser.parseTransactions(creditDocument);

        assertEquals(expected, actual);
    }

    @Test
    void getAccountType() {
        Maybe<OfxParser.AccountType> expected = new Maybe<>(OfxParser.AccountType.CHECKING);
        assertEquals(expected, OfxParser.getAccountType(debitDocument));

        expected = new Maybe<>(OfxParser.AccountType.CREDIT);
        assertEquals(expected, OfxParser.getAccountType(creditDocument));
    }
}