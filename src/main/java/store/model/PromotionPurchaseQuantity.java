package store.model;

public record PromotionPurchaseQuantity(int purchase, boolean isEqualOrMore, int unDiscounted, int discounted, int free) {

}
