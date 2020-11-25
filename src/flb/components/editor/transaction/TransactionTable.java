package flb.components.editor.transaction;

import flb.tuples.*;
import flb.util.*;
import javax.swing.*;

public interface TransactionTable extends TableHighlighter {
    JPanel getPanel();
    Maybe<? extends Transaction> getTransaction(int row);
}
