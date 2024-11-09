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
                totalPurchaseQuantity - discountedQuantity,
                discountedQuantity,
                calcFreeQuantity(availableQuantity));
    }

    private int calcAvailableQuantity(final int quantity) {
        return Math.min(this.quantity, quantity);
    }

    private boolean isQuantityExceeded(final long totalQuantity) {
        return this.quantity < totalQuantity;
    }

    private int calcFreeQuantity(final int purchaseQuantity) {
        return promotion.calcFreeQuantity(purchaseQuantity);
    }

    private int calcDiscountedQuantity(final int purchaseQuantity) {
        return promotion.calcCurAppliedQuantity(purchaseQuantity);
    }

    public boolean needOneMoreForPromotion(final int unDiscountedQuantity, final int purchaseQuantity) {
        if (purchaseQuantity + 1 > this.quantity) {
            return false;
        }
        return promotion.promotionIfPurchaseOneMore(unDiscountedQuantity);
    }

    public void purchaseAll() {
        super.purchase(this.quantity);
    }

    public long calcUnDiscountedAmount(final int discountedQuantity) {
        return super.calcPayment(this.quantity - discountedQuantity);
    }

    @Override
    public String toString() {
        return super.toString() + " " + promotion;
    }
}
