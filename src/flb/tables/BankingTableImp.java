package flb.tables;

import flb.tuples.BankingTransaction;
import java.util.*;
import javax.swing.*;

public class BankingTableImp {
    private final BankingTableModelImp tableModel;
    private final JTable table;

    public BankingTableImp(JTable table, BankingTableModelImp tableModel) {
        this.table = table;
        this.tableModel = tableModel;
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setMinWidth(75);
        table.getColumnModel().getColumn(0).setMaxWidth(75);
        table.getColumnModel().getColumn(1).setMinWidth(65);
        table.getColumnModel().getColumn(1).setMaxWidth(65);
        table.getColumnModel().getColumn(2).setMinWidth(120);
        table.getColumnModel().getColumn(3).setMinWidth(170);
        table.getColumnModel().getColumn(3).setMaxWidth(170);
    }

    /*public Maybe<Category> getSelectedCategory() {
        int selectedRow = table.getSelectedRow();
        return tableModel.getCategory(selectedRow);
    }*/

    public void displayAndClearSelection(ArrayList<BankingTransaction> tableContents){
        tableModel.updateTransactions(tableContents);
    }

    /*public void displayAndKeepSelection(ArrayList<Category> tableContents){
        int selection = table.getSelectedRow();
        tableModel.updateCategories(tableContents);
        table.getSelectionModel().setSelectionInterval(selection, selection);
    }*/
}
