package store.model;

import store.core.utils.ReceiptFormater;

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
        return ReceiptFormater.buildPurchasedItemFormat(name, quantity, quantity * price);
    }

    public String getFreeStatus() {
        return ReceiptFormater.buildFreeItemFormat(name, quantity);
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