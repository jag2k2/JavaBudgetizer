package flb.application.main;

import flb.application.main.listeners.*;
import flb.database.*;
import javax.swing.*;
import javax.swing.border.*;

import flb.tables.banking.*;
import flb.tables.credit.*;
import flb.tables.goal.*;
import flb.util.WhichMonth;
import org.jdesktop.swingx.*;
import java.awt.*;
import java.util.Calendar;

public class MainGUI {
    private final JFrame frame;
    private final AbstractDatabase database;
    private final JButton prev;
    private enum Months {January, February, March, April, May, June, July, August, September, October, November, December}
    private final JComboBox<Months> month;
    private final JFormattedTextField year;
    private final JTextField balance;
    private final JButton next;
    private final JTable tableForBanking;
    private final JTable tableForCredit;
    private final JTable tableForGoals;
    private final GoalTableImp goalTable;
    private final CreditTableImp creditTable;
    private final BankingEditorImpl bankingTableEditor;
    private final CreditTableEditorImp creditTableEditor;
    private final JPopupMenu categoryMenu;

    public MainGUI(AbstractDatabase database) {
        this.database = database;
        frame = new JFrame();
        BankingTableModelImp bankingModel = new BankingTableModelImp();
        CreditTableModelImp creditModel = new CreditTableModelImp();
        GoalTableModelImp goalModel = new GoalTableModelImp();

        TransactionStoreImp transactionStore = new TransactionStoreImp(database);

        this.tableForBanking = new JTable(bankingModel);
        this.tableForCredit = new JTable(creditModel);
        this.tableForGoals = new JTable(goalModel);

        this.goalTable = new GoalTableImp(tableForGoals, goalModel);
        this.creditTable = new CreditTableImp(tableForCredit, creditModel);

        this.bankingTableEditor = new BankingEditorImpl(transactionStore);
        this.creditTableEditor = new CreditTableEditorImp(transactionStore, creditTable);

        this.prev = new JButton("Prev");
        this.month = new JComboBox<>(Months.values());
        this.year = new JFormattedTextField();
        this.next = new JButton("Next");

        this.balance = new JTextField();
        this.categoryMenu = new JPopupMenu();
    }

    public void layout(){
        year.setPreferredSize(new Dimension(50,25));
        Border blackBorder = new LineBorder(Color.BLACK);
        Border greyBorder = new LineBorder(Color.LIGHT_GRAY);
        Border margin = BorderFactory.createEmptyBorder(5,5,5,5);
        JPanel datePane = new JPanel();
        datePane.setBorder(new CompoundBorder(blackBorder, margin));
        datePane.setLayout(new BoxLayout(datePane, BoxLayout.X_AXIS));
        datePane.add(prev);
        datePane.add(month);
        datePane.add(year);
        datePane.add(next);

        JPanel northLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        northLeftPanel.add(datePane);

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

        JScrollPane bankingScroller = new JScrollPane(tableForBanking);
        bankingScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        bankingScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollPane creditScroller = new JScrollPane(tableForCredit);
        creditScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        creditScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
        tabbedPane.addTab(null, bankingScroller);
        tabbedPane.addTab(null, creditScroller);
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

        JMenuItem item1 = new JMenuItem("Test1");
        JMenu superCat = new JMenu("Test2");
        superCat.add(new JMenuItem("sub1"));
        superCat.add(new JMenuItem("sub2"));
        categoryMenu.add(item1);
        categoryMenu.add(superCat);

        frame.add(categoryMenu);
        frame.setTitle("Filthy Lucre Budgetizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.setJMenuBar(menuBar);
        frame.setLocation(300, 100);
        frame.setMinimumSize(new Dimension(1200, 500));
        frame.setPreferredSize(new Dimension(1200, 500));
        frame.pack();
    }

    public void addListeners() {
        tableForBanking.setFocusable(false);
        tableForBanking.addMouseListener(new UserClicksTableListener(categoryMenu));
    }
    public void launch(){
        database.connect();
        bankingTableEditor.refresh(new WhichMonth(2020, Calendar.OCTOBER));
        creditTableEditor.refreshAndClearSelection(new WhichMonth(2020, Calendar.OCTOBER));
        frame.setVisible(true);
    }
}
