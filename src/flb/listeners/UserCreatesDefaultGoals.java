package flb.listeners;

import flb.components.editors.DefaultGoalMaker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserCreatesDefaultGoals implements ActionListener {
    private final DefaultGoalMaker goalMaker;

    public UserCreatesDefaultGoals(DefaultGoalMaker goalMaker)  {
        this.goalMaker = goalMaker;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        goalMaker.createDefaultGoals();
    }
}
