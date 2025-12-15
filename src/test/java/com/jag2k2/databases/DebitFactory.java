package com.jag2k2.databases;

import com.jag2k2.tuples.BankingTransaction;
import com.jag2k2.util.WhichMonth;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DebitFactory {

    private static final String[] refs = {"123", "456", "789"};
    private static final Calendar[] dates = {new GregorianCalendar(2020, Calendar.OCTOBER, 25),
            new GregorianCalendar(2020, Calendar.OCTOBER, 26),
            new GregorianCalendar(2020, Calendar.OCTOBER, 27)};
    private static final String[] descriptions = {"Amazon", "HEB", "Walmart"};
    private static final float[] amounts = {-50, -40, -30};
    private static final float[] balances = {1000, 960, 930};
    private static final String[] categories = {"Test1::sub2", "Name2", ""};

    private static final String[] importRefs = {"13782.82020100413782.82", "15917.16020100515917.16",
            "16697.16020100516697.16", "16715.24020100516715.24"};
    private static final Calendar[] importDates = {new GregorianCalendar(2020, Calendar.OCTOBER, 4),
            new GregorianCalendar(2020, Calendar.OCTOBER, 5),
            new GregorianCalendar(2020, Calendar.OCTOBER, 5),
            new GregorianCalendar(2020, Calendar.OCTOBER, 5)};
    private static final String[] importDescriptions = {"Check 291", "Westgate Church DES:Westgate C I",
            "SPECTRUM 10/03 PURCHASE 855-707-", "H-E-B ONLINE 10/03 PURCHASE 800-"};
    private static final float[] importAmounts = {-2.64F, -780F, -18.08F, -167.25F};
    private static final float[] importBalances = {-1, -1, -1, -1};

    public static String[] getDefaultRefs(){
        return refs;
    }
    public static String[] getImportRefs() {
        return importRefs;
    }

    static public BankingTransaction makeDefaultTransaction(String ref){
        int index = Arrays.binarySearch(refs, ref);
        return new BankingTransaction(refs[index], dates[index], descriptions[index], amounts[index], categories[index], balances[index]);
    }

    static public BankingTransaction makeImportingTransaction(String ref){
        int index = 0;
        for (String importRef : importRefs){
            if (importRef.equals(ref)){
                return new BankingTransaction(importRefs[index], importDates[index], importDescriptions[index], importAmounts[index], "", importBalances[index]);
            }
            else{
                index++;
            }
        }
        return null;
    }

    static public BankingTransaction makeNewTransaction(WhichMonth whichMonth) {
        return new BankingTransaction("7", new GregorianCalendar(whichMonth.getYear(), whichMonth.getMonth(),
                28), "Taco Bell", 14.53F, "", 100F);
    }

    static public BankingTransaction makeTransactionWithCategory(String ref, String categoryName){
        int index = Arrays.binarySearch(refs, ref);
        return new BankingTransaction(refs[index], dates[index], descriptions[index], amounts[index], categoryName, balances[index]);
    }

    static public BankingTransaction makeTransactionWithNewRef(String ref, String newRef) {
        int index = Arrays.binarySearch(refs, ref);
        return new BankingTransaction(newRef, dates[index], descriptions[index], amounts[index], categories[index], balances[index]);
    }

    static public BankingTransaction makeTransactionWithNewDate(String ref, Calendar newDate){
        int index = Arrays.binarySearch(refs, ref);
        return new BankingTransaction(refs[index], newDate, descriptions[index], amounts[index], categories[index], balances[index]);
    }

    static public BankingTransaction makeTransactionWithNewDescription(String ref, String newDescription){
        int index = Arrays.binarySearch(refs, ref);
        return new BankingTransaction(refs[index], dates[index], newDescription, amounts[index], categories[index], balances[index]);
    }

    static public BankingTransaction makeTransactionWithNewAmount(String ref, float newAmount){
        int index = Arrays.binarySearch(refs, ref);
        return new BankingTransaction(refs[index], dates[index], descriptions[index], newAmount, categories[index], balances[index]);
    }

    static public BankingTransaction makeTransactionWithNewBalance(String ref, float newBalance){
        int index = Arrays.binarySearch(refs, ref);
        return new BankingTransaction(refs[index], dates[index], descriptions[index], amounts[index], categories[index], newBalance);
    }
}
