package flb.datastores;

import flb.tuples.Goal;
import flb.util.WhichMonth;

import java.util.ArrayList;

public interface GoalStoreTester {
    ArrayList<Goal> getGoals(WhichMonth whichMonth);
}
