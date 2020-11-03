package flb.components.monthselector;

import flb.components.editors.MonthChangeListener;
import flb.util.*;
import java.util.*;

public class MonthSelectorModelImpl {
    private final ArrayList<MonthChangeListener> monthChangeListeners;
    private WhichMonth selectedMonth;

    public MonthSelectorModelImpl() {
        Calendar currentDate = new GregorianCalendar();
        this.selectedMonth = new WhichMonth(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH));
        this.monthChangeListeners = new ArrayList<>();
    }

    public void addMonthChangeListener(MonthChangeListener monthChangeListener) {
        monthChangeListeners.add(monthChangeListener);
    }

    protected void notifyMonthChange() {
        for(MonthChangeListener monthChangeListener : monthChangeListeners){
            monthChangeListener.update(selectedMonth);
        }
    }

    public void setToCurrentMonth() {
        Calendar currentDate = new GregorianCalendar();
        selectedMonth = new WhichMonth(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH));
        notifyMonthChange();
    }

    public void incrementMonth() {
        selectedMonth.incrementMonth();
        notifyMonthChange();
    }

    public void decrementMonth() {
        selectedMonth.decrementMonth();
        notifyMonthChange();
    }

    public WhichMonth getSelectedMonth() {
        return selectedMonth;
    }

    public int getYear() {
        return selectedMonth.getYear();
    }

    public int getMonth() {
        return selectedMonth.getMonth();
    }

    public void setYear(int yearValue) {
        selectedMonth.setYear(yearValue);
        notifyMonthChange();
    }

    public void setMonth(int monthValue) {
        selectedMonth.setMonth(monthValue);
        notifyMonthChange();
    }
}
