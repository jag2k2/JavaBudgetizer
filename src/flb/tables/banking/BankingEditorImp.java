package flb.tables.banking;

import flb.database.TransactionStoreImp;
import flb.tuples.BankingTransaction;
import flb.util.WhichMonth;

import java.util.ArrayList;

public class BankingEditorImp {
    private final TransactionStoreImp transactionStoreEditor;
    private final BankingTableImp bankingTable;

    public BankingEditorImp(TransactionStoreImp transactionStoreEditor, BankingTableImp bankingTable){
        this.transactionStoreEditor = transactionStoreEditor;
        this.bankingTable = bankingTable;
    }

    public void refreshAndClearSelection(WhichMonth searchDate) {
        ArrayList<BankingTransaction> bankingTransactions = transactionStoreEditor.getBankingTransactions(searchDate);
        bankingTable.displayAndClearSelection(bankingTransactions);
    }
}
