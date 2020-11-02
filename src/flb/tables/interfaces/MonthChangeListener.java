package flb.tables.interfaces;

import flb.util.WhichMonth;

public interface MonthChangeListener {
    void refresh(WhichMonth selectedDate);
}
