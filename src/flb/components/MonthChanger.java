package flb.components;

import flb.components.editors.MonthChangeObserver;

public interface MonthChanger {
    void addMonthChangeObserver(MonthChangeObserver monthChangeObserver);
    void notifyMonthChange();
}
