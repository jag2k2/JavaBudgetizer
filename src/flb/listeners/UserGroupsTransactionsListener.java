package flb.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import flb.components.editor.transaction.credit.TransactionGrouper;

public class UserGroupsTransactionsListener implements ActionListener {
    private final TransactionGrouper transactionGrouper;

    public UserGroupsTransactionsListener(TransactionGrouper transactionGrouper){
        this.transactionGrouper = transactionGrouper;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        transactionGrouper.groupSelectedTransactions(new GregorianCalendar());
    }
}
