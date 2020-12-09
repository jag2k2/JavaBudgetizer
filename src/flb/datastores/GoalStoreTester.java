package flb.datastores;

import flb.util.*;

public interface GoalStoreTester {
    Maybe<Float> getGoal(WhichMonth whichMonth, String categoryName);
}
