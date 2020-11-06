package flb.application.main;

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
            TransactionStore transactionStore = new TransactionStoreImp(database);
            CategoryStore categoryStore = new CategoryStoreImpl(database);
            GoalStore goalStore = new GoalStoreImpl(database);
            MainGUI mainGui = new MainGUI(transactionStore, categoryStore, goalStore);

            database.connect();
            mainGui.launch();
        }
    }
}
