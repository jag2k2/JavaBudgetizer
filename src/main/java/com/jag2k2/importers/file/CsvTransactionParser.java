package com.jag2k2.importers.file;

import java.io.BufferedReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import com.jag2k2.importers.ofx.OfxParser.AccountType;
import com.jag2k2.tuples.Transaction;
import com.jag2k2.tuples.BankingTransaction;
import com.jag2k2.tuples.CreditTransaction;
import com.jag2k2.util.Transactions;
import com.jag2k2.util.TransactionsImpl;

public class CsvTransactionParser {

    static public String convertFileToString(File file){
        StringBuilder csvString = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while((line = reader.readLine()) != null) {
                csvString.append(line).append("\n");
            }
            reader.close();
        } catch (Exception ex) {ex.printStackTrace();}
        return csvString.toString();
    }

    static public AccountType getAccountType(String fileString){
        String[] allRows = fileString.split("\\r?\\n");
        String firstRow = allRows[0];
        List<String> firstRowElements = Arrays.asList(firstRow.split(","));
        String cellB1 = firstRowElements.get(1);
        if (cellB1.equals("Reference Number")){
            return AccountType.CREDIT;
        }
        return AccountType.CHECKING;
    }

    static public Transactions<Transaction> parseTransactions(AccountType accountType, String fileString){
        Transactions<Transaction> transactions = new TransactionsImpl<>();
        String[] allRows = fileString.split("\\r?\\n");
        System.out.println(accountType);
        if (accountType == AccountType.CHECKING) {
            String[] transactionRows = Arrays.copyOfRange(allRows, 7, allRows.length); // Skip header rows
            for (String transactionRow : transactionRows) {
                List<String> transactionElements = Arrays.asList(transactionRow.split(","));

                String stringDate = transactionElements.get(0);
                String[] dateParts = stringDate.split("/");
                String stringYear = dateParts[2];
                String stringMonth = String.format("%02d", Integer.parseInt(dateParts[0]));
                String stringDay = String.format("%02d", Integer.parseInt(dateParts[1]));
                int month = Integer.parseInt(stringMonth) - 1; // Months are 0-based in Calendar
                int day = Integer.parseInt(stringDay);
                int year = Integer.parseInt(stringYear);
                Calendar calendarDate = new GregorianCalendar(year, month, day);

                String description = transactionElements.get(1);
                String stringAmount = transactionElements.get(2);
                Float floatAmount = Float.parseFloat(stringAmount);

                String stringBalance = transactionElements.get(3);
                Float floatBalance = -1.0f;

                String reference = stringBalance + stringYear.substring(1) + stringMonth + stringDay + stringBalance;

                Transaction newTransaction = new BankingTransaction(reference, calendarDate, description, floatAmount, "", floatBalance);
                transactions.add(newTransaction);
            }
        }
        if (accountType == AccountType.CREDIT) {
            String[] transactionRows = Arrays.copyOfRange(allRows, 1, allRows.length); // Skip header row
            for (String transactionRow : transactionRows) {
                List<String> transactionElements = Arrays.asList(transactionRow.split(","));

                String stringDate = transactionElements.get(0);
                String[] dateParts = stringDate.split("/");
                String stringYear = dateParts[2];
                String stringMonth = String.format("%02d", Integer.parseInt(dateParts[0]));
                String stringDay = String.format("%02d", Integer.parseInt(dateParts[1]));
                int month = Integer.parseInt(stringMonth) - 1; // Months are 0-based in Calendar
                int day = Integer.parseInt(stringDay);
                int year = Integer.parseInt(stringYear);
                Calendar calendarDate = new GregorianCalendar(year, month, day);

                String reference = transactionElements.get(1);
                String description = transactionElements.get(2);
                // String address = transactionElements.get(3);
                String stringAmount = transactionElements.get(4);
                Float floatAmount = Float.parseFloat(stringAmount);

                Transaction newTransaction = new CreditTransaction(reference, calendarDate, description, floatAmount, "", "");
                transactions.add(newTransaction);
            }
        }
        return transactions;
    }  
}
