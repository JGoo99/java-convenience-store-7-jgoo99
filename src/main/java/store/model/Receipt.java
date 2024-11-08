package store.model;

import java.util.ArrayList;
import java.util.List;

public class Receipt {

    private final List<PurchasedItem> purchasedItems;

    public Receipt() {
        this.purchasedItems = new ArrayList<>();
    }

    public void addPurchasedItem(PurchasedItem item) {
        this.purchasedItems.add(item);
    }

    public String printPurchasedItems() {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%-11s\t\t%-10s\t%s\n", "상품명", "수량", "금액"));
        purchasedItems.forEach(item -> sb.append(item + "\n"));
        return sb.toString();
    }
}
