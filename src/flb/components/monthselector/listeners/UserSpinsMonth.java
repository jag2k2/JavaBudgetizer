package flb.components.monthselector.listeners;

import flb.components.monthselector.*;
import java.awt.event.*;

public class UserSpinsMonth implements ActionListener {
    private final MonthSelectorImpl monthSelector;

    public UserSpinsMonth(MonthSelectorImpl monthSelector){
        this.monthSelector = monthSelector;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("decrement")){
            monthSelector.decrementMonth();
        }
        else if (e.getActionCommand().equals("increment")){
            monthSelector.incrementMonth();
        }
    }
}
