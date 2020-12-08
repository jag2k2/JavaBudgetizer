package flb.components.editor.summary;

import flb.util.Maybe;

public interface SummarySelector extends GoalSelectedNotifier {
    Maybe<String> getSelectedGoalName();
}
