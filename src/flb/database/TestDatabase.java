package flb.database;

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
                "('Name1', '100', FALSE), " +
                "('Name2', '200', TRUE), " +
                "('Name3', '300', FALSE), " +
                "('Test1', NULL, FALSE)";
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
                "('2020-10-27', 'banking', 'Walmart', '30.00', '-1', '930.00', '3589047')";
        super.executeUpdate(update);
    }
}
