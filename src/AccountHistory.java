import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountHistory {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost/gringotts";
        String user = "jag2k2";
        String password = "jeff1229";

        String query = "SELECT * FROM CATEGORIES";

        try{
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()){
                System.out.println(rs.getString(2));
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(AccountHistory.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
