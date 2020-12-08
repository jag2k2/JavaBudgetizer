package flb.components.editor.summary;

import flb.components.editor.transaction.TableHighlighter;
import flb.util.Maybe;

public class SummarySelectorMock implements SummarySelector {

    @Override
    public Maybe<String> getSelectedGoalName() {
        return new Maybe<>("MockGoalName");
    }

    @Override
    public void notifyGoalSelected() {

    }

    @Override
    public void addGoalSelectedObserver(TableHighlighter tableHighlighter) {

    }
}
