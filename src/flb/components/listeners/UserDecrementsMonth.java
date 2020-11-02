package flb.components.listeners;

import flb.components.MonthSelectorImp;
import flb.util.WhichMonth;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserDecrementsMonth implements ActionListener {
    private MonthSelectorImp monthSelector;

    public UserDecrementsMonth(MonthSelectorImp monthSelector){
        this.monthSelector = monthSelector;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
