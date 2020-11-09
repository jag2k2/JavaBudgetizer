package flb.datastores;

import java.util.*;
import java.sql.*;
import flb.tuples.*;
import flb.util.*;

public class GoalStoreImpl implements GoalStore, GoalStoreTester {
    private final DataStore dataStore;

    public GoalStoreImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void createDefaultGoals(WhichMonth whichMonth) {
        String update = "DELETE FROM goals WHERE year_mo = '$yrmo'";
        update = update.replace("$yrmo", whichMonth.toSQLString());
        dataStore.executeUpdate(update);

        update = "INSERT INTO goals (year_mo, category_id, amount) " +
                "SELECT '$yrmo' AS date, id, default_goal_amt " +
                "FROM categories " +
                "WHERE default_goal_amt IS NOT NULL";
        update = update.replace("$yrmo", whichMonth.toSQLString());
        dataStore.executeUpdate(update);
    }

    @Override
    public int countGoals(WhichMonth selectedMonth) {
        String query = "SELECT COUNT(*) FROM goals WHERE year_mo = '$yrmo'";
        query = query.replace("$yrmo", selectedMonth.toSQLString());

        ResultSet results = dataStore.executeQuery(query);

        int goalCount = 0;
        try {
            results.next();
            goalCount = results.getInt(1);
        } catch (SQLException ex) {ex.printStackTrace();}

        return goalCount;
    }

    @Override
    public boolean goalExists(String name) {
        return false;
    }

    @Override
    public void addGoal(Goal goal) {

    }

    @Override
    public void updateGoalAmount(float amount) {

    }

    @Override
    public void deleteGoal(String name) {

    }

    public ArrayList<Goal> getGoals(WhichMonth whichMonth) {
        String query = "SELECT " +
                "c.name, " +
                "c.default_goal_amt, " +
                "c.exclude, " +
                "g.amount " +
                "FROM goals g  " +
                "LEFT JOIN categories c  " +
                "ON g.category_id = c.id " +
                "WHERE g.year_mo = '$yrmo' " +
                "ORDER BY c.name;";
        query = query.replace("$yrmo", whichMonth.toSQLString());

        ResultSet results = dataStore.executeQuery(query);

        return castResultsToGoals(results, whichMonth);
    }

    protected ArrayList<Goal> castResultsToGoals(ResultSet results, WhichMonth whichMonth) {
        ArrayList<Goal> goals = new ArrayList<>();
        try {
            while (results.next()) {
                String name = results.getString("c.name");
                float default_amount = results.getFloat("c.default_goal_amt");
                if (results.wasNull())
                    default_amount = Float.NaN;
                boolean excluded = results.getBoolean("c.exclude");
                float amount = results.getFloat("g.amount");
                if (results.wasNull())
                    amount = Float.NaN;
                goals.add(new Goal(whichMonth, new Category(name, default_amount, excluded), amount));
            }
        } catch (SQLException ex) {ex.printStackTrace();}
        return goals;
    }
}
