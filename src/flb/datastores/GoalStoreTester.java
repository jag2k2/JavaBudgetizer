package flb.datastores;

import flb.util.*;

public interface GoalStoreTester {
    //ArrayList<Tran> getGoals(WhichMonth whichMonth);
    Maybe<Float> getGoal(WhichMonth whichMonth, String categoryName);
}
