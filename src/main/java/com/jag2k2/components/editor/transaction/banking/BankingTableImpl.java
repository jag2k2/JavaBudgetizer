package com.jag2k2.components.editor.transaction.banking;

import com.jag2k2.components.editor.SimpleDollarRenderer;
import com.jag2k2.components.editor.summary.SummarySelector;
import com.jag2k2.components.editor.transaction.*;
import com.jag2k2.components.menus.*;
import com.jag2k2.listeners.*;
import com.jag2k2.tuples.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import com.jag2k2.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class BankingTableImpl extends JComponent implements TransactionTable<BankingTransaction>, TransactionTableTester, TableHighlighter {
    private final BankingTableModelImp tableModel;

    public BankingTableImpl(MenuDisplayer categorizeMenu, SummarySelector summarySelector) {
        this.tableModel = new BankingTableModelImp();
        HighlightingRowTable table = new HighlightingRowTable(tableModel, 1, summarySelector);

        table.setSelectionModel(new NullSelectionModel());
        TableCellRenderer cellRenderer = new SimpleDollarRenderer(table.getDefaultRenderer(Float.class));
        table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
        table.add(categorizeMenu.getPopup());
        List<Integer> validCategorizeColumns = new ArrayList<>(Collections.singletonList(2));
        table.addMouseListener(new UserRightClicksTableListener(categorizeMenu, validCategorizeColumns));
        summarySelector.addGoalSelectedObserver(this);

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
    public BankingTransaction getTransaction(int row){
        return tableModel.getTransaction(row);
    }

    @Override
    public Transactions<BankingTransaction> getTransactions() {
        return tableModel.getTransactions();
    }

    @Override
    public void highlightRows() {
        tableModel.fireTableDataChanged();
    }
}
