package flb.components.monthselector.listeners;

import flb.components.monthselector.*;
import javax.swing.*;
import java.awt.event.*;

public class UserSelectsSpecificMonth implements ItemListener {
    private MonthSelectorImpl monthSelector;

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
