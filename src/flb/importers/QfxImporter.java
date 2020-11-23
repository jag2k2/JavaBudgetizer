package flb.importers;

import flb.importers.file.*;
import flb.tuples.*;
import java.io.*;
import java.util.List;
import java.util.*;

public class QfxImporter implements TransactionImporter {
    private final FileLoader fileLoader;

    public QfxImporter(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    public List<Transaction> getTransactionsToImport(){
        List<Transaction> transactions = new ArrayList<>();
        for (File file : fileLoader.getFileToImport()){
            String xmlString = XmlTransactionParser.convertFileToProperXMLString(file);
            transactions = XmlTransactionParser.parseTransactionsFromXml(xmlString);
        }
        return transactions;
    }
}
