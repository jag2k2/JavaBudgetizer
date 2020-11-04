package flb.datastores;

import java.sql.*;

public abstract class AbstractDatabase implements DataStore {

    private Connection connection;
    private String url = "jdbc:mysql://localhost/$name";
    private final String user = "jag2k2";
    private final String password = "jeff1229";

    public AbstractDatabase(String databaseName) {
        url = url.replace("$name", databaseName);
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
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

    @Override
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
