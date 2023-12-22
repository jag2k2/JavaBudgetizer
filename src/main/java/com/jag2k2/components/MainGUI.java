package com.jag2k2.components;

import javax.swing.*;
import javax.swing.border.*;
import com.jag2k2.components.balancedisplay.BalanceDisplayImpl;
import com.jag2k2.components.editor.transaction.banking.BankingEditorImpl;
import com.jag2k2.components.editor.category.CategoryEditorImpl;
import com.jag2k2.components.editor.transaction.credit.CreditEditorImpl;
import com.jag2k2.components.editor.summary.SummaryEditorImpl;
import com.jag2k2.components.menus.MenuBarImpl;
import com.jag2k2.components.monthselector.*;
import com.jag2k2.databases.AbstractDatabase;
import com.jag2k2.datastores.*;
import java.awt.*;

public class MainGUI {
    private final JFrame frame;
    private final MonthSelectorImpl monthSelector;
    private final CategoryEditorImpl categoryEditor;

    public MainGUI(AbstractDatabase database) {
        this.frame = new JFrame();
        this.monthSelector = new MonthSelectorImpl();

        DataStoreImpl dataStoreImpl = new DataStoreImpl(database);
        this.categoryEditor = new CategoryEditorImpl(dataStoreImpl);
        BalanceDisplayImpl balanceDisplay = new BalanceDisplayImpl(dataStoreImpl, monthSelector);
        SummaryEditorImpl summaryEditor = new SummaryEditorImpl(dataStoreImpl, monthSelector);
        BankingEditorImpl bankingEditor = new BankingEditorImpl(dataStoreImpl, monthSelector, summaryEditor);
        CreditEditorImpl creditEditor = new CreditEditorImpl(dataStoreImpl, monthSelector, summaryEditor);
        MenuBarImpl menuBar = new MenuBarImpl(dataStoreImpl, dataStoreImpl, monthSelector);

        JPanel northLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        northLeftPanel.add(monthSelector);

        JPanel northRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        northRightPanel.add(balanceDisplay);
        northRightPanel.add(Box.createRigidArea(new Dimension(34,5)));

        JTabbedPane tabbedCategoryPane = new JTabbedPane();
        tabbedCategoryPane.addTab(" Goals ", summaryEditor);
        tabbedCategoryPane.addTab(" Categories ", categoryEditor);
        tabbedCategoryPane.setBorder(new CompoundBorder(new LineBorder(Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(2,5,5,5)));

        JTabbedPane tabbedTransactionPane = new JTabbedPane();
        tabbedTransactionPane.addTab(" Banking ", bankingEditor);
        tabbedTransactionPane.addTab(" Credit ", creditEditor);
        tabbedTransactionPane.setBorder(new CompoundBorder(new LineBorder(Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(2,5,5,5)));

        JPanel leftPane = new JPanel(new BorderLayout());
        leftPane.add(BorderLayout.NORTH, northLeftPanel);
        leftPane.add(BorderLayout.CENTER, tabbedCategoryPane);

        JPanel rightPane = new JPanel(new BorderLayout());
        rightPane.add(BorderLayout.NORTH, northRightPanel);
        rightPane.add(BorderLayout.CENTER, tabbedTransactionPane);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(BorderLayout.WEST, leftPane);
        mainPanel.add(BorderLayout.CENTER, rightPane);

        frame.setTitle("Filthy Lucre Budgetizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.setJMenuBar(menuBar.getMenuBar());
        frame.setLocation(300, 100);
        frame.setMinimumSize(new Dimension(1200, 500));
        frame.setPreferredSize(new Dimension(1200, 500));
        frame.pack();
    }

    public void launch(){
        monthSelector.setToCurrentMonth();
        categoryEditor.update();
        frame.setVisible(true);
    }
}
