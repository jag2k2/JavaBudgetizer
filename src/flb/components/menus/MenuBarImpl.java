package flb.components.menus;

import flb.components.editors.DefaultGoalMaker;
import flb.components.monthselector.SelectedMonthGetter;
import flb.datastores.GoalStore;
import flb.datastores.TransactionStore;
import flb.importers.QfxImporter;
import flb.listeners.UserCreatesDefaultGoals;
import flb.listeners.UserImportsTransactionsListener;

import javax.swing.*;

public class MenuBarImpl {
    private final JMenuBar menuBar;
    private final TransactionStore transactionStore;
    private final DefaultGoalMaker goalMaker;

    public MenuBarImpl(TransactionStore transactionStore, DefaultGoalMaker goalMaker) {
        this.menuBar = new JMenuBar();
        this.goalMaker = goalMaker;
        this.transactionStore = transactionStore;

        layout();
    }

    protected void layout() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem importMenuItem = new JMenuItem("Import Transactions");
        JFileChooser fileChooser = new JFileChooser();
        QfxImporter qfxImporter = new QfxImporter(menuBar, fileChooser);
        //importMenuItem.addActionListener(new UserImportsTransactionsListener(transactionStore, qfxImporter));
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(importMenuItem);
        fileMenu.add(exitMenuItem);

        JMenu budgetMenu = new JMenu("Budget");
        JMenuItem defaultGoalsMenuItem = new JMenuItem("Create Default Goals");
        defaultGoalsMenuItem.addActionListener(new UserCreatesDefaultGoals(goalMaker));
        budgetMenu.add(defaultGoalsMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(budgetMenu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
