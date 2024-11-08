package store.model;

public record PromotionPurchaseQuantity(long purchase, boolean isExceeded, long unDiscounted, long discounted, long free) {

    public long purchase() {
        return purchase;
    }

    public boolean isExceeded() {
        return isExceeded;
    }

    public long unDiscounted() {
        return unDiscounted;
    }

    public long discounted() {
        return discounted;
    }

    public long free() {
        return free;
    }
}
