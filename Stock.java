public class Stock implements Asset{

    private final String key;
    private final String name;
    private double price;

    public void buy(int amount){
        System.out.println("Bought" + " " + this.name);
    }

    public void sell(int amount){
        System.out.println("Sold" + " " + this.name);
    }

    Stock(String key, String name, double price) {
        this.key = key;
        this.name = name;
        this.price = price;
    }

    public void priceFluctuation() {
        price += 0;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Asset asset) {
        return false;
    }

}
