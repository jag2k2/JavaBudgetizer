package flb.components.listeners;

import flb.components.*;
import java.awt.event.*;

public class UserSpinsMonth implements ActionListener {
    private MonthSelectorImp monthSelector;

    public UserSpinsMonth(MonthSelectorImp monthSelector){
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
