package store.model;

import store.reader.parser.Parsable;

public class ItemDto implements Parsable {

    private final String name;
    private int quantity;

    public ItemDto(String name, int quantity) {
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

    public void subtractQuantity(final int quantity) {
        this.quantity -= quantity;
    }

    @Override
    public String toString() {
        return name;
    }
}
