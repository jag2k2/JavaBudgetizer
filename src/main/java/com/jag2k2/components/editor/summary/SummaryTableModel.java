package com.jag2k2.components.editor.summary;

import com.jag2k2.util.*;
import com.jag2k2.tuples.*;

public interface SummaryTableModel {
    Maybe<String> getGoalName(int row);
    Maybe<TransactionSummary> getSummary(int row);
}
