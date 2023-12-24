public class Share{

    private final String ticker;

    private final String userName;
    public String getTicker() {
        return ticker;
    }

    private int number = 0;
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return averagePrice;
    }

    private double averagePrice;
    public double getAveragePrice() {
        return averagePrice;
    }

    private double realizedGain;
    public double getRealizedGain() {
        return realizedGain;
    }

    public double getUnrealizedGain(double currentPrice) {
        return number * (currentPrice - averagePrice);
    }

    public Share(String ticker) {
        this.userName = "";
        this.ticker = ticker;
        number = 0;
        realizedGain = 0;
    }

    public void updateDBShare(){
        Queries.updateShare(this.userName, this.ticker, this.number, this.averagePrice);
    }

    public void deleteFromDB(){
        Queries.deleteShare(this.userName, this.ticker);

    }


    public Share(String ticker, int number, double unitPrice) {
        this.ticker = ticker;
        this.number = number;
        this.averagePrice = unitPrice;
        realizedGain = 0;
        this.userName = "";
    }

    // used for load records
    public Share(String ticker, int number, double unitPrice, String userName) {
        this.userName = userName;
        this.ticker = ticker;
        this.number = number;
        this.averagePrice = unitPrice;
        this.realizedGain = 0;
    }

    public void updateAveragePrice(int number, double unitPrice) {
        this.averagePrice = (this.number * averagePrice + number * unitPrice) / (this.number + number);
    }

    public void bought(int number, double unitPrice) {
        updateAveragePrice(number, unitPrice);
        this.number += number;
    }

    public boolean sold(int number, double unitPrice) {
        if (number > this.number)
            return false;
        this.number -= number;
        this.realizedGain += number * (unitPrice - averagePrice);
        return true;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Share share = (Share) obj;
        return this.ticker.equals(share.getTicker());
    }

    @Override
    public String toString() {
        return "Ticker: " + this.ticker + ", Shares: " + this.number + ", AvgPrice: " + averagePrice;
    }
}
