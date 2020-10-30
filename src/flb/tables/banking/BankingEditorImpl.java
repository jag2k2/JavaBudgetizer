package flb.tables.banking;

import flb.application.main.listeners.UserClicksTableListener;
import flb.components.CategoryMenuImpl;
import flb.database.interfaces.*;
import flb.tables.banking.interfaces.*;
import flb.tables.interfaces.TransactionCategorizer;
import flb.tuples.BankingTransaction;
import flb.util.WhichMonth;
import javax.swing.*;
import java.util.ArrayList;

public class BankingEditorImpl implements BankingEditorAutomator, TransactionCategorizer {
    private final TransactionStore transactionStore;
    private final BankingTableImpl bankingTable;
    private final BankingTableAutomator tableAutomator;

    public BankingEditorImpl(TransactionStore transactionStore){
        this.transactionStore = transactionStore;
        BankingTableImpl bankingTableImpl = new BankingTableImpl();
        this.bankingTable = bankingTableImpl;
        this.tableAutomator = bankingTableImpl;
    }

    public JScrollPane getPane() {
        return bankingTable.getPane();
    }

    public BankingTableAutomator getTableAutomator() {
        return tableAutomator;
    }

    public void refresh(WhichMonth searchDate) {
        ArrayList<BankingTransaction> bankingTransactions = transactionStore.getBankingTransactions(searchDate);
        bankingTable.display(bankingTransactions);
    }

    public void addCategorizingListener(CategoryMenuImpl categoryMenuImpl) {
        bankingTable.addCategoryClickedListener(new UserClicksTableListener(categoryMenuImpl));
    }

    @Override
    public void userCategorizesTransaction() {
        System.out.println("User Categorizes Transaction");
    }
}
