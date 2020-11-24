package flb.components.editor.transaction.banking;

import flb.components.editor.SimpleDollarRenderer;
import flb.components.editor.summary.SummarySelector;
import flb.components.editor.transaction.HighlightableRowTable;
import flb.components.editor.transaction.TransactionTable;
import flb.components.editor.transaction.TransactionTableTester;
import flb.components.menus.*;
import flb.listeners.*;
import flb.tuples.*;

import java.awt.*;
import java.util.*;
import flb.util.*;
import javax.swing.*;

public class BankingTableImpl implements TransactionTable, TransactionTableTester {
    private final BankingTableModelImp tableModel;
    private final HighlightableRowTable table;
    private final JPanel panel;

    public BankingTableImpl(MenuDisplayer menuDisplayer, SummarySelector summarySelector) {
        this.tableModel = new BankingTableModelImp();
        this.table = new HighlightableRowTable(tableModel, 1, summarySelector);
        this.panel = new JPanel(new BorderLayout());

        table.setSelectionModel(new NullSelectionModel());
        SimpleDollarRenderer dollarRenderer = new SimpleDollarRenderer();
        dollarRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(1).setCellRenderer(dollarRenderer);
        table.add(menuDisplayer.getPopup());
        table.addMouseListener(new UserClicksCategoryColumnListener(menuDisplayer));

        layout();
    }

    protected void layout() {
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
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void display(ArrayList<Transaction> tableContents){
        tableModel.updateTransactions(tableContents);
    }

    @Override
    public void highlightRows(){
        tableModel.fireTableDataChanged();
    }

    @Override
    public Maybe<Transaction> getTransaction(int row){
        return tableModel.getTransaction(row);
    }

    @Override
    public ArrayList<Transaction> getTransactions() {
        return tableModel.getTransactions();
    }
}
