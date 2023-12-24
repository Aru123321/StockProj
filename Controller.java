/*
* My idea is that set controller as a subject
* and tracker, gui, accountManager, tradeManager as observers
*
* when one of the observers change,
* for example, when the user do sth in the gui, then the tracker record; managers process
* */
import Database.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Controller implements Subject{

    private static final String managerUser = "manage1";
    private static final String managePassword = "Moon";

    private final Tracker tracker;
    //private final GUI gui;
    private final TradeManager tradeManager;


    Controller() {
        CurrentUsers.getInstance();
        Database.getInstance();
        tracker = new Tracker();
        //gui = new GUI();
        AccountManager.getInstance();
        tradeManager = new TradeManager();
        MainForm signupform = new MainForm();
    }

    public static boolean checkCredentials(String username, String password){
        //System.out.println("GOPT HEREEEE");
        try {
            ResultSet resultSet = Queries.fetchUserByUsernamePassword(username,password);
            System.out.println(resultSet.getString(9));
            return resultSet.next();


        } catch (Exception e) {
            return false;
        }
    }

    /*public CheckAndReason signup(String username, String password) {
        CheckAndReason cr =  accountManager.createAccount(username, password);
        if (cr.success) {
            db.createOrUpdateCustomer(new Customer(username, password, false));
        }
        return cr;
    }*/

    /*public CheckAndReason login(String username, String password) {
        return accountManager.correctUsernamePasswordPair(username, password);
    }*/

    public CheckAndReason buy(Account account, String key, int number, double currentPrice) {
        return account.buy(key, number, currentPrice);
    }

    public CheckAndReason sell(Account account, String key, int number, double currentPrice) {
        return account.sell(key, number, currentPrice);
    }

    public static boolean managerCheck(String username, String password){
        return (username.equals(managerUser) && password.equals(managePassword));
    }
    public static boolean isPositiveNumber(String str) {
        // Check if the string is not empty
        if (str != null && !str.isEmpty()) {
            // Check if the string consists only of digits and is not zero
            if (str.matches("\\d+") && !str.equals("0")) {
                return true; // String is a positive number
            }
            else return str.matches("^(?!0\\d)(\\d+(\\.\\d{1,2})?)$");
        }
        return false; // String is not a positive number
    }


}
