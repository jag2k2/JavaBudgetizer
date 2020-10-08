import org.junit.jupiter.api.Test;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    void executeQuery() {
        Database connection = new Database("test");

        ResultSet results = connection.executeQuery("SELECT DATABASE()");

        String selectedDatabase = "";
        try {
            results.next();
            selectedDatabase = results.getString(1);
        } catch (SQLException ex) { ex.printStackTrace(); }

        assertEquals("test", selectedDatabase);
    }
}