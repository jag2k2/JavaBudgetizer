package flb.category.management;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import flb.category.*;

public class CategoryTable {
    private final CategoryTableModel tableModel;
    private final JTable table;
    private final JScrollPane tableScroller;

    public CategoryTable(ArrayList<Category> categories){
        tableModel = new CategoryTableModel(categories);
        table = new JTable(tableModel);
        tableScroller = new JScrollPane(table);
    }

    public void build() {
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setPreferredWidth(180);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableScroller.setPreferredSize(new Dimension(250, 300));
        tableScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tableScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void refresh(ArrayList<Category> tableContents){
        tableModel.setContents(tableContents);
        tableModel.fireTableDataChanged();
    }

    public String getSelectedRowName() {
        int selectedRow = table.getSelectedRow();
        return tableModel.getRowName(selectedRow);
    }

    public JScrollPane getPane() {
        return tableScroller;
    }
}
