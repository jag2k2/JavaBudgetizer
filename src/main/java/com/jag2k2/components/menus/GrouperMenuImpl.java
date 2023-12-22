package com.jag2k2.components.menus;

import com.jag2k2.components.editor.transaction.credit.TransactionGrouper;
import com.jag2k2.listeners.UserGroupsTransactionsListener;
import javax.swing.*;
import java.awt.*;

public class GrouperMenuImpl implements MenuDisplayer{
    private final JPopupMenu popupMenu;
    private final TransactionGrouper transactionGrouper;

    public GrouperMenuImpl(TransactionGrouper transactionGrouper){
        this.popupMenu = new JPopupMenu();
        this.transactionGrouper = transactionGrouper;
    }

    @Override
    public JPopupMenu getPopup() {
        return popupMenu;
    }

    @Override
    public void show(JTable table, int row, int column) {
        buildMenu(column);
        Rectangle cellBounds = table.getCellRect(row, column, false);
        popupMenu.show(table, cellBounds.x, cellBounds.y);
    }

    protected void buildMenu(int activeColumn){
        popupMenu.removeAll();
        if(activeColumn == 4) {
            JMenuItem payGroupItem = new JMenuItem("Pay Transactions");
            payGroupItem.addActionListener(new UserGroupsTransactionsListener(transactionGrouper));
            popupMenu.add(payGroupItem);
        }
    }
}
