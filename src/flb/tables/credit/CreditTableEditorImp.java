package flb.tables.credit;

import flb.database.TransactionStoreImp;
import flb.tuples.CreditTransaction;
import flb.util.WhichMonth;

import java.util.ArrayList;

public class CreditTableEditorImp {
    private final TransactionStoreImp transactionStoreEditor;
    private final CreditTableImp creditTable;

    public CreditTableEditorImp(TransactionStoreImp transactionStoreEditor, CreditTableImp creditTable){
        this.transactionStoreEditor = transactionStoreEditor;
        this.creditTable = creditTable;
    }

    public void refreshAndClearSelection(WhichMonth searchDate) {
        ArrayList<CreditTransaction> creditTransactions = transactionStoreEditor.getCreditTransactions(searchDate);
        creditTable.displayAndClearSelection(creditTransactions);
    }
}
