package com.jag2k2.importers;
import java.io.*;
import com.jag2k2.importers.file.FileLoader;
import com.jag2k2.importers.ofx.OfxParser.AccountType;
import com.jag2k2.importers.file.CsvTransactionParser;
import com.jag2k2.tuples.Transaction;
import com.jag2k2.util.Transactions;
import com.jag2k2.util.TransactionsImpl;

public class CsvImporter implements TransactionImporter{
    private final FileLoader fileLoader;

    public CsvImporter(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    @Override
    public Transactions<Transaction> getTransactionsToImport() {
        Transactions<Transaction> transactions = new TransactionsImpl<>();
        for (File file : fileLoader.getFileToImport()){
            String fileString = CsvTransactionParser.convertFileToString(file);
            AccountType accountType = CsvTransactionParser.getAccountType(fileString);
            transactions = CsvTransactionParser.parseTransactions(accountType, fileString);
        }
        return transactions;
    }
}
