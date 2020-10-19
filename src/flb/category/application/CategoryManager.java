package flb.category.application;

import javax.swing.*;
import flb.category.CategoryStorage;
import flb.database.*;

public class CategoryManager {

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        SwingUtilities.invokeLater(new CategoryManagerJob());
    }

    static class CategoryManagerJob implements Runnable {
        @Override
        public void run() {
            AbstractDatabase database = new TestDatabase();
            database.connect();
            CategoryStorage categoryStorage = new CategoryStorage(database);
            MainGUI mainGui = new MainGUI(categoryStorage);
            mainGui.layout();
            mainGui.addListeners();
            mainGui.launch();
        }
    }
}
