package flb.components.menus;

import javax.swing.*;

public interface MenuDisplayer {
    JPopupMenu getPopup();
    void show(JTable table, int row, int column);
}
