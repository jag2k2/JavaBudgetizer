package flb.components.monthselector.listeners;

import flb.components.monthselector.MonthSelectorModelImpl;
import javax.swing.*;
import java.awt.event.*;

public class UserSelectsSpecificYear implements ActionListener {
    private final MonthSelectorModelImpl monthSelectorModel;

    public UserSelectsSpecificYear(MonthSelectorModelImpl monthSelectorModel) {
        this.monthSelectorModel = monthSelectorModel;
        //this.yearField = yearField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JFormattedTextField) {
            JFormattedTextField yearField = (JFormattedTextField) e.getSource();
            Long yearValue = (Long)yearField.getValue();
            monthSelectorModel.setYear(yearValue.intValue());
        }
    }
}
