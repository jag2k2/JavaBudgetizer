package flb.database;

import flb.tuples.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TestDatabase extends AbstractDatabase {

    public TestDatabase(){
        super("test");
    }

    @Override
    public void connect() {
        super.connect();
        ResultSet tables = super.executeQuery("SHOW TABLES LIKE 'categories'");
        try {
            if(tables.next()) {
                super.executeUpdate("DROP TABLE categories");
            }

        } catch (Exception ex) {ex.printStackTrace();}
        String update = "CREATE TABLE test.categories " +
                "( `id` INT NOT NULL AUTO_INCREMENT, " +
                "`name` VARCHAR(255) NOT NULL , " +
                "`default_goal_amt` FLOAT(9.2) NULL DEFAULT NULL , " +
                "`exclude` BOOLEAN NOT NULL , " +
                "PRIMARY KEY (`id`)) ENGINE = InnoDB";
        super.executeUpdate(update);

        update = "INSERT INTO categories (name, default_goal_amt, exclude) VALUES " +
                "('Name1', '100', FALSE), " +
                "('Name2', '200', TRUE), " +
                "('Name3', '300', FALSE), " +
                "('Test1::sub1', NULL, FALSE), " +
                "('Test1::sub2', 500, TRUE)";
        super.executeUpdate(update);

        tables = super.executeQuery("SHOW TABLES LIKE 'transactions'");
        try {
            if(tables.next()) {
                super.executeUpdate("DROP TABLE transactions");
            }
        } catch (Exception ex) {ex.printStackTrace();}
        update = "CREATE TABLE test.transactions " +
                "( `id` INT NOT NULL AUTO_INCREMENT, " +
                "`type` VARCHAR(255) NOT NULL , " +
                "`date` DATE NOT NULL , " +
                "`description` VARCHAR(255) NOT NULL , " +
                "`amount` FLOAT(9,2) NOT NULL , " +
                "`category_id` INT NOT NULL DEFAULT -1 , " +
                "`balance` FLOAT(9,2) NOT NULL , " +
                "`reference` VARCHAR(255) NOT NULL , " +
                "PRIMARY KEY (`id`)) ENGINE = InnoDB";
        super.executeUpdate(update);

        update = "INSERT INTO transactions (date, type, description, amount, category_id, balance, reference) VALUES " +
                "('2020-10-25', 'banking', 'Amazon', '50.00', '1', '1000.00', '3589045'), " +
                "('2020-10-26', 'banking', 'HEB', '40.00', '2', '960.00', '3589046'), " +
                "('2020-10-27', 'banking', 'Walmart', '30.00', '-1', '930.00', '3589047'), " +
                "('2020-10-25', 'credit', 'Shell', '20.00', '1', '-1', '3589048'), " +
                "('2020-10-26', 'credit', 'Papa Johns', '25.00', '-1', '2', '3589049'), " +
                "('2020-10-27', 'credit', 'Torchys', '35.00', '-1', '-1', '3589050')";
        super.executeUpdate(update);
    }

    static public ArrayList<Category> getTestCategories() {
        ArrayList<Category> testCategories = new ArrayList<>();
        testCategories.add(new Category("Name1", 100, false));
        testCategories.add(new Category("Name2", 200, true));
        testCategories.add(new Category("Name3", 300, false));
        testCategories.add(new Category("Test1::sub1", Float.NaN, false));
        testCategories.add(new Category("Test1::sub2", 500, true));
        return testCategories;
    }

    static public ArrayList<BankingTransaction> getTestBankingTransactions() {
        ArrayList<BankingTransaction> testTransactions = new ArrayList<>();

        Calendar date1 = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        Calendar date2 = new GregorianCalendar(2020,Calendar.OCTOBER,26);
        Calendar date3 = new GregorianCalendar(2020, Calendar.OCTOBER, 27);

        testTransactions.add(new BankingTransaction("1", date1, "Amazon", 50F, "Name1", 1000F));
        testTransactions.add(new BankingTransaction("2", date2, "HEB", 40F, "Name2", 960F));
        testTransactions.add(new BankingTransaction("3", date3, "Walmart", 30F, "", 930F));

        return testTransactions;
    }

    static public ArrayList<CreditTransaction> getTestCreditTransactions() {
        ArrayList<CreditTransaction> testTransactions = new ArrayList<>();

        Calendar date1 = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        Calendar date2 = new GregorianCalendar(2020,Calendar.OCTOBER,26);
        Calendar date3 = new GregorianCalendar(2020, Calendar.OCTOBER, 27);

        testTransactions.add(new CreditTransaction("3589048", date1, "Shell", 20F, "Name1"));
        testTransactions.add(new CreditTransaction("3589049", date2, "Papa Johns", 25F, ""));
        testTransactions.add(new CreditTransaction("3589050", date3, "Torchys", 35F, ""));

        return testTransactions;
    }
}
