package flb.tables.listeners;

import flb.components.interfaces.MenuDisplayer;
import javax.swing.*;
import java.awt.event.*;

public class UserClicksTableListener implements MouseListener {
    private final MenuDisplayer menuDisplayer;

    public UserClicksTableListener(MenuDisplayer menuDisplayer){
        this.menuDisplayer = menuDisplayer;
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
            menuDisplayer.show(table, row, column);
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