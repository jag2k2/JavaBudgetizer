package com.jag2k2;

import com.jag2k2.components.MainGUI;
import com.jag2k2.databases.*;
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
