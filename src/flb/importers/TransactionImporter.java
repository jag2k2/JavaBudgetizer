package flb.importers;

import flb.tuples.Transaction;
import java.util.List;

public interface TransactionImporter {
    List<Transaction> getTransactionsToImport();
}
