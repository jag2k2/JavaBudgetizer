package flb.components.editor.summary;

import flb.components.editor.transaction.TableHighlighter;

public interface GoalSelectedNotifier {
    void notifyGoalSelected();
    void addGoalSelectedObserver(TableHighlighter tableHighlighter);
}
