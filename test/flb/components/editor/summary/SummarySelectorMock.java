package flb.components.editor.summary;

import flb.components.editor.summary.SummarySelector;
import flb.util.Maybe;

public class SummarySelectorMock implements SummarySelector {

    @Override
    public Maybe<String> getSelectedGoalName() {
        return new Maybe<>("MockGoalName");
    }
}
