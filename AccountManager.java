import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class AccountManager extends User{
    private static AccountManager instance;

    private final String username = "manage1";
    private final String password = "Moon";

    @Override
    public String getUsername() {
        return username;
    }

    // id to account
    //private final HashMap<String, Account> accounts;

    // id to account
   // private final HashMap<String, DerivativeTradingAccount> derivativeTradingAccounts;

    private AccountManager() {
    }

    public static synchronized AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

   // AccountManager() {

        /*//HashMap<String, Customer> customers = Database.loadCustomers();

        accounts = new HashMap<>();
        derivativeTradingAccounts = new HashMap<>();

        Customer customer;
        String password;
        HashMap<String, Share> shares;
        for (String username: customers.keySet()) {
            customer = customers.get(username);
            password = customer.getPassword();

            shares = Database.getSharesOfCustomer(username);
            loadAccount(username, password, shares);

            if (customer.hasDerivativeTradingAccount()) {
                loadDerivativeTradingAccount(username, password);
            }
        }*/
 //   }

    /*public void loadAccount(String username, String password, HashMap<String, Share> shares) {
        accounts.put(username, new Account(username, password, shares));
    }

    public void loadDerivativeTradingAccount(String username, String password) {
        derivativeTradingAccounts.put(username, new DerivativeTradingAccount(username, password));
    }*/

    /*public CheckAndReason createAccount(String username, String password) {
        if (accounts.containsKey(username))
            return new CheckAndReason(false, String.format("Account with username <%s> already exists.", username));
        accounts.put(username, new Account(username, password));
        return new CheckAndReason(true);
    }

    public CheckAndReason checkAndCreateDTAccount(String username, String password) {
        if (!accounts.containsKey(username))
            return new CheckAndReason(false, "You haven't created a base account.");
        else if (derivativeTradingAccounts.containsKey(username))
            return new CheckAndReason(false, String.format("Derivative TradingAccount with username <%s> already exists.", username));
        else if (!accounts.get(username).derivativesQualification())
            return new CheckAndReason(false, "You are not qualified to open a Derivative Trading Account.");
        else {
            derivativeTradingAccounts.put(username, new DerivativeTradingAccount(username, password));
            return new CheckAndReason(true);
        }
    }*/

    /*public CheckAndReason correctUsernamePasswordPair(String username, String password) {
        if (!accounts.containsKey(username))
            return new CheckAndReason(false, "Unknown username.");
        if (!Objects.equals(accounts.get(username).getPassword(), password))
            return new CheckAndReason(false, "Wrong password.");
        return new CheckAndReason(true);
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public HashMap<String, DerivativeTradingAccount> getDerivativeTradingAccounts() {
        return derivativeTradingAccounts;
    }*/

    public String toString() {
        return "Username: " + username + ", Password: " + password;
    }

    @Override
    public boolean equals(User user) {
        return this.username.equals(user.getUsername());
    }
}
