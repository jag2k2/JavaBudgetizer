package com.jag2k2.components.monthselector.listeners;

import com.jag2k2.components.monthselector.*;
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
