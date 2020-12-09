package flb.application;

import flb.components.MainGUI;
import flb.databases.*;
import flb.datastores.*;
import javax.swing.*;

public class FilthyLucreBudgetizer {

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        SwingUtilities.invokeLater(new FilthyLucreBudgetizer.InitializeJob());
    }

    static class InitializeJob implements Runnable {
        @Override
        public void run() {
            AbstractDatabase database = new ProductionDatabase();
            database.connect();
            MainGUI mainGui = new MainGUI(database);
            mainGui.launch();
        }
    }
}
