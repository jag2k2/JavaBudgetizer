package flb.components.editor.transaction.banking;

import flb.components.editor.SimpleDollarRenderer;
import flb.components.editor.summary.SummarySelector;
import flb.components.editor.transaction.*;
import flb.components.menus.*;
import flb.listeners.*;
import flb.tuples.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import flb.util.*;
import javax.swing.*;

public class BankingTableImpl extends JComponent implements BankingTable, TransactionTableTester {
    private final BankingTableModelImp tableModel;

    public BankingTableImpl(MenuDisplayer categorizeMenu, SummarySelector summarySelector) {
        this.tableModel = new BankingTableModelImp();
        HighlightingRowTable table = new HighlightingRowTable(tableModel, 1, summarySelector);

        table.setSelectionModel(new NullSelectionModel());
        SimpleDollarRenderer dollarRenderer = new SimpleDollarRenderer();
        dollarRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(1).setCellRenderer(dollarRenderer);
        table.add(categorizeMenu.getPopup());
        List<Integer> validCategorizeColumns = new ArrayList<>(Collections.singletonList(2));
        table.addMouseListener(new UserRightClicksTableListener(categorizeMenu, validCategorizeColumns));

        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setMinWidth(75);
        table.getColumnModel().getColumn(0).setMaxWidth(75);
        table.getColumnModel().getColumn(1).setMinWidth(75);
        table.getColumnModel().getColumn(1).setMaxWidth(75);
        table.getColumnModel().getColumn(2).setMinWidth(170);
        table.getColumnModel().getColumn(2).setMaxWidth(170);
        table.getColumnModel().getColumn(3).setMinWidth(120);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void display(Transactions<BankingTransaction> tableContents){
        tableModel.updateTransactions(tableContents);
    }

    @Override
    public Maybe<BankingTransaction> getTransaction(int row){
        return tableModel.getTransaction(row);
    }

    @Override
    public Transactions<BankingTransaction> getTransactions() {
        return tableModel.getTransactions();
    }
}
