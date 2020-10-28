package flb.tables.banking;

import flb.database.TransactionStoreEditorImp;
import flb.tuples.BankingTransaction;
import flb.util.WhichMonth;

import java.util.ArrayList;

public class BankingTableEditorImp {
    private final TransactionStoreEditorImp transactionStoreEditor;
    private final BankingTableImp bankingTable;

    public BankingTableEditorImp(TransactionStoreEditorImp transactionStoreEditor, BankingTableImp bankingTable){
        this.transactionStoreEditor = transactionStoreEditor;
        this.bankingTable = bankingTable;
    }

    public void refreshAndClearSelection(WhichMonth searchDate) {
        ArrayList<BankingTransaction> bankingTransactions = transactionStoreEditor.getBankingTransactions(searchDate);
        bankingTable.displayAndClearSelection(bankingTransactions);
    }
}
