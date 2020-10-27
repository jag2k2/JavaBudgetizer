package flb.database;

import flb.tuples.Category;

import java.util.*;
import java.sql.*;

public class CategoryStoreEditorImp implements CategoryStoreEditor {

    private final StoreEditor storeEditor;

    public CategoryStoreEditorImp(StoreEditor storeEditor) {
        this.storeEditor = storeEditor;
    }

    public void addCategory(String name) {
        String update = "INSERT INTO categories (name, default_goal_amt, exclude) VALUES ('$name', NULL, FALSE)";
        update = update.replace("$name", name);

        storeEditor.executeUpdate(update);
    }

    public void deleteCategory(String name) {
        String update = "DELETE FROM categories WHERE name = '$name'";
        update = update.replace("$name", name);

        storeEditor.executeUpdate(update);
    }

    public void updateAmount(String name, float amount) {
        String amountString;
        if (Float.isNaN(amount)) amountString =  "NULL";
        else amountString =  String.format("%.2f", amount);

        String update = "UPDATE categories SET default_goal_amt = $def_goal WHERE name = '$name'";
        update = update.replace("$name", name);
        update = update.replace("$def_goal", amountString);

        storeEditor.executeUpdate(update);
    }

    public void toggleExclusion(String name) {
        String update = "UPDATE categories SET exclude = !exclude WHERE name = '$name'";
        update = update.replace("$name", name);
        storeEditor.executeUpdate(update);
    }

    public void renameCategory(String oldName, String newName) {
        String update = "UPDATE categories SET name = '$newName' WHERE name = '$oldName'";
        update = update.replace("$newName", newName);
        update = update.replace("$oldName", oldName);
        storeEditor.executeUpdate(update);
    }

    public ArrayList<Category> getCategories(String nameFilter) {
        String query = "SELECT name, default_goal_amt, exclude FROM categories $condition ORDER BY name";
        String condition = "WHERE name LIKE '%$name%'";
        if (nameFilter.equals("")) { condition = ""; }
        else { condition = condition.replace("$name", nameFilter); }
        query = query.replace("$condition", condition);

        ResultSet results = storeEditor.executeQuery(query);

        return castResultsToCategories(results);
    }

    public Boolean categoryExist(String name) {
        String query = "SELECT * FROM categories WHERE name = '$name'";
        query = query.replace("$name", name);

        ResultSet results = storeEditor.executeQuery(query);
        ArrayList<Category> categories = castResultsToCategories(results);

        return !categories.isEmpty();
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
}