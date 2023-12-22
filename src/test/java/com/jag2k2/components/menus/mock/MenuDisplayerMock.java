package com.jag2k2.components.menus.mock;

import com.jag2k2.components.menus.MenuDisplayer;

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
