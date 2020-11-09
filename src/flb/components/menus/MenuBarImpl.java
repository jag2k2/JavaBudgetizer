package flb.components.menus;

import flb.components.editors.DefaultGoalMaker;
import flb.components.monthselector.MonthSelectorImpl;
import flb.listeners.UserCreatesDefaultGoals;
import javax.swing.*;

public class MenuBarImpl {
    private final JMenuBar menuBar;
    private final DefaultGoalMaker goalMaker;
    private final MonthSelectorImpl monthSelector;

    public MenuBarImpl(DefaultGoalMaker goalMaker, MonthSelectorImpl monthSelector) {
        this.menuBar = new JMenuBar();
        this.goalMaker = goalMaker;
        this.monthSelector = monthSelector;

        layout();
    }

    protected void layout() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem importMenuItem = new JMenuItem("Import Transactions");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(importMenuItem);
        fileMenu.add(exitMenuItem);

        JMenu budgetMenu = new JMenu("Budget");
        JMenuItem defaultGoalsMenuItem = new JMenuItem("Create Default Goals");
        defaultGoalsMenuItem.addActionListener(new UserCreatesDefaultGoals(goalMaker, monthSelector));
        budgetMenu.add(defaultGoalsMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(budgetMenu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
