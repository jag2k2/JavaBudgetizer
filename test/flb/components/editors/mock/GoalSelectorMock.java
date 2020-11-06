package flb.components.editors.mock;

import flb.components.editors.GoalSelector;
import flb.util.Maybe;

public class GoalSelectorMock implements GoalSelector {

    @Override
    public Maybe<String> getSelectedGoalName() {
        return new Maybe<>("MockGoalName");
    }
}
