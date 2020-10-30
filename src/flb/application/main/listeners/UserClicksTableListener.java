package flb.application.main.listeners;

import flb.components.CategoryMenuImpl;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        if(column == 2 && row >= 0) {
            categoryMenuImpl.show(table, e.getX(), e.getY());
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
