package flb.tables.credit;

import flb.tables.listeners.UserClicksTableListener;
import flb.components.interfaces.MenuDisplayer;
import flb.tables.credit.interfaces.*;
import flb.tuples.*;
import flb.util.*;
import java.util.*;
import javax.swing.*;

public class CreditTableImpl implements CreditTable, CreditTableTester {
    private final CreditTableModelImpl tableModel;
    private final JTable table;
    private final JScrollPane scrollPane;

    public CreditTableImpl(MenuDisplayer menuDisplayer) {
        this.tableModel = new CreditTableModelImpl();
        this.table = new JTable(tableModel);
        this.scrollPane = new JScrollPane(table);
        table.add(menuDisplayer.getPopup());
        table.addMouseListener(new UserClicksTableListener(menuDisplayer));

        layout();
    }

    protected void layout(){
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setMinWidth(75);
        table.getColumnModel().getColumn(0).setMaxWidth(75);
        table.getColumnModel().getColumn(1).setMinWidth(65);
        table.getColumnModel().getColumn(1).setMaxWidth(65);
        table.getColumnModel().getColumn(2).setMinWidth(170);
        table.getColumnModel().getColumn(2).setMaxWidth(170);
        table.getColumnModel().getColumn(3).setMinWidth(120);
        table.getColumnModel().getColumn(4).setMinWidth(120);
        table.getColumnModel().getColumn(4).setMaxWidth(120);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    @Override
    public JScrollPane getPane() {
        return scrollPane;
    }

    @Override
    public void displayAndClearSelection(ArrayList<CreditTransaction> tableContents){
        tableModel.updateTransactions(tableContents);
    }

    @Override
    public Maybe<Transaction> getTransaction(int row){
        return tableModel.getTransaction(row);
    }

    @Override
    public ArrayList<CreditTransaction> getTransactions() {
        return tableModel.getTransactions();
    }
}