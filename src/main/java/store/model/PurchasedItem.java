package store.model;

import java.text.DecimalFormat;

public class PurchasedItem {

    private final String name;
    private long quantity;
    private final long price;

    private PurchasedItem(String name, long quantity, long price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public static PurchasedItem create(String name, long buyQ, long price) {
        return new PurchasedItem(name, buyQ, price);
    }

    public static PurchasedItem createFree(String name, long freeQ) {
        return new PurchasedItem(name, freeQ, 0L);
    }

    public String getPurchasedStatus() {
        DecimalFormat df = new DecimalFormat("###,###");
        return String.format("%-11s\t\t%-10d\t%s", name, quantity, df.format(price * quantity));
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
}