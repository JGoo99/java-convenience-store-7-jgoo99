package store.core.utils;

import store.model.Item;

public class PaymentCalculator {

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
        this.membershipDiscountedAmount = (unDiscountedAmount / 100) * 30;
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
