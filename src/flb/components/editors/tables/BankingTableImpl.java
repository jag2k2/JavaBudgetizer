package flb.components.editors.tables;

import flb.components.categoryselector.MenuDisplayer;
import flb.components.editors.tables.listeners.UserClicksTableListener;
import flb.components.editors.tables.models.*;
import flb.tuples.*;
import java.util.*;
import flb.util.*;
import javax.swing.*;

public class BankingTableImpl implements BankingTable, BankingTableTester {
    private final BankingTableModelImp tableModel;
    private final JTable table;
    private final JScrollPane scrollPane;

    public BankingTableImpl(MenuDisplayer menuDisplayer) {
        this.tableModel = new BankingTableModelImp();
        this.table = new JTable(tableModel);
        this.scrollPane = new JScrollPane(table);
        table.add(menuDisplayer.getPopup());
        table.addMouseListener(new UserClicksTableListener(menuDisplayer));

        layout();
    }

    protected void layout() {
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setMinWidth(75);
        table.getColumnModel().getColumn(0).setMaxWidth(75);
        table.getColumnModel().getColumn(1).setMinWidth(65);
        table.getColumnModel().getColumn(1).setMaxWidth(65);
        table.getColumnModel().getColumn(2).setMinWidth(170);
        table.getColumnModel().getColumn(2).setMaxWidth(170);
        table.getColumnModel().getColumn(3).setMinWidth(120);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    @Override
    public JScrollPane getPane() {
        return scrollPane;
    }

    @Override
    public void display(ArrayList<BankingTransaction> tableContents){
        tableModel.updateTransactions(tableContents);
    }

    @Override
    public Maybe<Transaction> getTransaction(int row){
        return tableModel.getTransaction(row);
    }

    @Override
    public ArrayList<BankingTransaction> getTransactions() {
        return tableModel.getTransactions();
    }
}
