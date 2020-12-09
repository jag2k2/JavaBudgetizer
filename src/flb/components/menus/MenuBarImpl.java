package flb.components.menus;

import flb.components.monthselector.MonthDisplay;
import flb.datastores.*;
import flb.importers.file.*;
import flb.importers.QfxImporter;
import flb.listeners.UserCreatesDefaultGoals;
import flb.listeners.UserImportsTransactionsListener;
import javax.swing.*;

public class MenuBarImpl {
    private final JMenuBar menuBar;
    private final TransactionStoreAdder storeAdder;
    private final DefaultGoalStoreCreator goalStoreCreator;
    private final MonthDisplay monthDisplay;

    public MenuBarImpl(TransactionStoreAdder storeAdder, DefaultGoalStoreCreator goalStoreCreator, MonthDisplay monthDisplay) {
        this.menuBar = new JMenuBar();
        this.goalStoreCreator = goalStoreCreator;
        this.storeAdder = storeAdder;
        this.monthDisplay = monthDisplay;

        layout();
    }

    protected void layout() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem importMenuItem = new JMenuItem("Import Transactions");
        JFileChooser fileChooser = new JFileChooser();
        FileLoader fileLoader = new UserChoosesFileLoader(fileChooser, menuBar);
        QfxImporter qfxImporter = new QfxImporter(fileLoader);
        importMenuItem.addActionListener(new UserImportsTransactionsListener(storeAdder, qfxImporter));
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(importMenuItem);
        fileMenu.add(exitMenuItem);

        JMenu budgetMenu = new JMenu("Budget");
        JMenuItem defaultGoalsMenuItem = new JMenuItem("Create Default Goals");
        defaultGoalsMenuItem.addActionListener(new UserCreatesDefaultGoals(goalStoreCreator, monthDisplay, menuBar));
        budgetMenu.add(defaultGoalsMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(budgetMenu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
