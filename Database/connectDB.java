package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDB {

    public static void main(String[] args) {
        Connection conn = null;

        try {
            // SQLite database URL (you might need to change the URL based on your file location)
            String url = "jdbc:sqlite:StockMarket.db";

            // Create a connection to the database
            conn = DriverManager.getConnection(url);

            if (conn != null) {
                System.out.println("Connected to the SQLite database.");
                // Perform operations on the database here
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
