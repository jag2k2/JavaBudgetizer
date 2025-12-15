package com.jag2k2.importers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.jag2k2.databases.*;
import com.jag2k2.util.Transactions;
import com.jag2k2.importers.file.FileLoader;
import com.jag2k2.importers.file.CsvFileChooserFake;
import com.jag2k2.importers.file.UserChoosesFileLoader;
import com.jag2k2.importers.ofx.OfxParser;
import com.jag2k2.tuples.*;
import com.jag2k2.util.TransactionsImpl;
import javax.swing.*;


public class CsvImporterTest {
    private Transactions<? extends Transaction> expected = new TransactionsImpl<>();

    @Test
    public void fileDebitChooserTest() {
        JFrame frame = new JFrame();
        OfxParser.AccountType accountType = OfxParser.AccountType.CHECKING;
        JFileChooser fileChooser = new CsvFileChooserFake(accountType);
        FileLoader fileLoader = new UserChoosesFileLoader(fileChooser, frame);
        CsvImporter csvImporter = new CsvImporter(fileLoader);

        expected = DebitListFactory.makeImportingTransactions();

        assertEquals(expected, csvImporter.getTransactionsToImport());
    }

    @Test
    public void fileCreditChooserTest() {
        JFrame frame = new JFrame();
        OfxParser.AccountType accountType = OfxParser.AccountType.CREDIT;
        JFileChooser fileChooser = new CsvFileChooserFake(accountType);
        FileLoader fileLoader = new UserChoosesFileLoader(fileChooser, frame);
        CsvImporter csvImporter = new CsvImporter(fileLoader);

        expected = CreditListFactory.makeImportingTransactions();

        assertEquals(expected, csvImporter.getTransactionsToImport());
    }
}
