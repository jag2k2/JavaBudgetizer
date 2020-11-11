package flb.datastores;

import flb.util.*;
import flb.tuples.*;

public interface GoalStore {
    void createDefaultGoals(WhichMonth whichMonth);
    int countGoals(WhichMonth selectedMonth);
    void addGoal(TransactionSummary summary);
    void deleteGoal(TransactionSummary summary);
    boolean goalExists(TransactionSummary summary);
    void updateGoalAmount(TransactionSummary summary);
}
