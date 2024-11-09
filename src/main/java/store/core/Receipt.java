package store.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.core.utils.PaymentCalculator;
import store.model.PurchasedItem;
import store.core.utils.ReceiptWriter;

public class Receipt {

    private final List<PurchasedItem> purchasedItems;
    private final List<PurchasedItem> freeItems;
    private final PaymentCalculator paymentCalculator;
    private final ReceiptWriter writer;

    public Receipt() {
        this.purchasedItems = new ArrayList<>();
        this.freeItems = new ArrayList<>();
        this.paymentCalculator = new PaymentCalculator();
        this.writer = new ReceiptWriter(paymentCalculator);
    }

    public void addPurchasedItem(PurchasedItem purchasedItem) {
        paymentCalculator.addTotalQuantity(purchasedItem.getQuantity());
        paymentCalculator.addTotalAmount(purchasedItem.calcAmount());
        Optional<PurchasedItem> exist =
                purchasedItems.stream().filter(item -> item.isSameName(purchasedItem)).findFirst();
        if (exist.isEmpty()) {
            this.purchasedItems.add(purchasedItem);
            return;
        }
        exist.get().addQuantity(purchasedItem);
    }

    public void addFreeItem(PurchasedItem item) {
        paymentCalculator.addFreeAmount(item.calcAmount());
        this.freeItems.add(item);
    }

    public void addUnDiscountedAmount(final long amount) {
        paymentCalculator.addUnDiscountedAmount(amount);
    }

    public void membershipDiscount() {
        paymentCalculator.calcMembershipDiscountedAmount();
    }

    public String write() {
        return writer.run(purchasedItems, freeItems);
    }
}
