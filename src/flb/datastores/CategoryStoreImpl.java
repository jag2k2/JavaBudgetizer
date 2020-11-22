package flb.datastores;

import flb.databases.SQLExecutor;
import flb.tuples.*;
import flb.util.WhichMonth;
import java.util.*;
import java.sql.*;

public class CategoryStoreImpl extends AbstractDataStore implements CategoryStore {

    private final SQLExecutor SQLExecutor;

    public CategoryStoreImpl(SQLExecutor SQLExecutor) {
        this.SQLExecutor = SQLExecutor;
    }

    public void addCategory(String name) {
        String update = "INSERT INTO categories (name, default_goal_amt, exclude) VALUES ('$name', NULL, FALSE)";
        update = update.replace("$name", name);

        SQLExecutor.executeUpdate(update);
        notifyStoreChange();
    }

    public int getTransactionCountOfCategory(String categoryNameToDelete) {
        String query = "SELECT COUNT(*) FROM transactions " +
                "LEFT JOIN categories ON transactions.category_id = categories.id " +
                "WHERE categories.name = '$name'";
        query = query.replace("$name", categoryNameToDelete);

        ResultSet resultSet = SQLExecutor.executeQuery(query);
        int transactionCount = 0;
        try {
            resultSet.next();
            transactionCount = resultSet.getInt(1);
        } catch (SQLException ex) {ex.printStackTrace();}
        return transactionCount;
    }

    public void deleteCategory(String name) {
        String update = "DELETE FROM categories WHERE name = '$name'";
        update = update.replace("$name", name);

        SQLExecutor.executeUpdate(update);
        notifyStoreChange();
    }

    public void updateAmount(String name, float amount) {
        String amountString;
        if (Float.isNaN(amount)) amountString =  "NULL";
        else amountString =  String.format("%.2f", amount);

        String update = "UPDATE categories SET default_goal_amt = $def_goal WHERE name = '$name'";
        update = update.replace("$name", name);
        update = update.replace("$def_goal", amountString);

        SQLExecutor.executeUpdate(update);
        notifyStoreChange();
    }

    public void toggleExclusion(String name) {
        String update = "UPDATE categories SET exclude = !exclude WHERE name = '$name'";
        update = update.replace("$name", name);
        SQLExecutor.executeUpdate(update);
        notifyStoreChange();
    }

    public void renameCategory(String oldName, String newName) {
        String update = "UPDATE categories SET name = '$newName' WHERE name = '$oldName'";
        update = update.replace("$newName", newName);
        update = update.replace("$oldName", oldName);
        SQLExecutor.executeUpdate(update);
        notifyStoreChange();
    }

    public boolean categoryExist(String name) {
        String query = "SELECT * FROM categories WHERE name = '$name'";
        query = query.replace("$name", name);

        ResultSet results = SQLExecutor.executeQuery(query);
        ArrayList<Category> categories = castResultsToCategories(results);

        return !categories.isEmpty();
    }

    public ArrayList<Category> getCategories(String nameFilter) {
        String query = "SELECT name, default_goal_amt, exclude FROM categories $condition ORDER BY name";
        String condition = "WHERE name LIKE '%$name%'";
        if (nameFilter.equals("")) { condition = ""; }
        else { condition = condition.replace("$name", nameFilter); }
        query = query.replace("$condition", condition);

        ResultSet results = SQLExecutor.executeQuery(query);

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

        ResultSet incomeResults = SQLExecutor.executeQuery(incomeQuery);
        ResultSet expenseResults = SQLExecutor.executeQuery(expenseQuery);

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
}