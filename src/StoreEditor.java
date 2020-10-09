import java.sql.ResultSet;

public interface StoreEditor {
    ResultSet executeQuery(String query);

    void executeUpdate(String update);
}
