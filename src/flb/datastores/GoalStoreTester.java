package flb.datastores;

import flb.util.WhichMonth;
import java.util.ArrayList;

public interface GoalStoreTester {
    //ArrayList<Tran> getGoals(WhichMonth whichMonth);
    float getGoal(WhichMonth whichMonth, String categoryName);
}
