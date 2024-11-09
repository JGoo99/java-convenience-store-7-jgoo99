package store.core;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.model.PurchasedItem;

public class Receipt {

    private static final StringBuffer BUFFER = new StringBuffer();

    private final List<PurchasedItem> purchasedItems;
    private final List<PurchasedItem> freeItems;
    private long totalCnt = 0L;
    private long totalAmount = 0L;
    private long freeAmount = 0L;
    private long unAppliedAmount = 0L;
    private long membershipDiscountedAmount = 0L;

    public Receipt() {
        this.purchasedItems = new ArrayList<>();
        this.freeItems = new ArrayList<>();
    }

    private StringBuffer getBuffer() {
        BUFFER.setLength(0);
        return BUFFER;
    }

    public void addPurchasedItem(PurchasedItem purchasedItem) {
        this.totalCnt += purchasedItem.getQuantity();
        this.totalAmount += purchasedItem.calcAmount();

        Optional<PurchasedItem> exist =
                purchasedItems.stream().filter(item -> item.isSameName(purchasedItem)).findFirst();
        if (exist.isEmpty()) {
            this.purchasedItems.add(purchasedItem);
            return;
        }
        exist.get().addQuantity(purchasedItem);
    }

    public void addFreeItem(PurchasedItem item) {
        this.freeAmount += item.calcAmount();
        this.freeItems.add(item);
    }

    public String printPurchasedItems() {
        StringBuffer sb = getBuffer();
        purchasedItems.forEach(item -> sb.append(item.getPurchasedStatus() + "\n"));
        return sb.toString();
    }

    public String printFreeItems() {
        StringBuffer sb = getBuffer();
        freeItems.forEach(item -> sb.append(item.getFreeStatus() + "\n"));
        return sb.toString();
    }

    public void addUnDiscountedAmount(long amount) {
        this.unAppliedAmount += amount;
    }

    public void membershipDiscount() {
        this.membershipDiscountedAmount = (unAppliedAmount / 100) * 30;
    }

    public String printTotalAmount() {
        DecimalFormat df = new DecimalFormat("###,###");
        return String.format("%-11s\t\t%-10d\t%s", "총구매액", totalCnt, df.format(totalAmount));
    }

    public String printFreeAmount() {
        DecimalFormat df = new DecimalFormat("-###,###");
        return String.format("%-11s\t\t\t\t\t%s", "행사할인", df.format(freeAmount));
    }

    public String printMembershipDiscountedAmount() {
        DecimalFormat df = new DecimalFormat("-###,###");
        return String.format("%-11s\t\t\t\t\t%s", "멤버십할인", df.format(membershipDiscountedAmount));
    }

    public String printTotalPayment() {
        DecimalFormat df = new DecimalFormat("###,###");
        return String.format("%-11s\t\t\t\t\t%s", "내실돈", df.format(totalAmount - (freeAmount + membershipDiscountedAmount)));
    }
}