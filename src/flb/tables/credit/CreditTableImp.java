package flb.tables.credit;

import flb.tuples.CreditTransaction;
import java.util.*;
import javax.swing.*;

public class CreditTableImp {
    private final CreditTableModelImp tableModel;
    private final JTable table;

    public CreditTableImp(JTable table, CreditTableModelImp tableModel) {
        this.table = table;
        this.tableModel = tableModel;
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setMinWidth(75);
        table.getColumnModel().getColumn(0).setMaxWidth(75);
        table.getColumnModel().getColumn(1).setMinWidth(65);
        table.getColumnModel().getColumn(1).setMaxWidth(65);
        table.getColumnModel().getColumn(2).setMinWidth(170);
        table.getColumnModel().getColumn(2).setMaxWidth(170);
        table.getColumnModel().getColumn(3).setMinWidth(120);
        table.getColumnModel().getColumn(4).setMinWidth(120);
        table.getColumnModel().getColumn(4).setMaxWidth(120);
    }

    /*public Maybe<Category> getSelectedCategory() {
        int selectedRow = table.getSelectedRow();
        return tableModel.getCategory(selectedRow);
    }*/

    public void displayAndClearSelection(ArrayList<CreditTransaction> tableContents){
        tableModel.updateTransactions(tableContents);
    }

    /*public void displayAndKeepSelection(ArrayList<Category> tableContents){
        int selection = table.getSelectedRow();
        tableModel.updateCategories(tableContents);
        table.getSelectionModel().setSelectionInterval(selection, selection);
    }*/
}