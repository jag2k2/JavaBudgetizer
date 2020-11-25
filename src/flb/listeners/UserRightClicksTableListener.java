package flb.listeners;

import flb.components.menus.MenuDisplayer;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class UserRightClicksTableListener implements MouseListener {
    private final MenuDisplayer menuDisplayer;
    private final List<Integer> validColumns;

    public UserRightClicksTableListener(MenuDisplayer menuDisplayer, List<Integer> validColumns){
        this.menuDisplayer = menuDisplayer;
        this.validColumns = validColumns;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == 3){
            JTable table = (JTable) e.getSource();
            int row = table.rowAtPoint(e.getPoint());
            int column = table.columnAtPoint(e.getPoint());
            if(row >= 0 && validColumns.contains(column)){
                menuDisplayer.show(table, row, column);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
