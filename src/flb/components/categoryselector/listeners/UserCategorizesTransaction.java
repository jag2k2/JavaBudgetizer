package flb.components.categoryselector.listeners;

import java.awt.event.*;
import flb.components.editors.TransactionCategorizer;

public class UserCategorizesTransaction implements ActionListener {
    private final TransactionCategorizer transactionCategorizer;

    public UserCategorizesTransaction(TransactionCategorizer transactionCategorizer){
        this.transactionCategorizer = transactionCategorizer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        String[] elements = actionCommand.split(",");
        int row = Integer.parseInt(elements[0]);
        String categoryName = elements[1];
        transactionCategorizer.userCategorizesTransaction(row, categoryName);
    }
}
