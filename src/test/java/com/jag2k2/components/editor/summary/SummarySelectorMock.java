package com.jag2k2.components.editor.summary;

import com.jag2k2.components.editor.transaction.TableHighlighter;
import com.jag2k2.util.Maybe;

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
