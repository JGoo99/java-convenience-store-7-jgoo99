package store.model;

import store.reader.parser.Parsable;

public class Item implements Parsable {

    private final String name;
    private int quantity;

    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void addOneMoreQuantity() {
        this.quantity++;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void pay(final int purchaseQuantity) {
        this.quantity -= purchaseQuantity;
    }

    public void subtractUnDiscountedQuantity(final int unDiscountedQuantity) {
        this.quantity -= unDiscountedQuantity;
    }

    @Override
    public String toString() {
        return name;
    }
}
