package flb.databases;

import java.sql.*;

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
                "('Income', '1000', FALSE), " +
                "('Name2', '200', TRUE), " +
                "('Name3', '300', FALSE), " +
                "('Test1::sub1', NULL, FALSE), " +
                "('Test1::sub2', 500, TRUE)";
        super.executeUpdate(update);

        tables = super.executeQuery("SHOW TABLES LIKE 'goals'");
        try {
            if(tables.next()) {
                super.executeUpdate("DROP TABLE goals");
            }
        } catch (Exception ex) {ex.printStackTrace();}

        update = "CREATE TABLE test.goals " +
                "(`id` INT NOT NULL AUTO_INCREMENT, " +
                "`year_mo` VARCHAR(255) NOT NULL , " +
                "`category_id` INT NOT NULL DEFAULT -1 , " +
                "`amount` FLOAT(9,2) NOT NULL ," +
                "PRIMARY KEY (`id`)) ENGINE = InnoDB";
        super.executeUpdate(update);

        update = "INSERT INTO goals (year_mo, category_id, amount) VALUES " +
                "('2020-09', '1', 50), " +
                "('2020-09', '2', 55), " +
                "('2020-09', '3', 60), " +
                "('2020-10', '1', 1000), " +
                "('2020-10', '3', 70), " +
                "('2020-10', '5', 75)";
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
                "`pay_group` VARCHAR(255) , " +
                "PRIMARY KEY (`id`)) ENGINE = InnoDB";
        super.executeUpdate(update);

        update = "INSERT INTO transactions (date, type, description, amount, category_id, balance, reference, pay_group) VALUES " +
                "('2020-10-25', 'banking', 'Amazon', '-50.00', '5', '1000.00', '123', NULL), " +
                "('2020-10-26', 'banking', 'HEB', '-40.00', '2', '960.00', '456', NULL), " +
                "('2020-10-27', 'banking', 'Walmart', '-30.00', '-1', '930.00', '789', NULL), " +
                "('2020-10-25', 'credit', 'Shell', '-20.00', '3', '-1', '3589048', NULL), " +
                "('2020-10-26', 'credit', 'Papa Johns', '-25.00', '-1', '2', '3589049', NULL), " +
                "('2020-10-27', 'credit', 'Torchys', '-35.00', '-1', '-1', '3589050', NULL)";
        super.executeUpdate(update);
    }
}
