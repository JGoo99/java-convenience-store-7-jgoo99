package store.model;

public record PromotionPurchaseQuantity(int purchase, boolean isExceed, boolean requiredFullPriceForSome,
                                        int unDiscounted, int discounted, int free) {

}
