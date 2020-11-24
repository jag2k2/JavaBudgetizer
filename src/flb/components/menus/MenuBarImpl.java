package flb.components.menus;

import flb.components.monthselector.MonthDisplay;
import flb.datastores.GoalStore;
import flb.datastores.TransactionStore;
import flb.importers.file.*;
import flb.importers.QfxImporter;
import flb.listeners.UserCreatesDefaultGoals;
import flb.listeners.UserImportsTransactionsListener;

import javax.swing.*;

public class MenuBarImpl {
    private final JMenuBar menuBar;
    private final TransactionStore transactionStore;
    private final GoalStore goalStore;
    private final MonthDisplay monthDisplay;

    public MenuBarImpl(TransactionStore transactionStore, GoalStore goalStore, MonthDisplay monthDisplay) {
        this.menuBar = new JMenuBar();
        this.goalStore = goalStore;
        this.transactionStore = transactionStore;
        this.monthDisplay = monthDisplay;

        layout();
    }

    protected void layout() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem importMenuItem = new JMenuItem("Import Transactions");
        JFileChooser fileChooser = new JFileChooser();
        FileLoader fileLoader = new UserChoosesFileLoader(fileChooser, menuBar);
        QfxImporter qfxImporter = new QfxImporter(fileLoader);
        importMenuItem.addActionListener(new UserImportsTransactionsListener(transactionStore, qfxImporter));
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(importMenuItem);
        fileMenu.add(exitMenuItem);

        JMenu budgetMenu = new JMenu("Budget");
        JMenuItem defaultGoalsMenuItem = new JMenuItem("Create Default Goals");
        defaultGoalsMenuItem.addActionListener(new UserCreatesDefaultGoals(goalStore, monthDisplay, menuBar));
        budgetMenu.add(defaultGoalsMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(budgetMenu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
