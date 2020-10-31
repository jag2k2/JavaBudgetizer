package flb.tables.credit;

import flb.tables.credit.interfaces.*;
import flb.tuples.*;
import flb.util.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class CreditTableImpl implements CreditTable, CreditTableAutomator {
    private final CreditTableModelImpl tableModel;
    private final JTable table;
    private final JScrollPane scrollPane;

    public CreditTableImpl() {
        this.tableModel = new CreditTableModelImpl();
        this.table = new JTable(tableModel);
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

        this.scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public JScrollPane getPane() {
        return scrollPane;
    }

    /*public Maybe<Category> getSelectedCategory() {
        int selectedRow = table.getSelectedRow();
        return tableModel.getCategory(selectedRow);
    }*/

    public void displayAndClearSelection(ArrayList<CreditTransaction> tableContents){
        tableModel.updateTransactions(tableContents);
    }

    @Override
    public void addCategoryClickedListener(MouseListener mouseListener) {
        table.addMouseListener(mouseListener);
    }

    @Override
    public ArrayList<CreditTransaction> getTransactions() {
        return tableModel.getTransactions();
    }

    @Override
    public Maybe<Transaction> getTransaction(int row){
        return tableModel.getTransaction(row);
    }

    /*public void displayAndKeepSelection(ArrayList<Category> tableContents){
        int selection = table.getSelectedRow();
        tableModel.updateCategories(tableContents);
        table.getSelectionModel().setSelectionInterval(selection, selection);
    }*/
}