package flb.listeners;

import flb.components.menus.MenuDisplayer;
import javax.swing.*;
import java.awt.event.*;

public class UserRightClicksCreditTransactions implements MouseListener {
    private final MenuDisplayer menuDisplayer;

    public UserRightClicksCreditTransactions(MenuDisplayer menuDisplayer){
        this.menuDisplayer = menuDisplayer;
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
            if(row >= 0 && column == 4){
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
