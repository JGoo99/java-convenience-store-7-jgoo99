package store;

public class Product {
    private final String name;
    private final long price;
    private final long quantity;
    private final String promotion;

    public Product(String name, long price, long quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public boolean isAvailablePurchase(long quantity) {
        return this.quantity >= quantity;
    }
}
