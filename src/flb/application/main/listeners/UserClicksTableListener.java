package flb.application.main.listeners;

import flb.components.*;
import javax.swing.*;
import java.awt.event.*;

public class UserClicksTableListener implements MouseListener {
    private final CategoryMenuImpl categoryMenuImpl;

    public UserClicksTableListener(CategoryMenuImpl categoryMenuImpl){
        this.categoryMenuImpl = categoryMenuImpl;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        JTable table = (JTable)e.getSource();
        int row = table.rowAtPoint(e.getPoint());
        int column = table.columnAtPoint(e.getPoint());
        if(row >= 0  && column == 2) {
            categoryMenuImpl.show(table, row, column);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
