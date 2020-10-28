package flb.application.main.listeners;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UserClicksTableListener implements MouseListener {
    private final JPopupMenu categoryMenu;
    public UserClicksTableListener(JPopupMenu categoryMenu){
        this.categoryMenu = categoryMenu;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable target = (JTable)e.getSource();
        int row = target.rowAtPoint(e.getPoint());
        int column = target.columnAtPoint(e.getPoint());
        System.out.println(row + ", " + column);
        categoryMenu.show(target, e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
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
