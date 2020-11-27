package flb.importers;

import flb.importers.file.*;
import flb.tuples.*;
import flb.util.Transactions;
import flb.util.TransactionsImpl;
import java.io.*;

public class QfxImporter implements TransactionImporter {
    private final FileLoader fileLoader;

    public QfxImporter(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    public Transactions<Transaction> getTransactionsToImport(){
        Transactions<Transaction> transactions = new TransactionsImpl<>();
        for (File file : fileLoader.getFileToImport()){
            String xmlString = XmlTransactionParser.convertFileToProperXMLString(file);
            transactions = XmlTransactionParser.parseTransactionsFromXml(xmlString);
        }
        return transactions;
    }
}
