package flb.components.editors.mock;

import flb.components.editors.SummarySelector;
import flb.util.Maybe;

public class SummarySelectorMock implements SummarySelector {

    @Override
    public Maybe<String> getSelectedGoalName() {
        return new Maybe<>("MockGoalName");
    }
}
