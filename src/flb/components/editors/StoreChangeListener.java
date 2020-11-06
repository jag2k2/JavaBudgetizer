package flb.components.editors;

import flb.util.WhichMonth;

public interface StoreChangeListener {
    void updateAndKeepSelection(WhichMonth selectedDate);
}
