package store.model;

import java.text.DecimalFormat;

public record PurchasedItem(String name, long buyQ, long price) {

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("###,###");
        return String.format("%-11s\t\t%-10d\t%s", name, buyQ, df.format(price * buyQ));
    }
}