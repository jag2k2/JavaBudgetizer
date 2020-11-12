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
    public boolean goalExists(TransactionSummary summary) {
        String query = "SELECT COUNT(*) FROM goals " +
                "LEFT JOIN categories ON goals.category_id = categories.id " +
                "WHERE goals.year_mo = '$yrmo' AND categories.name = '$name'";
        query = query.replace("$yrmo", summary.getMonthSQLString());
        query = query.replace("$name", summary.getName());

        ResultSet results = dataStore.executeQuery(query);

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

            dataStore.executeUpdate(update);
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

            dataStore.executeUpdate(update);
        }
    }

    @Override
    public void deleteGoal(TransactionSummary summary) {
        String update = "DELETE goals FROM goals " +
                "LEFT JOIN categories ON goals.category_id = categories.id " +
                "WHERE goals.year_mo = '$yrmo' AND categories.name = '$name'";
        update = update.replace("$yrmo", summary.getMonthSQLString());
        update = update.replace("$name", summary.getName());

        dataStore.executeUpdate(update);
    }

    @Override
    public Maybe<Float> getGoal(WhichMonth whichMonth, String categoryName) {
        String query = "SELECT amount FROM goals " +
                "LEFT JOIN categories ON goals.category_id = categories.id " +
                "WHERE goals.year_mo = '$yrmo' AND categories.name = '$name'";
        query = query.replace("$yrmo", whichMonth.toSQLString());
        query = query.replace("$name", categoryName);

        ResultSet results = dataStore.executeQuery(query);

        try{
            if(results.next()){
                float goalAmount = results.getFloat(1);
                return new Maybe<>(goalAmount);
            }
        } catch (SQLException ex) {ex.printStackTrace();}

        return new Maybe<>();
    }
}
