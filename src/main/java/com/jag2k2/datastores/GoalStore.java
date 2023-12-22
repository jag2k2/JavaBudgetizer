package com.jag2k2.datastores;

import com.jag2k2.util.*;
import com.jag2k2.tuples.*;
import java.util.ArrayList;

public interface GoalStore extends StoreChangeNotifier {
    ArrayList<TransactionSummary> getTransactionSummaries (WhichMonth whichMonth);
    void addGoal(TransactionSummary summary);
    void deleteGoal(TransactionSummary summary);
    boolean goalExists(TransactionSummary summary);
    void updateGoalAmount(TransactionSummary summary);
}
