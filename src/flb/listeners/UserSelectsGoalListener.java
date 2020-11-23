package flb.listeners;

import flb.components.editor.transaction.TableHighlighter;
import java.util.*;
import javax.swing.event.*;

public class UserSelectsGoalListener implements ListSelectionListener {
    private final ArrayList<TableHighlighter> tableHighlighters;

    public UserSelectsGoalListener(ArrayList<TableHighlighter> tableHighlighters){
        this.tableHighlighters = tableHighlighters;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            for (TableHighlighter tableHighlighter : tableHighlighters){
                tableHighlighter.highlightRows();
            }
        }
    }
}
