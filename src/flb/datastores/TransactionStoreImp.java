package flb.datastores;

import flb.databases.SQLExecutor;
import flb.util.WhichMonth;
import flb.tuples.*;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TransactionStoreImp extends AbstractDataStore implements TransactionStore {
    private final SQLExecutor sqlExecutor;

    public TransactionStoreImp(SQLExecutor sqlExecutor){
        this.sqlExecutor = sqlExecutor;
    }

    @Override
    public void categorizeTransaction(Transaction transaction, String categoryName) {
        String update = "UPDATE transactions " +
                "SET category_id = (SELECT categories.id FROM categories WHERE categories.name = '$name') " +
                "WHERE reference = '$ref'";
        String reference = transaction.getReference();
        update = update.replace("$name", categoryName);
        update = update.replace("$ref", reference);

        sqlExecutor.executeUpdate(update);
        notifyStoreChange();
    }

    @Override
    public void addTransactions(List<Transaction> transactions) {
        if(transactions.size() > 0){
            String update = "CREATE TEMPORARY TABLE transactions_temp (" +
                    "date TIMESTAMP, " +
                    "type VARCHAR(255), " +
                    "description VARCHAR(255), " +
                    "amount FLOAT(9,2), " +
                    "category_id INT(11), " +
                    "balance FLOAT(9,2), " +
                    "reference VARCHAR(255))";
            sqlExecutor.executeUpdate(update);

            StringBuilder multiValues = new StringBuilder();
            int count = 0;
            for (Transaction transaction : transactions){
                String values = "('$date', '$type', '$desc', '$amt', '$cat_id', '$bal', '$ref')";
                values = values.replace("$date", transaction.getDateString());
                values = values.replace("$type", transaction.getTypeString());
                values = values.replace("$desc", transaction.getDescriptionWithEscapes());
                values = values.replace("$amt", Float.toString(transaction.getAmount()));
                values = values.replace("$cat_id", "-1");
                values = values.replace("$bal", Float.toString(transaction.getBalance()));
                values = values.replace("$ref", transaction.getReference());
                multiValues.append(values);

                count++;
                if (count < transactions.size())
                    multiValues.append(", ");
            }

            update = "INSERT INTO transactions_temp(date, type, description, amount, category_id, balance, reference) " +
                    "VALUES $multiValues";
            update = update.replace("$multiValues", multiValues);
            sqlExecutor.executeUpdate(update);

            update = "INSERT INTO transactions(date, type, description, amount, category_id, balance, reference) " +
                    "SELECT temp.date, temp.type, temp.description, temp.amount, temp.category_id, temp.balance, temp.reference " +
                    "FROM transactions_temp temp " +
                    "WHERE NOT EXISTS (" +
                    "SELECT 1 FROM transactions trans " +
                    "WHERE trans.reference = temp.reference)";
            sqlExecutor.executeUpdate(update);

            update = "DROP TABLE transactions_temp";
            sqlExecutor.executeUpdate(update);
            notifyStoreChange();
        }
    }

    public ArrayList<Transaction> getBankingTransactions (WhichMonth whichMonth) {
        String query = getTransactionQueryTemplate(whichMonth);
        query = query.replace("$uniquifier", "transactions.id");
        query = query.replace("$type", "banking");

        ResultSet results = sqlExecutor.executeQuery(query);

        return castResultsToBankingTransactions(results);
    }

    public ArrayList<Transaction> getCreditTransactions (WhichMonth whichMonth) {
        String query = getTransactionQueryTemplate(whichMonth);
        query = query.replace("$uniquifier", "transactions.reference");
        query = query.replace("$type", "credit");

        ResultSet results = sqlExecutor.executeQuery(query);

        return castResultsToCreditTransactions(results);
    }

    private String getTransactionQueryTemplate(WhichMonth whichMonth){
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
        query = query.replace("$yrmo", whichMonth.toSQLString());
        return query;
    }

    private ArrayList<Transaction> castResultsToBankingTransactions(ResultSet results) {
        ArrayList<Transaction> bankingTransactions = new ArrayList<>();
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
        } catch (SQLException ex) {ex.printStackTrace();}
        return bankingTransactions;
    }

    private ArrayList<Transaction> castResultsToCreditTransactions(ResultSet results) {
        ArrayList<Transaction> creditTransactions = new ArrayList<>();
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
        } catch (SQLException ex) {ex.printStackTrace();}
        return creditTransactions;
    }

    public ArrayList<TransactionSummary> getTransactionSummaries(WhichMonth whichMonth) {
        String query = "SELECT " +
                "c.name as category_name, " +
                "g.amount as goal_amount, " +
                "SUM(t.amount) as total_txn_amount, " +
                "c.exclude as excluded " +
                "FROM categories c " +
                "LEFT JOIN goals g " +
                "ON c.id=g.category_id AND g.year_mo = '$yrmo' " +
                "LEFT JOIN transactions t " +
                "ON c.id = t.category_id AND t.date LIKE '$yrmo-%' " +
                "GROUP by c.id " +
                "HAVING COALESCE (goal_amount, total_txn_amount) IS NOT NULL " +
                "ORDER BY c.name";

        query = query.replace("$yrmo", whichMonth.toSQLString());

        ResultSet results = sqlExecutor.executeQuery(query);

        return castResultsToSummaries(results, whichMonth);
    }

    private ArrayList<TransactionSummary> castResultsToSummaries (ResultSet results, WhichMonth whichMonth) {
        ArrayList<TransactionSummary> summaries = new ArrayList<>();
        try {
            while (results.next()) {
                String name = results.getString("category_name");
                boolean excluded = results.getBoolean("excluded");
                Category category = new Category(name, excluded);
                TransactionSummary summary = new TransactionSummary(whichMonth, category);
                float sum = results.getFloat("total_txn_amount");
                if (!results.wasNull())
                    summary.addSum(sum);
                float goal = results.getFloat("goal_amount");
                if (!results.wasNull())
                    summary.addGoal(goal);
                summaries.add(summary);
            }
        } catch (SQLException ex) {ex.printStackTrace();}

        return summaries;
    }

    public int getAffectedRows(){
        String query = "SELECT ROW_COUNT()";
        ResultSet results = sqlExecutor.executeQuery((query));
        int affectedRows = -1;
        try {
            results.next();
            affectedRows = results.getInt(1);
        } catch (Exception ex) {ex.printStackTrace();}
        return affectedRows;
    }
}
