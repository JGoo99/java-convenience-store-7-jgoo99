package store.model.entity;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import store.model.PromotionPurchaseQuantity;

public class PromotionProduct extends Product {

    private final Promotion promotion;

    public PromotionProduct(String name, long price, int quantity, Promotion promotion) {
        super(name, price, quantity);
        this.promotion = promotion;
    }

    public boolean expiredPromotion() {
        return !promotion.withinPeriod(LocalDate.from(DateTimes.now()));
    }

    public PromotionPurchaseQuantity getPurchaseQuantityStatus(final int totalPurchaseQuantity) {
        int availableQuantity = calcAvailableQuantity(totalPurchaseQuantity);
        int discountedQuantity = calcDiscountedQuantity(availableQuantity);

        return new PromotionPurchaseQuantity(
                availableQuantity,
                isQuantityExceeded(totalPurchaseQuantity),
                requiredFullPriceForSomeQuantities(availableQuantity, totalPurchaseQuantity),
                totalPurchaseQuantity - discountedQuantity,
                discountedQuantity,
                calcFreeQuantity(availableQuantity));
    }

    private int calcAvailableQuantity(final int quantity) {
        return Math.min(this.quantity, quantity);
    }

    private int calcDiscountedQuantity(final int purchaseQuantity) {
        if (expiredPromotion()) {
            return 0;
        }
        return promotion.calcCurAppliedQuantity(purchaseQuantity);
    }

    private boolean requiredFullPriceForSomeQuantities(final int purchaseQuantity, final int totalQuantity) {
        return isInsufficientPromotion(purchaseQuantity, totalQuantity) || promotion.calcFreeQuantity(purchaseQuantity) == 0;
    }

    private boolean isInsufficientPromotion(final int purchaseQuantity, final int totalQuantity) {
        return promotion.calcCurAppliedQuantity(purchaseQuantity) < totalQuantity;
    }

    private boolean isQuantityExceeded(final int totalQuantity) {
        return this.quantity < totalQuantity;
    }

    private int calcFreeQuantity(final int purchaseQuantity) {
        if (expiredPromotion()) {
            return 0;
        }
        return promotion.calcFreeQuantity(purchaseQuantity);
    }

    public boolean availableGetOneMoreForFree(final int unDiscountedQuantity, final int purchaseQuantity) {
        if (purchaseQuantity + 1 > this.quantity) {
            return false;
        }
        return promotion.availableGetOneMoreForFree(unDiscountedQuantity);
    }

    public void purchaseAll() {
        super.purchase(this.quantity);
    }

    @Override
    public String toString() {
        return super.toString() + " " + promotion;
    }
}
