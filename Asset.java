// Asset interface representing a tradable asset
public interface Asset {
    double getPrice();  // Method to get the price of the asset

    public abstract boolean equals(Asset asset);

}