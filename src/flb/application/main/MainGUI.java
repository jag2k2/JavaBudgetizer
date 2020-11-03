package flb.application.main;

import javax.swing.*;
import javax.swing.border.*;
import flb.database.interfaces.*;
import flb.tables.banking.*;
import flb.tables.credit.*;
import flb.tables.goal.*;
import flb.components.*;
import org.jdesktop.swingx.*;
import java.awt.*;

public class MainGUI {
    private final JFrame frame;
    private final MonthSelectorImpl monthSelector;
    private final JTextField balance;
    private final JTable tableForGoals;
    private final GoalTableImp goalTable;
    private final BankingEditorImpl bankingEditor;
    private final CreditEditorImpl creditEditor;
    private final CategoryMenuImpl categoryMenuImpl;

    public MainGUI(TransactionStore transactionStore, CategoryStore categoryStore) {
        this.frame = new JFrame();
        this.monthSelector = new MonthSelectorImpl();
        this.bankingEditor = new BankingEditorImpl(transactionStore);
        this.creditEditor = new CreditEditorImpl(transactionStore);
        GoalTableModelImp goalModel = new GoalTableModelImp();
        this.tableForGoals = new JTable(goalModel);
        this.goalTable = new GoalTableImp(tableForGoals, goalModel);
        this.balance = new JTextField();
        this.categoryMenuImpl = new CategoryMenuImpl(categoryStore, bankingEditor, creditEditor, monthSelector);

        addListeners();
        layout();
    }

    protected void layout(){
        Border blackBorder = new LineBorder(Color.BLACK);
        Border greyBorder = new LineBorder(Color.LIGHT_GRAY);
        Border margin = BorderFactory.createEmptyBorder(5,5,5,5);

        JPanel northLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        northLeftPanel.add(monthSelector.getPane());

        JLabel balanceLabel = new JLabel("Balance");
        JPanel balancePane = new JPanel();
        balancePane.setBorder(new CompoundBorder(blackBorder, margin));
        balancePane.setLayout(new BoxLayout(balancePane, BoxLayout.X_AXIS));
        balancePane.add(balanceLabel);
        balance.setPreferredSize(new Dimension(100,28));
        balancePane.add(balance);

        JPanel northRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        northRightPanel.add(balancePane);
        northRightPanel.add(Box.createRigidArea(new Dimension(34,5)));

        tableForGoals.setPreferredScrollableViewportSize(new Dimension(395,-1));
        JScrollPane goalScroller = new JScrollPane(tableForGoals);
        goalScroller.getViewport().setViewSize(new Dimension(10,10));
        goalScroller.setMaximumSize(new Dimension(10,10));
        goalScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        goalScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        goalScroller.setBorder(new CompoundBorder(greyBorder, margin));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
        tabbedPane.addTab(null, bankingEditor.getPane());
        tabbedPane.addTab(null, creditEditor.getPane());
        JXLabel bankingLabel = new JXLabel(" Banking ");
        bankingLabel.setTextRotation(Math.PI/2);
        tabbedPane.setTabComponentAt(0, bankingLabel);
        JXLabel creditLabel = new JXLabel(" Credit ");
        creditLabel.setTextRotation(Math.PI/2);
        tabbedPane.setTabComponentAt(1, creditLabel);

        tabbedPane.setBorder(new CompoundBorder(greyBorder, BorderFactory.createEmptyBorder(2,5,5,5)));

        JPanel leftPane = new JPanel(new BorderLayout());
        leftPane.add(BorderLayout.NORTH, northLeftPanel);
        leftPane.add(BorderLayout.CENTER, goalScroller);

        JPanel rightPane = new JPanel(new BorderLayout());
        rightPane.add(BorderLayout.NORTH, northRightPanel);
        rightPane.add(BorderLayout.CENTER, tabbedPane);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(BorderLayout.WEST, leftPane);
        mainPanel.add(BorderLayout.CENTER, rightPane);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem importMenuItem = new JMenuItem("Import Transactions");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(importMenuItem);
        fileMenu.add(exitMenuItem);

        JMenu budgetMenu = new JMenu("Budget");
        JMenuItem defaultGoalsMenuItem = new JMenuItem("Create Default Goals");
        JMenuItem manageCategoriesMenuItem = new JMenuItem("Manage Categories");
        budgetMenu.add(defaultGoalsMenuItem);
        budgetMenu.add(manageCategoriesMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(budgetMenu);

        frame.add(categoryMenuImpl.getPopup());
        frame.setTitle("Filthy Lucre Budgetizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.setJMenuBar(menuBar);
        frame.setLocation(300, 100);
        frame.setMinimumSize(new Dimension(1200, 500));
        frame.setPreferredSize(new Dimension(1200, 500));
        frame.pack();
    }

    protected void addListeners() {
        bankingEditor.addCategorizingListener(categoryMenuImpl);
        creditEditor.addCategorizingListener(categoryMenuImpl);

        monthSelector.addMonthChangeListener(bankingEditor);
        monthSelector.addMonthChangeListener(creditEditor);
    }
    public void launch(){
        monthSelector.setToCurrentMonth();
        frame.setVisible(true);
    }
}
