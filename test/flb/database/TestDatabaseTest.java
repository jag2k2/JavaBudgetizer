package flb.database;

import org.junit.jupiter.api.*;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

class TestDatabaseTest {
    private TestDatabase database;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void confirmDatabase() {
        String query = "SELECT DATABASE()";
        ResultSet results = database.executeQuery(query);

        String selectedDatabase = "";
        try {
            results.next();
            selectedDatabase = results.getString(1);
        } catch (SQLException ex) { ex.printStackTrace(); }

        assertEquals("test", selectedDatabase);
    }

    @Test
    void confirmTables() {
        String query = "SHOW TABLES";
        ResultSet results = database.executeQuery(query);

        try {
            results.next();
            String resultTable = results.getString(1);
            assertEquals("categories", resultTable);

            results.next();
            resultTable = results.getString(1);
            assertEquals("transactions", resultTable);
        } catch (SQLException ex) {ex.printStackTrace();}

    }

    @Test
    void confirmCategories() {
        ResultSet results = database.executeQuery("SELECT * FROM categories");
        int rowCount = 0;
        try {
            while(results.next()) {
                rowCount++;
            }
        } catch (Exception ex) {ex.printStackTrace();}

        assertEquals(4, rowCount);
    }

    @Test
    void confirmTransactions() {
        ResultSet results = database.executeQuery("SELECT * FROM transactions");
        int rowCount = 0;
        try {
            while(results.next()) {
                rowCount++;
            }
        } catch (Exception ex) {ex.printStackTrace();}

        assertEquals(6, rowCount);
    }
}