package store.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Receipt {

    private final List<PurchasedItem> purchasedItems;
    private final List<PurchasedItem> freeItems;

    public Receipt() {
        this.purchasedItems = new ArrayList<>();
        this.freeItems = new ArrayList<>();
    }

    public void addPurchasedItem(PurchasedItem targetItem) {
        Optional<PurchasedItem> exist =
                purchasedItems.stream().filter(item -> item.isSameName(targetItem)).findFirst();
        if (exist.isEmpty()) {
            this.purchasedItems.add(targetItem);
            return;
        }
        exist.get().addQuantity(targetItem);
    }

    public void addFreeItem(PurchasedItem item) {
        this.freeItems.add(item);
    }

    public String printPurchasedItems() {
        StringBuffer sb = new StringBuffer();
        purchasedItems.forEach(item -> sb.append(item.getPurchasedStatus() + "\n"));
        purchasedItems.clear();
        return sb.toString();
    }

    public String printFreeItems() {
        StringBuffer sb = new StringBuffer();
        freeItems.forEach(item -> sb.append(item.getFreeStatus() + "\n"));
        freeItems.clear();
        return sb.toString();
    }
}
