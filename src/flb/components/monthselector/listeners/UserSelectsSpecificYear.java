package flb.components.monthselector.listeners;

import flb.components.monthselector.*;
import javax.swing.*;
import java.awt.event.*;

public class UserSelectsSpecificYear implements ActionListener {
    private final MonthSelectorImpl monthSelector;

    public UserSelectsSpecificYear(MonthSelectorImpl monthSelector) {
        this.monthSelector = monthSelector;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JFormattedTextField) {
            JFormattedTextField yearField = (JFormattedTextField) e.getSource();
            Long yearValue = (Long)yearField.getValue();
            monthSelector.setYear(yearValue.intValue());
        }
    }
}
