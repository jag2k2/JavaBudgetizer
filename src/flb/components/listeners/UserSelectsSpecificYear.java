package flb.components.listeners;

import flb.components.MonthSelectorModelImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserSelectsSpecificYear implements ActionListener {
    private MonthSelectorModelImpl monthSelectorModel;

    public UserSelectsSpecificYear(MonthSelectorModelImpl monthSelectorModel) {
        this.monthSelectorModel = monthSelectorModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFormattedTextField yearField = (JFormattedTextField) e.getSource();
        int yearValue = Integer.parseInt(yearField.getText());
        monthSelectorModel.setYear(yearValue);
    }
}
