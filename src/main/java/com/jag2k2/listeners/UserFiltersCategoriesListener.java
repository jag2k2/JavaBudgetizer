package com.jag2k2.listeners;

import com.jag2k2.components.editor.ViewChangeObserver;
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

