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
        int unDiscountedQuantity = totalPurchaseQuantity - discountedQuantity;
        return new PromotionPurchaseQuantity(
                availableQuantity,
                isOutOfStockToApplyPromotion(totalPurchaseQuantity, unDiscountedQuantity),
                unDiscountedQuantity,
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

    private boolean isOutOfStockToApplyPromotion(final int totalQuantity, final int unDiscountedQuantity) {
        return isQuantityExceeded(totalQuantity) || cantTackFreeBecauseOfOutOfStock(totalQuantity, unDiscountedQuantity);
    }

    private boolean cantTackFreeBecauseOfOutOfStock(int totalQuantity, int unDiscountedQuantity) {
        return isMeetTheBuyQuantity(unDiscountedQuantity) && isQuantityExceeded(totalQuantity + 1);
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
        return isMeetTheBuyQuantity(unDiscountedQuantity);
    }

    public boolean isMeetTheBuyQuantity(int unDiscountedQuantity) {
        return promotion.isMeetTheBuyQuantity(unDiscountedQuantity);
    }

    public void purchaseAll() {
        super.purchase(this.quantity);
    }

    @Override
    public String toString() {
        return super.toString() + " " + promotion;
    }
}
