import java.util.HashMap;

public class Account {

    private final String username;
    public String getUsername() {
        return username;
    }

    private final String password;
    public String getPassword() {
        return password;
    }

    private double savings;

    // key to Share object
    private HashMap<String, Share> shares;

    Account(String username, String password) {
        this.username = username;
        this.password = password;
        shares = new HashMap<>();
    }

    Account(String username, String password, HashMap<String, Share> shares) {
        this(username, password);
        this.shares = shares;
    }

    public CheckAndReason buy(String key, int number, double currentPrice) {
        if (number * currentPrice > savings)
            return new CheckAndReason(false, "No enough money to trade.");

        savings -= number * currentPrice;
        if (shares.containsKey(key))
            shares.get(key).bought(number, currentPrice);
        else
            shares.put(key, new Share(key, number, currentPrice));
        return new CheckAndReason(true);
    }

    public CheckAndReason sell(String key, int number, double currentPrice) {
        if (shares.containsKey(key)) {
            if (shares.get(key).sold(number, currentPrice)) {
                savings += number * currentPrice;
                return new CheckAndReason(true);
            }
            else
                return new CheckAndReason(false, "No enough share to be sold.");
        }
        return new CheckAndReason(false, String.format("You have no share <%s>.", key));
    }

    public double getUnrealizedGain() {
        Share share;
        double price;
        double urp = 0;
        for (String key: shares.keySet()) {
            share = shares.get(key);
            price = Queries.fetchPriceByTicker(key);
            urp += share.getUnrealizedGain(price);
        }
        return urp;
    }

    public double getRealizedGain() {
        double rg = 0;
        for (Share share: shares.values()) {
            rg += share.getRealizedGain();
        }
        return rg;
    }

    public boolean derivativesQualification() {
//        return getRealizedGain() > config.xxx;
        return getRealizedGain() > 10000;
    }
}
