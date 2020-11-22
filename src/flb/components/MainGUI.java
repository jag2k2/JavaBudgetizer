package flb.components;

import javax.swing.*;
import javax.swing.border.*;
import flb.components.balancedisplay.BalanceDisplayImpl;
import flb.components.menus.MenuBarImpl;
import flb.listeners.*;
import flb.components.monthselector.*;
import flb.datastores.*;
import flb.components.editors.*;
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

    private final JTextField nameFilter;
    private final JButton addButton;

    public MainGUI(TransactionStore transactionStore, CategoryStore categoryStore, GoalStore goalStore) {
        this.frame = new JFrame();
        this.monthSelector = new MonthSelectorImpl();
        this.summaryEditor = new SummaryEditorImpl(transactionStore, goalStore, monthSelector, frame);
        this.categoryEditor = new CategoryEditorImpl(categoryStore);
        this.bankingEditor = new BankingEditorImpl(transactionStore, categoryStore, monthSelector, summaryEditor);
        this.creditEditor = new CreditEditorImpl(transactionStore, categoryStore, monthSelector, summaryEditor);
        this.balanceDisplay = new BalanceDisplayImpl(categoryStore, monthSelector);
        this.addButton = new JButton("Add");
        this.nameFilter = new JTextField();
        this.menuBar = new MenuBarImpl(transactionStore, goalStore, monthSelector);

        transactionStore.addStoreChangeObserver(bankingEditor);
        transactionStore.addStoreChangeObserver(creditEditor);
        transactionStore.addStoreChangeObserver(summaryEditor);
        transactionStore.addStoreChangeObserver(balanceDisplay);

        categoryStore.addStoreChangeObserver(bankingEditor);
        categoryStore.addStoreChangeObserver(creditEditor);
        categoryStore.addStoreChangeObserver(summaryEditor);
        categoryStore.addStoreChangeObserver(balanceDisplay);

        goalStore.addStoreChangeObserver(summaryEditor);
        goalStore.addStoreChangeObserver(balanceDisplay);

        addListeners();
        layout();
    }

    protected void layout(){
        Border greyBorder = new LineBorder(Color.LIGHT_GRAY);

        JPanel northLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        northLeftPanel.add(monthSelector.getPanel());

        JPanel northRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        northRightPanel.add(balanceDisplay.getPanel());
        northRightPanel.add(Box.createRigidArea(new Dimension(34,5)));

        JPanel northCategoryPanel = new JPanel();
        northCategoryPanel.setLayout(new BoxLayout(northCategoryPanel, BoxLayout.X_AXIS));
        northCategoryPanel.add(nameFilter);
        northCategoryPanel.add(addButton);

        JPanel mainCategoryPanel = new JPanel(new BorderLayout());
        mainCategoryPanel.add(BorderLayout.NORTH, northCategoryPanel);
        mainCategoryPanel.add(BorderLayout.CENTER, categoryEditor.getPane());

        JTabbedPane tabbedCategoryPane = new JTabbedPane();
        tabbedCategoryPane.addTab(" Goals ", summaryEditor.getPane());
        tabbedCategoryPane.addTab(" Categories ", mainCategoryPanel);
        tabbedCategoryPane.setBorder(new CompoundBorder(greyBorder, BorderFactory.createEmptyBorder(2,5,5,5)));

        JTabbedPane tabbedTransactionPane = new JTabbedPane();
        tabbedTransactionPane.addTab(" Banking ", bankingEditor.getPane());
        tabbedTransactionPane.addTab(" Credit ", creditEditor.getPane());
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

    protected void addListeners() {
        monthSelector.addMonthChangeObserver(bankingEditor);
        monthSelector.addMonthChangeObserver(creditEditor);
        monthSelector.addMonthChangeObserver(summaryEditor);
        monthSelector.addMonthChangeObserver(balanceDisplay);

        summaryEditor.addGoalSelectedListener(bankingEditor);
        summaryEditor.addGoalSelectedListener(creditEditor);

        categoryEditor.addCategoryEditingListeners(nameFilter, frame);
        addButton.addActionListener(new UserAddsCategoryListener(categoryEditor, nameFilter));
        nameFilter.getDocument().addDocumentListener(new UserFiltersCategoriesListener(categoryEditor, nameFilter));
    }

    public void launch(){
        categoryEditor.refreshAndClearSelection("");
        monthSelector.setToCurrentMonth();
        frame.setVisible(true);
    }
}
