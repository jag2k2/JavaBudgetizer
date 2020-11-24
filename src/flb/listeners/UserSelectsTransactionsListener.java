package flb.listeners;

import flb.components.editor.transaction.credit.StatusDisplayer;
import javax.swing.event.*;

public class UserSelectsTransactionsListener implements ListSelectionListener {
    private final StatusDisplayer statusDisplayer;

    public UserSelectsTransactionsListener(StatusDisplayer statusDisplayer){
        this.statusDisplayer = statusDisplayer;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        statusDisplayer.displaySelectionStatus();
    }
}
