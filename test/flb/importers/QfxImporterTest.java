package flb.importers;

import static org.junit.jupiter.api.Assertions.*;
import flb.importers.*;
import flb.importers.file.JFileChooserMock;
import flb.importers.ofx.OfxParser;
import org.junit.jupiter.api.*;
import javax.swing.*;
import flb.tuples.*;
import java.util.*;

public class QfxImporterTest {
    private List<Transaction> expected = new ArrayList<>();

    @BeforeEach
    public void setup(){
    }

    @Test
    public void fileDebitChooserTest() {
        JFrame frame = new JFrame();
        JFileChooser fileChooser = new JFileChooserMock(OfxParser.AccountType.CHECKING);
        QfxImporter qfxImporter = new QfxImporter(fileChooser, frame);

        expected = ImportingTransactions.getDebitTransactions();

        assertEquals(expected, qfxImporter.getTransactionsToImport());
    }

    @Test
    public void fileCreditChooserTest() {
        JFrame frame = new JFrame();
        JFileChooser fileChooser = new JFileChooserMock(OfxParser.AccountType.CREDIT);
        QfxImporter qfxImporter = new QfxImporter(fileChooser, frame);

        expected = ImportingTransactions.getCreditTransactions();

        assertEquals(expected, qfxImporter.getTransactionsToImport());
    }
}
