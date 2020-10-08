import java.sql.*;

public class Database {

    private Connection connection;
    String url = "jdbc:mysql://localhost/$name";
    String user = "jag2k2";
    String password = "jeff1229";

    public Database(String databaseName) {
        url = url.replace("$name", databaseName);
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query) {
        ResultSet results = null;
        try {
            Statement statementExecutor = connection.createStatement();
            results = statementExecutor.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return results;
    }

    public void executeUpdate(String update) {
        try {
            Statement statementExecutor = connection.createStatement();
            statementExecutor.executeUpdate(update);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
