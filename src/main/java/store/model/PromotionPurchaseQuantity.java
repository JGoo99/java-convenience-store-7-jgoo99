package store.model;

public record PromotionPurchaseQuantity(int purchase, boolean isExceeded, int unDiscounted, int discounted, int free) {
    
}
