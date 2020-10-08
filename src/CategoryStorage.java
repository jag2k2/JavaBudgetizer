import java.util.*;
import java.sql.*;

public class CategoryStorage {

    private final DatabaseConnection databaseConnection;

    public CategoryStorage() {
        databaseConnection = new DatabaseConnection("gringotts");
    }

    public CategoryStorage(String databaseName) {
        databaseConnection = new DatabaseConnection(databaseName);
    }

    public ArrayList<Category> getCategories(String nameFilter) {
        String query = "SELECT name, default_goal_amt, exclude " +
                "FROM categories " +
                "$condition " +
                "ORDER BY name";

        String condition = "WHERE name LIKE '%$name%'";
        if (nameFilter.equals("")) { condition = ""; }
        else { condition = condition.replace("$name", nameFilter); }
        query = query.replace("$condition", condition);

        ResultSet results = databaseConnection.executeQuery(query);

        return castResultsToCategories(results);
    }

    private ArrayList<Category> castResultsToCategories(ResultSet results) {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            while (results.next()) {
                String name = results.getString("name");
                float goal = results.getFloat("default_goal_amt");
                if (results.wasNull())
                    goal = Float.NaN;
                boolean excluded = results.getBoolean("exclude");
                categories.add(new Category(name, goal, excluded));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return categories;
    }

    public Boolean categoryExist(String name) {
        String query = "SELECT * " +
                "FROM categories " +
                "WHERE name = '$name'";
        query = query.replace("$name", name);

        ResultSet results = databaseConnection.executeQuery(query);
        ArrayList<Category> categories = castResultsToCategories(results);

        return !categories.isEmpty();
    }
}