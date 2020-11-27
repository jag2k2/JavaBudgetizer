package flb.importers;

import flb.tuples.*;
import flb.util.Transactions;
import flb.util.TransactionsImpl;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ImportingTransactionsTest {

    static public Transactions<BankingTransaction> getDebitTransactions(){
        Transactions<BankingTransaction> transactions = new TransactionsImpl<>();
        BankingTransaction banking0 = new BankingTransaction("84892847712-2.64020100429113782.82",
                new GregorianCalendar(2020, Calendar.OCTOBER, 4), "Check 291",
                -2.64F, "", 13782.82F);
        BankingTransaction banking1 = new BankingTransaction("00090257601-780.00020100515917.16",
                new GregorianCalendar(2020, Calendar.OCTOBER, 5), "Westgate Church DES:Westgate C I",
                -780F, "", 15917.16F);
        BankingTransaction banking2 = new BankingTransaction("00090571004-18.08020100516697.16",
                new GregorianCalendar(2020, Calendar.OCTOBER, 5), "SPECTRUM 10/03 PURCHASE 855-707-",
                -18.08F, "", 16697.16F);
        BankingTransaction banking3 = new BankingTransaction("00090571004-167.25020100516715.24",
                new GregorianCalendar(2020, Calendar.OCTOBER, 5), "H-E-B ONLINE 10/03 PURCHASE 800-",
                -167.25F, "", 16715.24F);
        transactions.add(banking0);
        transactions.add(banking1);
        transactions.add(banking2);
        transactions.add(banking3);
        return transactions;
    }

    static public Transactions<CreditTransaction> getCreditTransactions(){
        Transactions<CreditTransaction> transactions = new TransactionsImpl<>();
        CreditTransaction credit0 = new CreditTransaction("202010091", new GregorianCalendar(2020, Calendar.OCTOBER, 9),
                "EXXONMOBIL 47946819 AUSTIN TX", -21.43F, "", "");
        CreditTransaction credit1 = new CreditTransaction("202010092", new GregorianCalendar(2020, Calendar.OCTOBER, 9),
                "AMAZON.COM*2813C0KV1 AMZNAMZN.CO", -24.99F, "", "");
        CreditTransaction credit2 = new CreditTransaction("202010093", new GregorianCalendar(2020, Calendar.OCTOBER, 9),
                "MANDOLAS ITALIAN KITCHEN CEDAR P", -74.28F, "", "");
        transactions.add(credit0);
        transactions.add(credit1);
        transactions.add(credit2);
        return transactions;
    }
}
