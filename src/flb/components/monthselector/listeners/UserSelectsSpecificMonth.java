package flb.components.monthselector.listeners;

import flb.components.monthselector.MonthSelectorModelImpl;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class UserSelectsSpecificMonth implements ItemListener {
    private MonthSelectorModelImpl monthSelectorModel;

    public UserSelectsSpecificMonth(MonthSelectorModelImpl monthSelectorModel) {
        this.monthSelectorModel = monthSelectorModel;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED){
            JComboBox selector = (JComboBox) e.getSource();
            monthSelectorModel.setMonth(selector.getSelectedIndex());
        }
    }
}
