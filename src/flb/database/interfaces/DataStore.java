package flb.database.interfaces;

import java.sql.ResultSet;

public interface DataStore {
    ResultSet executeQuery(String query);
    void executeUpdate(String update);
}
