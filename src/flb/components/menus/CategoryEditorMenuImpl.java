package flb.components.menus;

import javax.swing.*;
import java.awt.*;
import flb.components.editors.*;
import flb.listeners.*;

public class CategoryEditorMenuImpl implements MenuDisplayer {
    private final CategoryClearer categoryClearer;
    private final CategoryDeleter categoryDeleter;
    private final JPopupMenu popupMenu;
    private final JTextField nameFilter;

    public CategoryEditorMenuImpl(CategoryEditorImpl categoryEditor, JTextField nameFilter){
        this.popupMenu = new JPopupMenu();
        this.categoryClearer = categoryEditor;
        this.categoryDeleter = categoryEditor;
        this.nameFilter = nameFilter;
    }

    @Override
    public JPopupMenu getPopup() {
        return popupMenu;
    }

    @Override
    public void show(JTable table, int row, int column) {
        buildMenu(row, column);
        Rectangle cellBounds = table.getCellRect(row, column, false);
        popupMenu.show(table, cellBounds.x, cellBounds.y);
    }

    protected void buildMenu(int activeRow, int activeColumn) {
        popupMenu.removeAll();
        if(activeColumn == 0) {
            JMenuItem deleteCategory = new JMenuItem("Delete Category");
            deleteCategory.addActionListener(new UserDeletesCategoryListener(categoryDeleter, popupMenu));
            deleteCategory.setActionCommand(Integer.toString(activeRow));
            popupMenu.add(deleteCategory);
        }
        else if (activeColumn == 1) {
            JMenuItem clearCategory = new JMenuItem("Clear Default Goal");
            clearCategory.addActionListener(new UserClearsDefaultGoalListener(categoryClearer));
            clearCategory.setActionCommand(Integer.toString(activeRow));
            popupMenu.add(clearCategory);
        }
    }
}
