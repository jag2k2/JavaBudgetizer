package flb.database;

import org.junit.jupiter.api.*;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

class TestDatabaseTest {
    private TestDatabase database;

    @BeforeEach
    void init() {
        database = new TestDatabase();
        database.connect();
    }

    @Test
    void executeQuery() {
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
    void executeUpdate() {
        String update = "INSERT INTO categories (name, default_goal_amt, exclude) VALUES ('test', NULL, FALSE)";
        database.executeUpdate(update);

        ResultSet results = database.executeQuery("SELECT * FROM categories");
        int rowCount = 0;
        try {
            while(results.next()) {
                rowCount++;
            }
        } catch (Exception ex) {ex.printStackTrace();}

        assertEquals(5, rowCount);
    }

    @AfterEach
    void shutdown() {
        database.close();
    }
}