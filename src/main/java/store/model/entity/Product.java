package store.model.entity;

import java.text.DecimalFormat;
import store.constants.ViewLine;
import store.model.Item;
import store.reader.parser.Parsable;

public class Product implements ConvenienceEntity, Parsable {

    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("###,###");

    protected final String name;
    protected final long price;
    protected int quantity;

    public Product(String name, long price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void purchase(final int purchaseQuantity) {
        this.quantity -= purchaseQuantity;
    }

    public boolean isSoldOut() {
        return this.quantity == 0;
    }

    public boolean isSameName(final String name) {
        return this.name.equals(name);
    }

    public Item parseOf(final int purchaseQuantity) {
        return new Item(name, purchaseQuantity, price);
    }

    public long calcPayment(final int buyQuantity) {
        return this.price * buyQuantity;
    }

    public String getQuantityStatus() {
        if (quantity == 0) {
            return ViewLine.NONE_QUANTITY.toString();
        }
        return PRICE_FORMAT.format(quantity) + ViewLine.QUANTITY_UNIT;
    }

    @Override
    public String toString() {
        return name + " " +
                PRICE_FORMAT.format(price) + ViewLine.PRICE_UNIT +
                getQuantityStatus();
    }
}
