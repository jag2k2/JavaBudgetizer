package flb.tables.credit;

import flb.database.TransactionStoreEditorImp;
import flb.tuples.CreditTransaction;
import flb.util.WhichMonth;

import java.util.ArrayList;

public class CreditTableEditorImp {
    private final TransactionStoreEditorImp transactionStoreEditor;
    private final CreditTableImp creditTable;

    public CreditTableEditorImp(TransactionStoreEditorImp transactionStoreEditor, CreditTableImp creditTable){
        this.transactionStoreEditor = transactionStoreEditor;
        this.creditTable = creditTable;
    }

    public void refreshAndClearSelection(WhichMonth searchDate) {
        ArrayList<CreditTransaction> creditTransactions = transactionStoreEditor.getCreditTransactions(searchDate);
        creditTable.displayAndClearSelection(creditTransactions);
    }
}
