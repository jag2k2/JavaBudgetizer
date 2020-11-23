package flb.listeners;

import flb.components.editors.ViewChangeObserver;
import javax.swing.event.*;

public class UserFiltersCategoriesListener implements DocumentListener {
    private final ViewChangeObserver changeObserver;

    public UserFiltersCategoriesListener(ViewChangeObserver changeObserver) {
        this.changeObserver = changeObserver;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        changeObserver.update();
    }

    @Override
    public void removeUpdate(DocumentEvent e){
        changeObserver.update();
    }
}

