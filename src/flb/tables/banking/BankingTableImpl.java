package flb.tables.banking;

import flb.tables.banking.interfaces.*;
import flb.tuples.BankingTransaction;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.*;

public class BankingTableImpl implements BankingTable, BankingTableAutomator {
    private final BankingTableModelImp tableModel;
    private final JTable table;
    private final JScrollPane scrollPane;

    public BankingTableImpl() {
        this.tableModel = new BankingTableModelImp();
        this.table = new JTable(tableModel);
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

        this.scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public JScrollPane getPane() {
        return scrollPane;
    }

    public ArrayList<BankingTransaction> getTransactions() {
        return tableModel.getContents();
    }

    public void display(ArrayList<BankingTransaction> tableContents){
        tableModel.updateTransactions(tableContents);
    }

    @Override
    public void addCategoryClickedListener(MouseListener mouseListener) {
        table.addMouseListener(mouseListener);
    }
}
