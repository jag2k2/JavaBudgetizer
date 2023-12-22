package com.jag2k2.components.menus;

import com.jag2k2.components.editor.summary.MonthGoalClearer;
import com.jag2k2.listeners.UserClearsSummaryGoalListener;

import javax.swing.*;
import java.awt.*;

public class GoalEditorMenuImpl implements MenuDisplayer {
    private final MonthGoalClearer goalClearer;
    private final JPopupMenu popupMenu;

    public GoalEditorMenuImpl(MonthGoalClearer goalClearer){
        this.goalClearer = goalClearer;
        this.popupMenu = new JPopupMenu();
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
        if(activeColumn == 1){
            JMenuItem clearGoalItem = new JMenuItem("Clear Goal");
            clearGoalItem.addActionListener(new UserClearsSummaryGoalListener(goalClearer));
            clearGoalItem.setActionCommand(Integer.toString(activeRow));
            popupMenu.add(clearGoalItem);
        }
    }
}
