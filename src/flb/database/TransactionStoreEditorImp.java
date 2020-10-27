package flb.database;

import flb.tuples.*;
import flb.util.WhichMonth;

import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TransactionStoreEditorImp {
    private final StoreEditor storeEditor;

    public TransactionStoreEditorImp (StoreEditor storeEditor){
        this.storeEditor = storeEditor;
    }

    public ArrayList<BankingTransaction> getBankingTransactions (WhichMonth searchMonth) {
        String query = "SELECT $uniquifier, " +
                "transactions.date, " +
                "transactions.amount, " +
                "transactions.description," +
                "categories.name, " +
                "transactions.reference, " +
                "transactions.balance " +
                "FROM transactions " +
                "LEFT JOIN categories ON transactions.category_id = categories.id WHERE " +
                "transactions.date LIKE '$yrmo-%' AND transactions.type = '$type' " +
                "ORDER BY transactions.date ASC, transactions.id ASC";
        String uniquifier = "transactions.id";
        String dateString = searchMonth.get(Calendar.YEAR) + "-" + (1 + searchMonth.get(Calendar.MONTH));
        String type = "banking";
        query = query.replace("$uniquifier", uniquifier);
        query = query.replace("$yrmo", dateString);
        query = query.replace("$type", type);

        ResultSet results = storeEditor.executeQuery(query);

        return castResultsToBankingTransactions(results);
    }

    private ArrayList<BankingTransaction> castResultsToBankingTransactions(ResultSet results) {
        ArrayList<BankingTransaction> bankingTransactions = new ArrayList<>();
        try {
            while (results.next()) {
                Date sqlDate = results.getDate("transactions.date");
                float amount = results.getFloat("transactions.amount");
                String description = results.getString("transactions.description");
                String categoryName = results.getString("categories.name");
                if (results.wasNull())
                    categoryName = "";
                String reference = results.getString("transactions.reference");
                float balance = results.getFloat("transactions.balance");
                LocalDate localDate = sqlDate.toLocalDate();
                Calendar date = new GregorianCalendar(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
                bankingTransactions.add(new BankingTransaction(reference, date, description, amount, categoryName, balance));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return bankingTransactions;
    }
}
