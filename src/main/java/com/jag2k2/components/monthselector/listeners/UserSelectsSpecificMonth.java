package com.jag2k2.components.monthselector.listeners;

import com.jag2k2.components.monthselector.*;
import javax.swing.*;
import java.awt.event.*;

public class UserSelectsSpecificMonth implements ItemListener {
    private final MonthSelectorImpl monthSelector;

    public UserSelectsSpecificMonth(MonthSelectorImpl monthSelector) {
        this.monthSelector = monthSelector;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED){
            JComboBox selector = (JComboBox) e.getSource();
            monthSelector.setMonth(selector.getSelectedIndex());
        }
    }
}
