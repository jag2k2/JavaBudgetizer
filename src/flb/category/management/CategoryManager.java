package flb.category.management;

import javax.swing.*;
import flb.category.CategoryStorage;
import flb.database.*;

public class CategoryManager {

    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {e.printStackTrace();}
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                launch();
            }
        });
    }

    static void launch() {
        AbstractDatabase database = new TestDatabase();
        database.connect();
        CategoryStorage categoryStorage = new CategoryStorage(database);
        MainGUI mainGui = new MainGUI(categoryStorage);
        mainGui.build();;
    }
}
