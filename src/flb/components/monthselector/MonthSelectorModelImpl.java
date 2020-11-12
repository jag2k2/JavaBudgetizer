package flb.components.monthselector;

import flb.util.*;
import java.util.*;

public class MonthSelectorModelImpl {
    private WhichMonth selectedMonth;

    public MonthSelectorModelImpl() {
        Calendar currentDate = new GregorianCalendar();
        this.selectedMonth = new WhichMonth(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH));
    }

    public void setToCurrentMonth() {
        Calendar currentDate = new GregorianCalendar();
        selectedMonth = new WhichMonth(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH));
    }

    public void incrementMonth() {
        selectedMonth.incrementMonth();
    }

    public void decrementMonth() {
        selectedMonth.decrementMonth();
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
    }

    public void setMonth(int monthValue) {
        selectedMonth.setMonth(monthValue);
    }
}
