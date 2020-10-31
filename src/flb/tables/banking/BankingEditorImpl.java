package flb.tables.banking;

import flb.application.main.listeners.*;
import flb.components.CategoryMenuImpl;
import flb.database.interfaces.*;
import flb.tables.banking.interfaces.*;
import flb.tables.interfaces.*;
import flb.tuples.*;
import flb.util.*;
import javax.swing.*;
import java.util.*;

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
        bankingTable.addCategoryClickedListener(new UserClicksTableListener("banking", categoryMenuImpl));
    }

    public Maybe<Transaction> getTransaction(int row) {
        return bankingTable.getTransaction(row);
    }
}
