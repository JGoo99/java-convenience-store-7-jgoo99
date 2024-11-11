package store.model;

import store.core.utils.ReceiptFormater;

public class Item {

    private final String name;
    private int quantity;
    private final long price;

    public Item(String name, int quantity, long price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String parseReceiptLineOfPurchased() {
        return ReceiptFormater.buildItemLine(name, quantity, quantity * price);
    }

    public String parseReceiptLineOfFree() {
        return ReceiptFormater.buildFreeItemLine(name, quantity);
    }

    public boolean isSameName(Item item) {
        return this.name.equals(item.name);
    }

    public void addQuantity(Item item) {
        this.quantity += item.quantity;
    }

    public int calcQuantityAdditionWith(int quantity) {
        return this.quantity + quantity;
    }

    public long calcAmount() {
        return this.quantity * this.price;
    }

    public boolean isQuantityGreaterThanZero() {
        return this.quantity > 0;
    }
}