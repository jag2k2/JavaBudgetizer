package com.jag2k2.importers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.jag2k2.databases.*;
import com.jag2k2.util.Transactions;
import com.jag2k2.importers.file.FileLoader;
import com.jag2k2.importers.file.JFileChooserMock;
import com.jag2k2.importers.file.UserChoosesFileLoader;
import com.jag2k2.importers.ofx.OfxParser;
import com.jag2k2.util.TransactionsImpl;
import javax.swing.*;
import com.jag2k2.tuples.*;

public class QfxImporterTest {
    private Transactions<? extends Transaction> expected = new TransactionsImpl<>();

    @Test
    public void fileDebitChooserTest() {
        JFrame frame = new JFrame();
        JFileChooser fileChooser = new JFileChooserMock(OfxParser.AccountType.CHECKING);
        FileLoader fileLoader = new UserChoosesFileLoader(fileChooser, frame);
        QfxImporter qfxImporter = new QfxImporter(fileLoader);

        expected = DebitListFactory.makeImportingTransactions();

        assertEquals(expected, qfxImporter.getTransactionsToImport());
    }

    @Test
    public void fileCreditChooserTest() {
        JFrame frame = new JFrame();
        JFileChooser fileChooser = new JFileChooserMock(OfxParser.AccountType.CREDIT);
        FileLoader fileLoader = new UserChoosesFileLoader(fileChooser, frame);
        QfxImporter qfxImporter = new QfxImporter(fileLoader);

        expected = CreditListFactory.makeImportingTransactions();

        assertEquals(expected, qfxImporter.getTransactionsToImport());
    }
}
