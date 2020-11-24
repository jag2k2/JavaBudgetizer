package flb.components;

import javax.swing.*;
import javax.swing.border.*;
import flb.components.balancedisplay.BalanceDisplayImpl;
import flb.components.editor.transaction.banking.BankingEditorImpl;
import flb.components.editor.category.CategoryEditorImpl;
import flb.components.editor.transaction.credit.CreditEditorImpl;
import flb.components.editor.summary.SummaryEditorImpl;
import flb.components.menus.MenuBarImpl;
import flb.components.monthselector.*;
import flb.datastores.*;

import java.awt.*;

public class MainGUI {
    private final JFrame frame;
    private final MenuBarImpl menuBar;
    private final MonthSelectorImpl monthSelector;
    private final BalanceDisplayImpl balanceDisplay;

    private final BankingEditorImpl bankingEditor;
    private final CreditEditorImpl creditEditor;
    private final CategoryEditorImpl categoryEditor;
    private final SummaryEditorImpl summaryEditor;

    public MainGUI(TransactionStore transactionStore, CategoryStore categoryStore, GoalStore goalStore) {
        this.frame = new JFrame();
        this.monthSelector = new MonthSelectorImpl();
        this.summaryEditor = new SummaryEditorImpl(transactionStore, goalStore, monthSelector);
        this.categoryEditor = new CategoryEditorImpl(categoryStore);
        this.bankingEditor = new BankingEditorImpl(transactionStore, categoryStore, monthSelector, summaryEditor);
        this.creditEditor = new CreditEditorImpl(transactionStore, categoryStore, monthSelector, summaryEditor);
        this.balanceDisplay = new BalanceDisplayImpl(categoryStore, monthSelector);
        this.menuBar = new MenuBarImpl(transactionStore, goalStore, monthSelector);

        transactionStore.addStoreChangeObserver(bankingEditor);
        transactionStore.addStoreChangeObserver(creditEditor);
        transactionStore.addStoreChangeObserver(summaryEditor);
        transactionStore.addStoreChangeObserver(balanceDisplay);

        categoryStore.addStoreChangeObserver(bankingEditor);
        categoryStore.addStoreChangeObserver(creditEditor);
        categoryStore.addStoreChangeObserver(summaryEditor);
        categoryStore.addStoreChangeObserver(balanceDisplay);
        categoryStore.addStoreChangeObserver(categoryEditor);

        goalStore.addStoreChangeObserver(summaryEditor);
        goalStore.addStoreChangeObserver(balanceDisplay);

        monthSelector.addViewChangeObserver(bankingEditor);
        monthSelector.addViewChangeObserver(creditEditor);
        monthSelector.addViewChangeObserver(summaryEditor);
        monthSelector.addViewChangeObserver(balanceDisplay);
        monthSelector.addViewChangeObserver(categoryEditor);

        summaryEditor.addGoalSelectedObserver(bankingEditor);
        summaryEditor.addGoalSelectedObserver(creditEditor);

        layout();
    }

    protected void layout(){
        Border greyBorder = new LineBorder(Color.LIGHT_GRAY);

        JPanel northLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        northLeftPanel.add(monthSelector.getPanel());

        JPanel northRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        northRightPanel.add(balanceDisplay.getPanel());
        northRightPanel.add(Box.createRigidArea(new Dimension(34,5)));

        JTabbedPane tabbedCategoryPane = new JTabbedPane();
        tabbedCategoryPane.addTab(" Goals ", summaryEditor.getPane());
        tabbedCategoryPane.addTab(" Categories ", categoryEditor.getPanel());
        tabbedCategoryPane.setBorder(new CompoundBorder(greyBorder, BorderFactory.createEmptyBorder(2,5,5,5)));

        JTabbedPane tabbedTransactionPane = new JTabbedPane();
        tabbedTransactionPane.addTab(" Banking ", bankingEditor.getPanel());
        tabbedTransactionPane.addTab(" Credit ", creditEditor.getPanel());
        tabbedTransactionPane.setBorder(new CompoundBorder(greyBorder, BorderFactory.createEmptyBorder(2,5,5,5)));

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
        frame.setVisible(true);
    }
}
