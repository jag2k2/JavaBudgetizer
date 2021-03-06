package flb.importers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.databases.*;
import flb.util.Transactions;
import flb.importers.file.FileLoader;
import flb.importers.file.JFileChooserMock;
import flb.importers.file.UserChoosesFileLoader;
import flb.importers.ofx.OfxParser;
import flb.util.TransactionsImpl;
import javax.swing.*;
import flb.tuples.*;

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
