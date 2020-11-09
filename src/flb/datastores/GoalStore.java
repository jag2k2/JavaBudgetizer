package flb.datastores;

import java.util.*;
import flb.util.*;
import flb.tuples.*;

public interface GoalStore {
    void createDefaultGoals(WhichMonth whichMonth);
    int countGoals(WhichMonth selectedMonth);
    void addGoal(Goal goal);
    void deleteGoal(String name);
    boolean goalExists(String name);
    void updateGoalAmount(float amount);
}
