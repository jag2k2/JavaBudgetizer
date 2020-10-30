package flb.tables.banking;

import flb.database.TransactionStoreImp;
import flb.tables.banking.interfaces.*;
import flb.tuples.BankingTransaction;
import flb.util.WhichMonth;

import java.util.ArrayList;

public class BankingEditorImpl implements BankingEditorAutomator {
    private final TransactionStoreImp transactionStore;
    private final BankingTableImpl bankingTable;
    private final BankingTableAutomator tableAutomator;

    public BankingEditorImpl(TransactionStoreImp transactionStore){
        this.transactionStore = transactionStore;
        BankingTableImpl bankingTableImpl = new BankingTableImpl();
        this.bankingTable = bankingTableImpl;
        this.tableAutomator = bankingTableImpl;
    }

    public BankingTableAutomator getTableAutomator() {
        return tableAutomator;
    }

    public void refresh(WhichMonth searchDate) {
        ArrayList<BankingTransaction> bankingTransactions = transactionStore.getBankingTransactions(searchDate);
        bankingTable.refresh(bankingTransactions);
    }
}
