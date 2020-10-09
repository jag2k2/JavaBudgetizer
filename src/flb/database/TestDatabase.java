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

        update = "INSERT INTO categories " + "(name, default_goal_amt, exclude) VALUES " +
                "('Name1', '100', FALSE), " +
                "('Name2', '200', TRUE), " +
                "('Name3', '300', FALSE), " +
                "('Test1', NULL, FALSE)";
        super.executeUpdate(update);
    }
}
