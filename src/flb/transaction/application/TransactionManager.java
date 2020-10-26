package flb.transaction.application;

import flb.database.*;
import javax.swing.*;

public class TransactionManager {

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        SwingUtilities.invokeLater(new TransactionManager.InitializeJob());
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
