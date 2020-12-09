package flb.datastores;

import flb.util.*;
import flb.tuples.*;
import java.util.ArrayList;

public interface GoalStore extends StoreChangeNotifier {
    ArrayList<TransactionSummary> getTransactionSummaries (WhichMonth whichMonth);
    void addGoal(TransactionSummary summary);
    void deleteGoal(TransactionSummary summary);
    boolean goalExists(TransactionSummary summary);
    void updateGoalAmount(TransactionSummary summary);
}
