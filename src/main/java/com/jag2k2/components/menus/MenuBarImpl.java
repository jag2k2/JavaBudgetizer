package com.jag2k2.components.menus;

import com.jag2k2.components.monthselector.MonthDisplay;
import com.jag2k2.datastores.*;
import com.jag2k2.importers.file.*;
import com.jag2k2.importers.CsvImporter;
import com.jag2k2.listeners.UserCreatesDefaultGoals;
import com.jag2k2.listeners.UserImportsTransactionsListener;
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
        CsvImporter csvImporter = new CsvImporter(fileLoader);
        importMenuItem.addActionListener(new UserImportsTransactionsListener(storeAdder, csvImporter));
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
