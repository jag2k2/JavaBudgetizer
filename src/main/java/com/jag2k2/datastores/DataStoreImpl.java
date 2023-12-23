package com.jag2k2.datastores;

import com.jag2k2.components.editor.StoreChangeObserver;
import com.jag2k2.databases.SQLExecutor;
import com.jag2k2.util.Maybe;
import com.jag2k2.util.WhichMonth;
import com.jag2k2.util.Transactions;
import com.jag2k2.util.TransactionsImpl;
import com.jag2k2.tuples.*;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DataStoreImpl implements BankingStore, CreditStore, CategoryStore, GoalStore,
        DefaultGoalStoreCreator, BalanceStore, TransactionStoreAdder, GoalStoreTester {
    private final ArrayList<StoreChangeObserver> storeChangeObservers;
    private final SQLExecutor sqlExecutor;

    public DataStoreImpl(SQLExecutor sqlExecutor){
        this.sqlExecutor = sqlExecutor;
        this.storeChangeObservers = new ArrayList<>();
    }

    //region StoreChangeNotifier
        @Override
        public void notifyStoreChange() {
            for (StoreChangeObserver storeChangeObserver : storeChangeObservers) {
                storeChangeObserver.updateAndKeepSelection();
            }
        }

        @Override
        public void addStoreChangeObserver(StoreChangeObserver storeChangeObserver){
            storeChangeObservers.add(storeChangeObserver);
        }
    //endregion

    //region TransactionStore
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
        public void labelGroup(Transactions<? extends Transaction> transactions, String groupName){
            String update = "UPDATE transactions " +
                    "SET pay_group = '$name' " +
                    "WHERE reference IN ($refList)";
            StringBuilder refList = new StringBuilder();
            int count = 0;
            for(Transaction transaction : transactions){
                refList.append("'").append(transaction.getReference()).append("'");
                count++;
                if (count < transactions.size()){
                    refList.append(", ");
                }
            }

            update = update.replace("$name", groupName);
            update = update.replace("$refList", refList.toString());

            sqlExecutor.executeUpdate(update);
            notifyStoreChange();
        }

        @Override
        public void addTransactions(Transactions<? extends Transaction> transactions) {
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

        @Override
        public Transactions<BankingTransaction> getBankingTransactions (WhichMonth whichMonth) {
            String query = getTransactionQueryTemplate(whichMonth);
            query = query.replace("$type", "banking");

            ResultSet results = sqlExecutor.executeQuery(query);

            return castResultsToBankingTransactions(results);
        }

        @Override
        public Transactions<CreditTransaction> getCreditTransactions (WhichMonth whichMonth) {
            String query = getTransactionQueryTemplate(whichMonth);
            query = query.replace("$type", "credit");

            ResultSet results = sqlExecutor.executeQuery(query);

            return castResultsToCreditTransactions(results);
        }

        private String getTransactionQueryTemplate(WhichMonth whichMonth){
            String query = "SELECT " +
                    "transactions.date, " +
                    "transactions.amount, " +
                    "transactions.description," +
                    "categories.name, " +
                    "transactions.reference, " +
                    "transactions.balance, " +
                    "transactions.pay_group " +
                    "FROM transactions " +
                    "LEFT JOIN categories ON transactions.category_id = categories.id WHERE " +
                    "transactions.date LIKE '$yrmo-%' AND transactions.type = '$type' " +
                    "ORDER BY transactions.date ASC, transactions.id ASC";
            query = query.replace("$yrmo", whichMonth.toSQLString());
            return query;
        }

        private Transactions<BankingTransaction> castResultsToBankingTransactions(ResultSet results) {
            Transactions<BankingTransaction> bankingTransactions = new TransactionsImpl<>();
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

        private Transactions<CreditTransaction> castResultsToCreditTransactions(ResultSet results) {
            Transactions<CreditTransaction> creditTransactions = new TransactionsImpl<>();
            try {
                while (results.next()) {
                    Date sqlDate = results.getDate("transactions.date");
                    float amount = results.getFloat("transactions.amount");
                    String description = results.getString("transactions.description");
                    String categoryName = results.getString("categories.name");
                    if (results.wasNull())
                        categoryName = "";
                    String reference = results.getString("transactions.reference");
                    String payGroup = results.getString("transactions.pay_group");
                    if (results.wasNull())
                        payGroup = "";
                    LocalDate localDate = sqlDate.toLocalDate();
                    Calendar date = new GregorianCalendar(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
                    creditTransactions.add(new CreditTransaction(reference, date, description, amount, categoryName, payGroup));
                }
            } catch (SQLException ex) {ex.printStackTrace();}
            return creditTransactions;
        }

        @Override
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
    //endregion

    //region CategoryStore
        @Override
        public void addCategory(String name) {
            String update = "INSERT INTO categories (name, default_goal_amt, exclude) VALUES ('$name', NULL, FALSE)";
            update = update.replace("$name", name);

            sqlExecutor.executeUpdate(update);
            notifyStoreChange();
        }

        @Override
        public int getTransactionCountOfCategory(String categoryNameToDelete) {
            String query = "SELECT COUNT(*) FROM transactions " +
                    "LEFT JOIN categories ON transactions.category_id = categories.id " +
                    "WHERE categories.name = '$name'";
            query = query.replace("$name", categoryNameToDelete);

            ResultSet resultSet = sqlExecutor.executeQuery(query);
            int transactionCount = 0;
            try {
                resultSet.next();
                transactionCount = resultSet.getInt(1);
            } catch (SQLException ex) {ex.printStackTrace();}
            return transactionCount;
        }

        @Override
        public void deleteCategory(String name) {
            String update = "DELETE FROM categories WHERE name = '$name'";
            update = update.replace("$name", name);

            sqlExecutor.executeUpdate(update);
            notifyStoreChange();
        }

        @Override
        public void updateAmount(String name, float amount) {
            String amountString;
            if (Float.isNaN(amount)) amountString =  "NULL";
            else amountString =  String.format("%.2f", amount);

            String update = "UPDATE categories SET default_goal_amt = $def_goal WHERE name = '$name'";
            update = update.replace("$name", name);
            update = update.replace("$def_goal", amountString);

            sqlExecutor.executeUpdate(update);
            notifyStoreChange();
        }

        @Override
        public void toggleExclusion(String name) {
            String update = "UPDATE categories SET exclude = !exclude WHERE name = '$name'";
            update = update.replace("$name", name);
            sqlExecutor.executeUpdate(update);
            notifyStoreChange();
        }

        @Override
        public void renameCategory(String oldName, String newName) {
            String update = "UPDATE categories SET name = '$newName' WHERE name = '$oldName'";
            update = update.replace("$newName", newName);
            update = update.replace("$oldName", oldName);
            sqlExecutor.executeUpdate(update);
            notifyStoreChange();
        }

        @Override
        public boolean categoryExist(String name) {
            String query = "SELECT * FROM categories WHERE name = '$name'";
            query = query.replace("$name", name);

            ResultSet results = sqlExecutor.executeQuery(query);
            ArrayList<Category> categories = castResultsToCategories(results);

            return !categories.isEmpty();
        }

        @Override
        public ArrayList<Category> getCategories(String nameFilter) {
            String query = "SELECT name, default_goal_amt, exclude FROM categories $condition ORDER BY name";
            String condition = "WHERE name LIKE '%$name%'";
            if (nameFilter.equals("")) { condition = ""; }
            else { condition = condition.replace("$name", nameFilter); }
            query = query.replace("$condition", condition);

            ResultSet results = sqlExecutor.executeQuery(query);

            return castResultsToCategories(results);
        }

        private ArrayList<Category> castResultsToCategories(ResultSet results) {
            ArrayList<Category> categories = new ArrayList<>();
            try {
                while (results.next()) {
                    String name = results.getString("name");
                    boolean excluded = results.getBoolean("exclude");
                    Category category = new Category(name, excluded);
                    float defaultGoal = results.getFloat("default_goal_amt");
                    if (!results.wasNull()) {
                        category.setDefaultGoal(defaultGoal);
                    }
                    categories.add(category);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return categories;
        }

        @Override
        public float getBalance(WhichMonth whichMonth){
            //Sum of income - sum(max(budget.goal, sum(trans.amount))
            String query = "SELECT " +
                    "COALESCE(SUM(max_amt),0) " +
                    "FROM(SELECT " +
                    "cat_id, " +
                    "COALESCE(GREATEST(IFNULL(goal_amount, total_tx_amount), IFNULL(total_tx_amount, goal_amount)), 0) as max_amt " +
                    "FROM (SELECT " +
                    "c.id as cat_id, " +
                    "g.amount as goal_amount, " +
                    "$inv*SUM(t.amount) as total_tx_amount, " +
                    "c.exclude as cat_exclude " +
                    "FROM categories c " +
                    "LEFT JOIN goals g " +
                    "ON c.id=g.category_id AND g.year_mo = '$yrmo' " +
                    "LEFT JOIN transactions t " +
                    "ON c.id = t.category_id AND t.date LIKE '$yrmo-%' " +
                    "GROUP by c.id) as all_cat_summaries " +
                    "WHERE $cond AND cat_exclude = 'false') as max_expense_amts";
            query = query.replace("$yrmo", whichMonth.toSQLString());
            String incomeQuery = query.replace("$inv", "1");
            incomeQuery = incomeQuery.replace("$cond", "cat_id = '1' OR cat_id = '2'");

            String expenseQuery = query.replace("$inv", "-1");
            expenseQuery = expenseQuery.replace("$cond", "cat_id != '1' AND cat_id != '2'");

            ResultSet incomeResults = sqlExecutor.executeQuery(incomeQuery);
            ResultSet expenseResults = sqlExecutor.executeQuery(expenseQuery);

            float income = Float.NaN;
            try{
                incomeResults.next();
                income = incomeResults.getFloat(1);
            } catch (SQLException ex) {ex.printStackTrace();}

            float expenses = Float.NaN;

            try{
                expenseResults.next();
                expenses = expenseResults.getFloat(1);
            } catch (SQLException ex) {ex.printStackTrace();}

            return income - expenses;
        }
    //endregion

    //region GoalStore
        @Override
        public void createDefaultGoals(WhichMonth whichMonth) {
            String update = "DELETE FROM goals WHERE year_mo = '$yrmo'";
            update = update.replace("$yrmo", whichMonth.toSQLString());
            sqlExecutor.executeUpdate(update);

            update = "INSERT INTO goals (year_mo, category_id, amount) " +
                    "SELECT '$yrmo' AS date, id, default_goal_amt " +
                    "FROM categories " +
                    "WHERE default_goal_amt IS NOT NULL";
            update = update.replace("$yrmo", whichMonth.toSQLString());
            sqlExecutor.executeUpdate(update);
            notifyStoreChange();
        }

        @Override
        public int countGoals(WhichMonth selectedMonth) {
            String query = "SELECT COUNT(*) FROM goals WHERE year_mo = '$yrmo'";
            query = query.replace("$yrmo", selectedMonth.toSQLString());

            ResultSet results = sqlExecutor.executeQuery(query);

            int goalCount = 0;
            try {
                results.next();
                goalCount = results.getInt(1);
            } catch (SQLException ex) {ex.printStackTrace();}

            return goalCount;
        }

        @Override
        public boolean goalExists(TransactionSummary summary) {
            String query = "SELECT COUNT(*) FROM goals " +
                    "LEFT JOIN categories ON goals.category_id = categories.id " +
                    "WHERE goals.year_mo = '$yrmo' AND categories.name = '$name'";
            query = query.replace("$yrmo", summary.getMonthSQLString());
            query = query.replace("$name", summary.getName());

            ResultSet results = sqlExecutor.executeQuery(query);

            int goalCount = 0;
            try {
                results.next();
                goalCount = results.getInt(1);
            } catch (SQLException ex) {ex.printStackTrace();}
            return (goalCount > 0);
        }

        @Override
        public void addGoal(TransactionSummary summary) {
            for (float goalAmount : summary.getGoalAmount()) {
                String update = "INSERT INTO goals (year_mo, category_id, amount) " +
                        "SELECT '$yrmo' AS date, id, $amt AS goal_amount " +
                        "FROM categories " +
                        "WHERE name = '$name'";
                update = update.replace("$yrmo", summary.getMonthSQLString());
                update = update.replace("$name", summary.getName());
                update = update.replace("$amt", Float.toString(goalAmount));

                sqlExecutor.executeUpdate(update);
                notifyStoreChange();
            }
        }

        @Override
        public void updateGoalAmount(TransactionSummary summary) {
            for (float goalAmount : summary.getGoalAmount()) {
                String update = "UPDATE goals " +
                        "LEFT JOIN categories ON goals.category_id = categories.id " +
                        "SET goals.amount = $amt " +
                        "WHERE goals.year_mo = '$yrmo' AND categories.name = '$name'";
                update = update.replace("$yrmo", summary.getMonthSQLString());
                update = update.replace("$name", summary.getName());
                update = update.replace("$amt", Float.toString(goalAmount));

                sqlExecutor.executeUpdate(update);
                notifyStoreChange();
            }
        }

        @Override
        public void deleteGoal(TransactionSummary summary) {
            String update = "DELETE goals FROM goals " +
                    "LEFT JOIN categories ON goals.category_id = categories.id " +
                    "WHERE goals.year_mo = '$yrmo' AND categories.name = '$name'";
            update = update.replace("$yrmo", summary.getMonthSQLString());
            update = update.replace("$name", summary.getName());

            sqlExecutor.executeUpdate(update);
            notifyStoreChange();
        }
    //endregion

    //region GoalStoreTester
        @Override
        public Maybe<Float> getGoal(WhichMonth whichMonth, String categoryName) {
            String query = "SELECT amount FROM goals " +
                    "LEFT JOIN categories ON goals.category_id = categories.id " +
                    "WHERE goals.year_mo = '$yrmo' AND categories.name = '$name'";
            query = query.replace("$yrmo", whichMonth.toSQLString());
            query = query.replace("$name", categoryName);

            ResultSet results = sqlExecutor.executeQuery(query);

            try{
                if(results.next()){
                    float goalAmount = results.getFloat(1);
                    return new Maybe<>(goalAmount);
                }
            } catch (SQLException ex) {ex.printStackTrace();}

            return new Maybe<>();
        }
    //endregion
}
