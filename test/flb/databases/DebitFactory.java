package flb.databases;

import flb.tuples.BankingTransaction;
import flb.util.Transactions;
import flb.util.TransactionsImpl;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DebitFactory {

    private static final int numberOfTransactions = 3;
    private static final String[] refs = {"123", "456", "789"};
    private static final Calendar[] dates = {new GregorianCalendar(2020, Calendar.OCTOBER, 25),
            new GregorianCalendar(2020, Calendar.OCTOBER, 26),
            new GregorianCalendar(2020, Calendar.OCTOBER, 27)};
    private static final String[] descriptions = {"Amazon", "HEB", "Walmart"};
    private static final float[] amounts = {-50, -40, -30};
    private static final float[] balances = {1000, 960, 930};
    private static final String[] categories = {"Test1::sub2", "Name2", ""};

    static public Transactions<BankingTransaction> makeTransactions() {
        Transactions<BankingTransaction> transactions = new TransactionsImpl<>();
        for(int i = 0; i < numberOfTransactions; i++) {
            transactions.add(new BankingTransaction(refs[i], dates[i], descriptions[i], amounts[i], categories[i], balances[i]));
        }
        return transactions;
    }

    static public Transactions<BankingTransaction> makeTransactionsWithCategorizedEntry(int row, String category){
        Transactions<BankingTransaction> transactions = new TransactionsImpl<>();
        for(int i = 0; i < numberOfTransactions; i++) {
            if (i == row){
                transactions.add(new BankingTransaction(refs[i], dates[i], descriptions[i], amounts[i], category, balances[i]));
            }
            else {
                transactions.add(new BankingTransaction(refs[i], dates[i], descriptions[i], amounts[i], categories[i], balances[i]));
            }
        }
        return transactions;
    }



}
