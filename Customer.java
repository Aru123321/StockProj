/*
* transform customer information to customer object
* */

import Database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Customer extends User{
    Connection connection = Database.getConnection();

    private ArrayList<Share> shares;


    private final String username;
    private String password;
    private int ID;
    private String name;
    private double unProfits;
    private double profits;
    private int pAccountID;
    private String options;
    private double balance;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getUnProfits() {
        return unProfits;
    }

    public void setUnProfits(double unProfits) {
        this.unProfits = unProfits;
    }

    public double getProfits() {
        return profits;
    }

    public void setProfits(double profits) {
        this.profits = profits;
    }

    public int getPAccountID() {
        return pAccountID;
    }

    public void setPAccountID(int pAccountID) {
        this.pAccountID = pAccountID;
    }

    public String getOptions() {
        return options.toLowerCase();
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }



    private boolean hasDTAccount;
    public boolean hasDerivativeTradingAccount() {
        return hasDTAccount;
    }

    private Account account;
    private DerivativeTradingAccount dTAccount;

    public void setDerivativeTradingAccount() throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT Options FROM users WHERE Username=username AND Password=password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(preparedStatement);

    }

    Customer(String username, String password, boolean hasDTAccount) {
        // for manager
        // may have a CustomerManager class for extension
        this.username = username;
        this.password = password;
        this.hasDTAccount = hasDTAccount;
    }

    Customer(String username, String password) throws SQLException {
        ResultSet resultSet = Queries.fetchUserByUsername(username);
        assert resultSet != null;
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("Name");
        String usernameNew = resultSet.getString("Username");
        String passwordNew = resultSet.getString("Password");
        double unProfits = resultSet.getDouble("UnProfits");
        double profits = resultSet.getDouble("Profits");
        int pAccountID = resultSet.getInt("PAccountID");
        this.options = resultSet.getString("Options");
        this.balance = resultSet.getDouble("Balance");

        this.ID = id;
        this.username = usernameNew;


        updateCustomer(name, passwordNew, unProfits, profits, pAccountID, options, this.balance );
        ResultSet resultSet1 = Queries.fetchSharesByUsername(this.username);
        if (!(resultSet1 == null)){
            this.shares = Queries.convertResultSetToShareList(resultSet1 , this.username);
        }
        else {
            this.shares = new ArrayList<Share>();
        }
        System.out.println(this.shares);
        createAccount();
    }

    public void createAccount() {
        //HashMap<String, Share> shares;
        //if !(Queries.)
        //shares = Queries.fetchSharesByUsername(username);
        //account = new Account(username, password, shares);
        if (hasDTAccount)
            dTAccount = new DerivativeTradingAccount(username, password);
    }

    public void changePassword(String password) {
        // usernamePasswordPairs needs to be updated
        this.password = password;
    }

    public CheckAndReason buy(String key, int number) {
        if (account == null) {
            return new CheckAndReason(false, "You do not have an account.");
        }
        double currentPrice = Queries.fetchPriceByTicker(key);
        return account.buy(key, number, currentPrice);
    }

    public CheckAndReason sell(String key, int number) {
        if (account == null) {
            return new CheckAndReason(false, "You do not have an account.");
        }
        double currentPrice = Queries.fetchPriceByTicker(key);
        return account.sell(key, number, currentPrice);
    }

    public void addShare(Share share){
        int index = this.shares.indexOf(share);
        if (index != -1) {
            Share existingShare = this.shares.get(index);
            existingShare.updateAveragePrice(share.getNumber(), share.getAveragePrice());
            existingShare.setNumber(share.getNumber() + existingShare.getNumber());
        } else {
            this.shares.add(share);
        }
    }

    public ArrayList<Share> getShares(){
        return this.shares;
    }

    public void deleteShare(String shareID){
    }


/*
    public CheckAndReason buyWithDTAccount() {
        if (dTAccount == null) {
            return new CheckAndReason(false, "You do not have a derivative account.");
        }
        return new CheckAndReason(true, "Not implemented");
    }

    public CheckAndReason sellWithDTAccount() {
        if (dTAccount == null) {
            return new CheckAndReason(false, "You do not have a derivative account.");
        }
        return new CheckAndReason(true, "Not implemented");
    }*/

    public String toString() {
        return this.getUsername();
    }

    @Override
    public boolean equals(User user) {
        return this.username.equals(user.getUsername());
    }

    /*@Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }*/

    public void updateCustomer(String name, String password, double unProfits, double profits,
    int pAccountID, String options, double Balance) {

        // Set the values using the setters
        setName(name);
        setPassword(password);
        setUnProfits(unProfits);
        setProfits(profits);
        setPAccountID(pAccountID);
        setOptions(options);
        setBalance(Balance);

    }

    public void updateDBCustomer(){
        Queries.updateUserDetails(this.ID, this.name, this.username, this.password, this.unProfits, this.profits,
        this.pAccountID, this.options, this.balance);

    }

    public void updateBalance(double Balance) {
        setBalance(Balance);
    }

    public void sellShare(String ticker, int quantity) {
        for (Share share : shares) {
            if (share.getTicker().equals(ticker)) {
                int currentShares = share.getNumber();
                share.setNumber(currentShares - quantity);
                share.updateDBShare();
                if (share.getNumber() == 0){
                    this.shares.remove(share);
                    share.deleteFromDB();
                    System.out.println("SHARE REMOVED");
                }
                System.out.println("Sold " + quantity + " shares of " + ticker);
                return;
            }
        }
    }

    public void updateUnrealizedGains(String ticker, Double newStockPrice) {
        for (Share share : shares) {
            if (share.getTicker().equals(ticker)) {
                int currentShares = share.getNumber();
                this.unProfits += currentShares * (newStockPrice - share.getAveragePrice());
                System.out.println(this.unProfits);
                this.updateDBCustomer();
            }
        }
    }
}
