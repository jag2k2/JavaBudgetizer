package com.jag2k2.importers.file;

import java.io.BufferedReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
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
        if (accountType == AccountType.CHECKING) {
            String[] transactionRows = Arrays.copyOfRange(allRows, 8, allRows.length); // Skip header rows and "Beginning balance" row
            for (String transactionRow : transactionRows) {
                List<String> transactionElements = parseCsvLine(transactionRow);

                String stringDate = stripSurroundingQuotes(transactionElements.get(0));
                String[] dateParts = stringDate.split("/");
                String stringYear = dateParts[2];
                String stringMonth = String.format("%02d", Integer.parseInt(dateParts[0]));
                String stringDay = String.format("%02d", Integer.parseInt(dateParts[1]));
                int month = Integer.parseInt(stringMonth) - 1; // Months are 0-based in Calendar
                int day = Integer.parseInt(stringDay);
                int year = Integer.parseInt(stringYear);
                Calendar calendarDate = new GregorianCalendar(year, month, day);

                String description = stripSurroundingQuotes(transactionElements.get(1));
                String stringAmount = stripSurroundingQuotes(transactionElements.get(2)).replace(",", "");
                Float floatAmount = Float.parseFloat(stringAmount);

                String stringBalance = stripSurroundingQuotes(transactionElements.get(3)).replace(",", "");
                Float floatBalance = -1.0f;

                String reference = stringBalance + stringYear.substring(1) + stringMonth + stringDay + stringBalance;

                Transaction newTransaction = new BankingTransaction(reference, calendarDate, description, floatAmount, "", floatBalance);
                transactions.add(newTransaction);
            }
        }
        if (accountType == AccountType.CREDIT) {
            String[] transactionRows = Arrays.copyOfRange(allRows, 1, allRows.length); // Skip header row
            for (String transactionRow : transactionRows) {
                List<String> transactionElements = parseCsvLine(transactionRow);

                String stringDate = stripSurroundingQuotes(transactionElements.get(0));
                String[] dateParts = stringDate.split("/");
                String stringYear = dateParts[2];
                String stringMonth = String.format("%02d", Integer.parseInt(dateParts[0]));
                String stringDay = String.format("%02d", Integer.parseInt(dateParts[1]));
                int month = Integer.parseInt(stringMonth) - 1; // Months are 0-based in Calendar
                int day = Integer.parseInt(stringDay);
                int year = Integer.parseInt(stringYear);
                Calendar calendarDate = new GregorianCalendar(year, month, day);

                String reference = stripSurroundingQuotes(transactionElements.get(1));
                String description = stripSurroundingQuotes(transactionElements.get(2));
                // String address = stripSurroundingQuotes(transactionElements.get(3));
                String stringAmount = stripSurroundingQuotes(transactionElements.get(4)).replace(",", "");
                Float floatAmount = Float.parseFloat(stringAmount);

                Transaction newTransaction = new CreditTransaction(reference, calendarDate, description, floatAmount, "", "");
                transactions.add(newTransaction);
            }
        }
        return transactions;
    }

    /**
     * Parses a CSV line, properly handling commas inside quoted strings
     */
    private static List<String> parseCsvLine(String csvLine) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();
        
        for (int i = 0; i < csvLine.length(); i++) {
            char c = csvLine.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
                currentField.append(c);
            } else if (c == ',' && !inQuotes) {
                result.add(currentField.toString());
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }
        
        // Add the last field
        result.add(currentField.toString());
        return result;
    }

    public static String stripSurroundingQuotes(String input_string) {
        if (input_string != null && input_string.length() >= 2 && input_string.startsWith("\"") && input_string.endsWith("\"")) {
            return input_string.substring(1, input_string.length() - 1);
        }
        return input_string;
    }
}
