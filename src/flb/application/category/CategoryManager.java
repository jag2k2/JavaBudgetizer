package flb.application.category;

import javax.swing.*;
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

        SwingUtilities.invokeLater(new InitializeJob());
    }

    static class InitializeJob implements Runnable {
        @Override
        public void run() {
            AbstractDatabase database = new TestDatabase();
            MainGUI mainGui = new MainGUI(database);
            mainGui.layout();
            mainGui.addListeners();
            mainGui.launch();
        }
    }
}
