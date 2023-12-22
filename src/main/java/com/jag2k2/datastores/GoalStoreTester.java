package com.jag2k2.datastores;

import com.jag2k2.util.*;

public interface GoalStoreTester {
    Maybe<Float> getGoal(WhichMonth whichMonth, String categoryName);
}
