package flb.datastores;

import java.sql.ResultSet;

public interface DataStore {
    ResultSet executeQuery(String query);
    void executeUpdate(String update);
}
