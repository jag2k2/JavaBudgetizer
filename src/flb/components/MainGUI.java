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
import flb.databases.AbstractDatabase;
import flb.datastores.*;
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
        SummaryEditorImpl summaryEditor = new SummaryEditorImpl(dataStoreImpl, dataStoreImpl, dataStoreImpl, monthSelector);
        BankingEditorImpl bankingEditor = new BankingEditorImpl(dataStoreImpl, dataStoreImpl, monthSelector, summaryEditor);
        CreditEditorImpl creditEditor = new CreditEditorImpl(dataStoreImpl, dataStoreImpl, monthSelector, summaryEditor);
        BalanceDisplayImpl balanceDisplay = new BalanceDisplayImpl(dataStoreImpl, dataStoreImpl, dataStoreImpl, monthSelector);
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
