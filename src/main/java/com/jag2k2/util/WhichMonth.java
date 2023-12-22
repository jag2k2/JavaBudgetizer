package com.jag2k2.util;

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
        return year + "-" + monthString;
    }

    @Override
    public boolean equals(Object toCompare){
        if (this == toCompare) return true;
        if (this.getClass() != toCompare.getClass()) return false;
        WhichMonth whichMonthToCompare = (WhichMonth) toCompare;
        return super.get(Calendar.YEAR) == whichMonthToCompare.get(Calendar.YEAR) &&
                super.get(Calendar.MONTH) == whichMonthToCompare.get(Calendar.MONTH);
    }

    public String toSQLString() {
        String yearString = Integer.toString(super.get(Calendar.YEAR));
        String monthString = String.format("%02d", (1 + super.get(Calendar.MONTH)));
        return yearString + "-" + monthString;
    }

    public void setMonth(int monthValue) {
        super.set(Calendar.MONTH, monthValue);
    }

    public void setYear(int yearValue) {
        super.set(Calendar.YEAR, yearValue);
    }

    public void incrementMonth() {
        super.add(Calendar.MONTH, 1);
    }

    public void decrementMonth() {
        super.add(Calendar.MONTH, -1);
    }

    public int getYear(){
        return super.get(Calendar.YEAR);
    }

    public int getMonth(){
        return super.get(Calendar.MONTH);
    }
}
