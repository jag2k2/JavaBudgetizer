package com.jag2k2.importers.ofx;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.jag2k2.databases.*;
import com.jag2k2.tuples.*;
import com.jag2k2.util.*;
import org.w3c.dom.Document;
import javax.xml.parsers.*;
import java.io.*;

class OfxParserTest {
    private Document debitDocument;
    private Document creditDocument;
    private Transactions<? extends Transaction> expected;

    @BeforeEach
    void setUp() {
        try {
            File debitFile = new File(".\\src\\test\\java\\com\\jag2k2\\importers\\file\\test_debit_xml.qfx");
            File creditFile = new File(".\\src\\test\\java\\com\\jag2k2\\importers\\file\\test_credit_xml.qfx");
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            debitDocument = builder.parse(debitFile);
            creditDocument = builder.parse(creditFile);
        }
        catch (Exception ex) {ex.printStackTrace();}
    }

    @Test
    void parseDebitTransactions() {
        expected = DebitListFactory.makeImportingTransactions();

        Transactions<Transaction> actual = OfxParser.parseTransactions(debitDocument);

        assertEquals(expected, actual);
    }

    @Test
    void parseCreditTransactions() {
        expected = CreditListFactory.makeImportingTransactions();

        Transactions<Transaction> actual = OfxParser.parseTransactions(creditDocument);

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