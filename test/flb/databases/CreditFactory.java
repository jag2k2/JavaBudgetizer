package flb.databases;

import flb.components.editor.transaction.credit.GroupNameFactory;
import flb.tuples.CreditTransaction;
import flb.util.Transactions;
import flb.util.TransactionsImpl;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CreditFactory {

    private static final int numberOfTransactions = 3;
    private static final String[] refs = {"3589048", "3589049", "3589050"};
    private static final Calendar[] dates = {new GregorianCalendar(2020, Calendar.OCTOBER, 25),
            new GregorianCalendar(2020, Calendar.OCTOBER, 26),
            new GregorianCalendar(2020, Calendar.OCTOBER, 27)};
    private static final String[] descriptions = {"Shell", "Papa Johns", "Torchys"};
    private static final float[] amounts = {-20, -25, -35};
    private static final String[] categories = {"Name3", "", ""};
    private static final String[] payGroups = {"", "", ""};

    static public Transactions<CreditTransaction> makeGroupedTransactions(int[] rows, Calendar date){
        Transactions<CreditTransaction> transactions = new TransactionsImpl<>();
        String payGroup = GroupNameFactory.createGroupName(date, getTransactionSum(rows));
        for (int i = 0; i < numberOfTransactions; i++){
            if (Arrays.binarySearch(rows, i) >= 0){
                transactions.add(new CreditTransaction(refs[i], dates[i], descriptions[i], amounts[i], categories[i], payGroup));
            }
            else {
                transactions.add(new CreditTransaction(refs[i], dates[i], descriptions[i], amounts[i], categories[i], payGroups[i]));
            }
        }
        return transactions;
    }

    static public Transactions<CreditTransaction> makeTransactionsWithCategorizedEntry(int row, String category){
        Transactions<CreditTransaction> transactions = new TransactionsImpl<>();
        for (int i = 0; i < numberOfTransactions; i++){
            if (i == row){
                transactions.add(new CreditTransaction(refs[i], dates[i], descriptions[i], amounts[i], category, payGroups[i]));
            }
            else {
                transactions.add(new CreditTransaction(refs[i], dates[i], descriptions[i], amounts[i], categories[i], payGroups[i]));
            }
        }
        return transactions;
    }

    static public Transactions<CreditTransaction> makeTransactions() {
        Transactions<CreditTransaction> transactions = new TransactionsImpl<>();
        for (int i = 0; i < numberOfTransactions; i++){
            transactions.add(new CreditTransaction(refs[i], dates[i], descriptions[i], amounts[i], categories[i], payGroups[i]));
        }
        return transactions;
    }

    static public Transactions<CreditTransaction> makeTransactions(int[] rows){
        Transactions<CreditTransaction> transactions = new TransactionsImpl<>();
        for(int row : rows){
            transactions.add(new CreditTransaction(refs[row], dates[row], descriptions[row], amounts[row], categories[row], payGroups[row]));
        }
        return transactions;
    }

    static public float getTransactionSum(int[] rows){
        float sum = 0F;
        for (int row : rows){
            sum += amounts[row];
        }
        return sum;
    }
}
