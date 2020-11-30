package flb.databases;

import flb.tuples.CreditTransaction;
import flb.util.WhichMonth;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CreditFactory {

    private static final String[] refs = {"3589048", "3589049", "3589050"};
    private static final Calendar[] dates = {new GregorianCalendar(2020, Calendar.OCTOBER, 25),
            new GregorianCalendar(2020, Calendar.OCTOBER, 26),
            new GregorianCalendar(2020, Calendar.OCTOBER, 27)};
    private static final String[] descriptions = {"Shell", "Papa Johns", "Torchys"};
    private static final float[] amounts = {-20, -25, -35};
    private static final String[] categories = {"Name3", "", ""};
    private static final String[] payGroups = {"", "", ""};

    private static final String[] importRefs = {"202010091", "202010092", "202010093"};
    private static final Calendar[] importDates = {new GregorianCalendar(2020, Calendar.OCTOBER, 9),
            new GregorianCalendar(2020, Calendar.OCTOBER, 9),
            new GregorianCalendar(2020, Calendar.OCTOBER, 9)};
    private static final String[] importDescriptions = {"EXXONMOBIL 47946819 AUSTIN TX",
            "AMAZON.COM*2813C0KV1 AMZNAMZN.CO", "MANDOLAS ITALIAN KITCHEN CEDAR P"};
    private static final float[] importAmounts = {-21.43F, -24.99F, -74.28F};

    public static String[] getDefaultRefs(){
        return refs;
    }

    public static String[] getDefaultRefs(int[] selectedRows){
        String[] selectedRefs = new String[selectedRows.length];
        int index = 0;
        for (int row : selectedRows){
            selectedRefs[index] = refs[row];
            index++;
        }
        return selectedRefs;
    }

    public static String[] getImportRefs() {
        return importRefs;
    }

    static public float getSelectedSum(int[] selectedRows){
        float sum = 0F;
        for (int row : selectedRows){
            sum += amounts[row];
        }
        return sum;
    }

    static public CreditTransaction makeDefaultTransaction(String ref){
        int index = Arrays.binarySearch(refs, ref);
        return new CreditTransaction(refs[index], dates[index], descriptions[index], amounts[index], categories[index], payGroups[index]);
    }

    static public CreditTransaction makeImportingTransaction(String ref){
        int index = 0;
        for (String importRef : importRefs){
            if (importRef.equals(ref)){
                return new CreditTransaction(importRefs[index], importDates[index], importDescriptions[index], importAmounts[index], "", "");
            }
            else{
                index++;
            }
        }
        return null;
    }

    static public CreditTransaction makeNewTransaction(WhichMonth whichMonth) {
        return new CreditTransaction("7", new GregorianCalendar(whichMonth.getYear(), whichMonth.getMonth(),
                28), "Taco Bell", 14.53F, "", "");
    }

    static public CreditTransaction makeTransactionWithCategory(String ref, String categoryName){
        int index = Arrays.binarySearch(refs, ref);
        return new CreditTransaction(refs[index], dates[index], descriptions[index], amounts[index], categoryName, payGroups[index]);
    }

    static public CreditTransaction makeTransactionWithNewRef(String ref, String newRef) {
        int index = Arrays.binarySearch(refs, ref);
        return new CreditTransaction(newRef, dates[index], descriptions[index], amounts[index], categories[index], payGroups[index]);
    }

    static public CreditTransaction makeTransactionWithNewDate(String ref, Calendar newDate){
        int index = Arrays.binarySearch(refs, ref);
        return new CreditTransaction(refs[index], newDate, descriptions[index], amounts[index], categories[index], payGroups[index]);
    }

    static public CreditTransaction makeTransactionWithNewDescription(String ref, String newDescription){
        int index = Arrays.binarySearch(refs, ref);
        return new CreditTransaction(refs[index], dates[index], newDescription, amounts[index], categories[index], payGroups[index]);
    }

    static public CreditTransaction makeTransactionWithNewAmount(String ref, float newAmount){
        int index = Arrays.binarySearch(refs, ref);
        return new CreditTransaction(refs[index], dates[index], descriptions[index], newAmount, categories[index], payGroups[index]);
    }

    static public CreditTransaction makeTransactionWithNewPayGroup(String ref, String newPayGroup){
        int index = Arrays.binarySearch(refs, ref);
        return new CreditTransaction(refs[index], dates[index], descriptions[index], amounts[index], categories[index], newPayGroup);
    }
}
