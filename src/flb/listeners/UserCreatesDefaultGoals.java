package flb.listeners;

import flb.components.editors.DefaultGoalMaker;
import flb.components.monthselector.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserCreatesDefaultGoals implements ActionListener {
    private final DefaultGoalMaker goalMaker;
    private final SelectedMonthGetter selectedMonthGetter;

    public UserCreatesDefaultGoals(DefaultGoalMaker goalMaker, SelectedMonthGetter selectedMonthGetter)  {
        this.goalMaker = goalMaker;
        this.selectedMonthGetter = selectedMonthGetter;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        goalMaker.createDefaultGoals(selectedMonthGetter.getSelectedMonth());
    }
}
