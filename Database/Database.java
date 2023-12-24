package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static Connection theConnection;

    private static Database theDB;
    private static final String JDBC_URL = "jdbc:sqlite:Database/StockMarket.db";

    private Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            theConnection = DriverManager.getConnection(JDBC_URL);
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static synchronized Database getInstance() {
        if (theDB == null) {
            theDB = new Database();
        }
        return theDB;
    }

    public static Connection getConnection() {
        return theConnection;
    }
}
