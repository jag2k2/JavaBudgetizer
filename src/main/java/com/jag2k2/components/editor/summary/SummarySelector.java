package com.jag2k2.components.editor.summary;

import com.jag2k2.util.Maybe;

public interface SummarySelector extends GoalSelectedNotifier {
    Maybe<String> getSelectedGoalName();
}
