package store.model;

public record PromotionPurchaseQuantity(int purchase, boolean isExceeded, int unDiscounted, int discounted, int free) {

    public int purchase() {
        return purchase;
    }

    public boolean isExceeded() {
        return isExceeded;
    }

    public int unDiscounted() {
        return unDiscounted;
    }

    public int discounted() {
        return discounted;
    }

    public int free() {
        return free;
    }
}
