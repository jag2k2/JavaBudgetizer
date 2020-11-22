package flb.importers;

import static org.junit.jupiter.api.Assertions.*;
import flb.importers.*;
import flb.importers.file.JFileChooserMock;
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
    public void fileChooserTest() {
        JFrame frame = new JFrame();
        JFileChooser fileChooser = new JFileChooserMock();
        QfxImporter qfxImporter = new QfxImporter(frame, fileChooser);

        expected = ImportingTransactions.getDebitTransactions();

        assertEquals(expected, qfxImporter.getTransactionsToImport());
    }
}
