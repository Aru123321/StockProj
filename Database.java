import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
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

    //// customers
    public static HashMap<String, Customer> loadCustomers() {
        HashMap<String, Customer> customers = new HashMap<>();

        // ================================
        // Some data structure
        // Similar to HashMap<String, String>
        HashMap<String, HashMap<String, String>> customersInfo = new HashMap<>(); // load
        // ================================

        try {
            HashMap<String, String> info;
            for (String username: customersInfo.keySet()) {
                info = customersInfo.get(username);
                customers.put(
                    username,
                    new Customer(
                        username,
                        info.get("password"),
                        hasDTAccount(info.get("DTAccount"))
                    )
                );
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Customer information loaded from database is incomplete.");
        }
        return customers;
    }

    public static boolean hasDTAccount(String info) {
        return info.equals("1");
    }

    public void createOrUpdateCustomer (Customer customer) {
        String username = customer.getUsername();
        // get(username) or create(username)
        // set username, password, DTAccount
        // DTAccount: default = 0
    }

    public void derivativeAccountOpened(String username) {
//        info.get("DTAccount") = 1;
    }

    //// account info
    public static HashMap<String, Share> getSharesOfCustomer(String username) {
        // based on shares in Account object
        // we need the list of shares of some account with username
        return new HashMap<>();
    }

    public void updateShares(String username, String keyOfShare) {

    }
}
