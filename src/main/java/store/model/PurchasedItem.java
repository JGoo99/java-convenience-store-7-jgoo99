package store.model;

import java.text.DecimalFormat;

public class PurchasedItem {

    private final String name;
    private int quantity;
    private final long price;

    public PurchasedItem(String name, int quantity, long price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getPurchasedStatus() {
        DecimalFormat df = new DecimalFormat("###,###");
        return String.format("%-11s\t\t%-10d\t%-6s", name, quantity, df.format(price * quantity));
    }

    public String getFreeStatus() {
        return String.format("%-11s\t\t%d", name, quantity);
    }

    public boolean isSameName(PurchasedItem item) {
        return this.name.equals(item.name);
    }

    public void addQuantity(PurchasedItem item) {
        this.quantity += item.quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public long calcAmount() {
        return this.quantity * this.price;
    }
}