package flb.components.editors.tables.models;

import flb.util.*;
import flb.tuples.*;

public interface SummaryTableModel {
    Maybe<String> getGoalName(int row);
    Maybe<TransactionSummary> getSummary(int row);
}
