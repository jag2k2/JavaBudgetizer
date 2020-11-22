package flb.databases;

import java.sql.ResultSet;

public interface SQLExecutor {
    ResultSet executeQuery(String query);
    void executeUpdate(String update);
}
