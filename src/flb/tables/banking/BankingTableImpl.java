package flb.tables.banking;

import flb.tables.banking.interfaces.*;
import flb.tuples.BankingTransaction;
import java.util.*;
import javax.swing.*;

public class BankingTableImpl implements BankingTable, BankingTableAutomator {
    private final BankingTableModelImp tableModel;
    private final JTable table;

    public BankingTableImpl() {
        this.tableModel = new BankingTableModelImp();
        this.table = new JTable(tableModel);
        table.setRowSelectionAllowed(false);
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setMinWidth(75);
        table.getColumnModel().getColumn(0).setMaxWidth(75);
        table.getColumnModel().getColumn(1).setMinWidth(65);
        table.getColumnModel().getColumn(1).setMaxWidth(65);
        table.getColumnModel().getColumn(2).setMinWidth(170);
        table.getColumnModel().getColumn(2).setMaxWidth(170);
        table.getColumnModel().getColumn(3).setMinWidth(120);
    }

    public ArrayList<BankingTransaction> getTransactions() {
        return tableModel.getContents();
    }

    public void refresh(ArrayList<BankingTransaction> tableContents){
        tableModel.updateTransactions(tableContents);
    }
}
