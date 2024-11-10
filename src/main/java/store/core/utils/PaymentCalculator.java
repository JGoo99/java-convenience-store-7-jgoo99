package store.core.utils;

import store.model.Item;

public class PaymentCalculator {

    public static final long MEMBERSHIP_DISCOUNT_MAX_AMOUNT = 8000L;

    private int totalQuantity;
    private long totalAmount;
    private long freeAmount;
    private long unDiscountedAmount;
    private long membershipDiscountedAmount;

    public void addItem(Item item) {
        this.totalQuantity = item.calcQuantityAdditionWith(this.totalQuantity);
        this.totalAmount += item.calcAmount();
    }

    public void addFreeAmount(long amount) {
        this.freeAmount += amount;
    }

    public void addUnDiscountedAmount(long amount) {
        this.unDiscountedAmount += amount;
    }

    public void calcMembershipDiscountedAmount() {
        long discountedAmount = (unDiscountedAmount / 100) * 30;
        this.membershipDiscountedAmount = applyMembershipMaxDiscountAmount(discountedAmount);
    }

    private long applyMembershipMaxDiscountAmount(long discountedAmount) {
        return Math.min(MEMBERSHIP_DISCOUNT_MAX_AMOUNT, discountedAmount);
    }

    public long calcTotalPayment() {
        return totalAmount - (freeAmount + membershipDiscountedAmount);
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public long getFreeAmount() {
        return freeAmount;
    }

    public long getMembershipDiscountedAmount() {
        return membershipDiscountedAmount;
    }

}
