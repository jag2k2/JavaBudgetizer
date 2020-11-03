package flb.tables.interfaces;

import flb.util.WhichMonth;

public interface StoreChangeListener {
    void update(WhichMonth selectedDate);
}
