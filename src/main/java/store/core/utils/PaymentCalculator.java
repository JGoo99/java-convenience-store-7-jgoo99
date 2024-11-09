package store.core.utils;

public class PaymentCalculator {

    private int totalQuantity = 0;
    private long totalAmount = 0L;
    private long freeAmount = 0L;
    private long unDiscountedAmount = 0L;
    private long membershipDiscountedAmount = 0L;

    public void addTotalQuantity(int quantity) {
        this.totalQuantity += quantity;
    }

    public void addTotalAmount(long amount) {
        this.totalAmount += amount;
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
