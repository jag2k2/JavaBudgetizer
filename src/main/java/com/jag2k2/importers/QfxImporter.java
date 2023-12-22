package com.jag2k2.importers;

import com.jag2k2.importers.file.*;
import com.jag2k2.tuples.*;
import com.jag2k2.util.Transactions;
import com.jag2k2.util.TransactionsImpl;
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
