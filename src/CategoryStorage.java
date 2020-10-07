import java.util.*;
import java.sql.*;

public class CategoryStorage {

    private Connection connection;

    public static void main(String[] args) {
        CategoryStorage categoryStorage = new CategoryStorage();
        System.out.println(categoryStorage.getCategories(""));
        System.out.println(categoryStorage.categoryExist("Clothes"));
    }

    public CategoryStorage() {
        String url = "jdbc:mysql://localhost/gringotts";
        String user = "jag2k2";
        String password = "jeff1229";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Category> getCategories(String nameFilter) {
        String query = "SELECT name, default_goal_amt, exclude\n" +
                "FROM categories\n" +
                "$condition\n" +
                "ORDER BY name";

        String condition = "WHERE name LIKE '$name'";
        if (nameFilter.equals("")) { condition = ""; }
        else { condition = condition.replace("$name", nameFilter); }

        query = query.replace("$condition", condition);

        ArrayList<Category> categories = new ArrayList<>();
        try {
            Statement statementExecutor = connection.createStatement();
            ResultSet results = statementExecutor.executeQuery(query);
            categories = castResultSet(results);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return categories;
    }

    private ArrayList<Category> castResultSet(ResultSet results) {
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
        String query = "SELECT *\n" +
                "FROM categories\n" +
                "WHERE name = '$name'";
        query = query.replace("$name", name);

        ArrayList<Category> categories = new ArrayList<>();
        try {
            Statement statementExecutor = connection.createStatement();
            ResultSet results = statementExecutor.executeQuery(query);
            categories = castResultSet(results);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return !categories.isEmpty();
    }
}