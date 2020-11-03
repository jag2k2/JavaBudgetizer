package flb.components;

import flb.components.interfaces.MenuDisplayer;

import javax.swing.*;

public class MenuDisplayerMock implements MenuDisplayer {

    @Override
    public JPopupMenu getPopup() {
        return new JPopupMenu();
    }

    @Override
    public void show(JTable table, int row, int column) {

    }
}
