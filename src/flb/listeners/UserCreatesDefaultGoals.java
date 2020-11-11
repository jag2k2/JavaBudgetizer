package flb.listeners;

import flb.components.editors.DefaultGoalMaker;
import flb.components.monthselector.MonthSelectorImpl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserCreatesDefaultGoals implements ActionListener {
    private final DefaultGoalMaker goalMaker;
    private final MonthSelectorImpl monthSelector;

    public UserCreatesDefaultGoals(DefaultGoalMaker goalMaker, MonthSelectorImpl monthSelector)  {
        this.goalMaker = goalMaker;
        this.monthSelector = monthSelector;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        goalMaker.createDefaultGoals(monthSelector.getSelectedMonth());
    }
}
