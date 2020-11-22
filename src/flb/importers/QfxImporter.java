package flb.importers;

import flb.importers.file.FileFilterQFX;
import flb.importers.file.SgmlToXmlConverter;
import flb.importers.ofx.OfxParser;
import flb.tuples.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class QfxImporter implements TransactionImporter {
    private final JFileChooser fileChooser;
    private final Component parentComponent;

    public QfxImporter(JFileChooser fileChooser, Component parentComponent) {
        this.parentComponent = parentComponent;
        this.fileChooser = fileChooser;

        String userhome = System.getProperty("user.home");
        fileChooser.setCurrentDirectory(new File(userhome + "\\Downloads"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileFilterQFX());
        fileChooser.setDialogTitle("Import Transactions");
        fileChooser.getActionMap().get("viewTypeDetails").actionPerformed(null);
    }

    public List<Transaction> getTransactionsToImport(){
        List<Transaction> transactions = new ArrayList<>();
        int selection = fileChooser.showOpenDialog(parentComponent);
        if (selection == JFileChooser.APPROVE_OPTION){
            File importFile = fileChooser.getSelectedFile();
            StringBuilder contentsAsXml = new StringBuilder();
            try {
                FileReader fileReader = new FileReader(importFile);
                BufferedReader reader = new BufferedReader(fileReader);

                String line;
                while((line = reader.readLine()) != null) {
                    contentsAsXml.append(SgmlToXmlConverter.parseLine(line));
                }

                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document xmlDocument = builder.parse(new InputSource(new StringReader(contentsAsXml.toString())));
                transactions = OfxParser.parseTransactions(xmlDocument);
            }
            catch (Exception ex) {ex.printStackTrace();}
        }
        return transactions;
    }
}
