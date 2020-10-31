package flb.database;

import flb.database.interfaces.DataStore;
import flb.database.interfaces.TransactionStore;
import flb.util.WhichMonth;
import flb.tuples.*;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TransactionStoreImp implements TransactionStore {
    private final DataStore dataStore;

    public TransactionStoreImp(DataStore dataStore){
        this.dataStore = dataStore;
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

        ResultSet results = dataStore.executeQuery(query);

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
                String reference = results.getString("transactions.id");
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

    public ArrayList<CreditTransaction> getCreditTransactions (WhichMonth searchMonth) {
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
        String uniquifier = "transactions.reference";
        String dateString = searchMonth.get(Calendar.YEAR) + "-" + (1 + searchMonth.get(Calendar.MONTH));
        String type = "credit";
        query = query.replace("$uniquifier", uniquifier);
        query = query.replace("$yrmo", dateString);
        query = query.replace("$type", type);

        ResultSet results = dataStore.executeQuery(query);

        return castResultsToCreditTransactions(results);
    }

    private ArrayList<CreditTransaction> castResultsToCreditTransactions(ResultSet results) {
        ArrayList<CreditTransaction> creditTransactions = new ArrayList<>();
        try {
            while (results.next()) {
                Date sqlDate = results.getDate("transactions.date");
                float amount = results.getFloat("transactions.amount");
                String description = results.getString("transactions.description");
                String categoryName = results.getString("categories.name");
                if (results.wasNull())
                    categoryName = "";
                String reference = results.getString("transactions.reference");
                LocalDate localDate = sqlDate.toLocalDate();
                Calendar date = new GregorianCalendar(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
                creditTransactions.add(new CreditTransaction(reference, date, description, amount, categoryName));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return creditTransactions;
    }

    public void categorizeTransaction(Transaction transaction, String categoryName) {
        String update = "UPDATE transactions " +
                "SET category_id = (SELECT categories.id FROM categories WHERE categories.name = '$name') " +
                "WHERE $uniquifier = '$ref'";
        String uniquifier = transaction.getUniquifier();
        String reference = transaction.getReference();
        update = update.replace("$name", categoryName);
        update = update.replace("$uniquifier", uniquifier);
        update = update.replace("$ref", reference);

        dataStore.executeUpdate(update);
    }
}
