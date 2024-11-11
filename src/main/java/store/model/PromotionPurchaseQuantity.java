package store.model;

public record PromotionPurchaseQuantity(int purchase, boolean isExceed, int unDiscounted, int discounted, int free) {

}
