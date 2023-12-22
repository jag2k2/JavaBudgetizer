package com.jag2k2.components.editor.summary;

import com.jag2k2.components.editor.transaction.TableHighlighter;

public interface GoalSelectedNotifier {
    void notifyGoalSelected();
    void addGoalSelectedObserver(TableHighlighter tableHighlighter);
}
