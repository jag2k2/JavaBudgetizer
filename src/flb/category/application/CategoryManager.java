package flb.category.application;

import javax.swing.*;
import flb.category.*;
import flb.database.*;

public class CategoryManager {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new CategoryManagerJob());
    }

    static class CategoryManagerJob implements Runnable {
        @Override
        public void run() {
            AbstractDatabase database = new TestDatabase();
            database.connect();
            CategoryStorage categoryStorage = new CategoryStorage(database);
            MainPanel mainPanel = new MainPanel(categoryStorage);
            mainPanel.layout();
            mainPanel.addListeners();
            MainFrame mainFrame = new MainFrame();
            mainFrame.launch(mainPanel);
        }
    }
}
