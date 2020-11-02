package flb.util;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WhichMonth extends GregorianCalendar {
    public WhichMonth (int year, int month) {
        super(year, month, 1);
    }

    @Override
    public String toString() {
        int year = super.get(Calendar.YEAR);
        String monthString = new DateFormatSymbols().getMonths()[super.get(Calendar.MONTH)];
        return year + ", " + monthString;
    }

    @Override
    public boolean equals(Object toCompare){
        if (this == toCompare) return true;
        if (this.getClass() != toCompare.getClass()) return false;
        WhichMonth whichMonthToCompare = (WhichMonth) toCompare;
        return super.get(Calendar.YEAR) == whichMonthToCompare.get(Calendar.YEAR) &&
                super.get(Calendar.MONTH) == whichMonthToCompare.get(Calendar.MONTH);
    }

    public void incrementMonth() {
        super.add(Calendar.MONTH, 1);
    }

    public void decrementMonth() {
        super.add(Calendar.MONTH, -1);
    }
}
