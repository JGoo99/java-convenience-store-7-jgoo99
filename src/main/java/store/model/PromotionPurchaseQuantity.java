package store.model;

public record PromotionPurchaseQuantity(int purchase, boolean requiredFullPriceForSomeQuantities,
                                        int unDiscounted, int discounted, int free) {

}
