package store.model;

import java.util.ArrayList;
import java.util.List;

public class PosMachine {

    private final List<Item> items;
    private long totalPayment;
    private long promotionDiscount;
    private long membershipDiscount;

    public PosMachine() {
        this.items = new ArrayList<>();
    }
}
