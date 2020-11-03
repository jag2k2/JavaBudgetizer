package flb.components.monthselector.listeners;

import flb.components.monthselector.MonthSelectorModelImpl;

import java.awt.event.*;

public class UserSpinsMonth implements ActionListener {
    private MonthSelectorModelImpl monthModel;

    public UserSpinsMonth(MonthSelectorModelImpl monthModel){
        this.monthModel = monthModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("decrement")){
            monthModel.decrementMonth();
        }
        else if (e.getActionCommand().equals("increment")){
            monthModel.incrementMonth();
        }
    }
}
