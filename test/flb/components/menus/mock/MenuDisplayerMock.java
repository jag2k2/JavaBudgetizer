package flb.components.menus.mock;

import flb.components.menus.MenuDisplayer;

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
