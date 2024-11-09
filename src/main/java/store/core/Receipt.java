package store.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.core.utils.PaymentCalculator;
import store.model.Item;
import store.core.utils.ReceiptWriter;

public class Receipt {

    private final List<Item> items;
    private final List<Item> freeItems;
    private final PaymentCalculator paymentCalculator;
    private final ReceiptWriter writer;

    public Receipt() {
        this.items = new ArrayList<>();
        this.freeItems = new ArrayList<>();
        this.paymentCalculator = new PaymentCalculator();
        this.writer = new ReceiptWriter(paymentCalculator);
    }

    public void addItem(Item item) {
        paymentCalculator.addItem(item);
        Optional<Item> exist =
                items.stream().filter(registeredItem -> registeredItem.isSameName(item)).findFirst();
        if (exist.isEmpty()) {
            this.items.add(item);
            return;
        }
        exist.get().addQuantity(item);
    }

    public void addFreeItem(Item item) {
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
        return writer.run(items, freeItems);
    }
}
