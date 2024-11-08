package store.model.entity;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import store.model.PromotionPurchaseQuantity;

public class PromotionProduct extends Product {

    private final Promotion promotion;

    public PromotionProduct(String name, long price, long quantity, Promotion promotion) {
        super(name, price, quantity);
        this.promotion = promotion;
    }

    public boolean expiredPromotion() {
        return !promotion.withinPeriod(LocalDate.from(DateTimes.now()));
    }

    public PromotionPurchaseQuantity getPurchaseStatus(long totalPurchaseQuantity) {
        long availableQuantity = calcAvailableQuantity(totalPurchaseQuantity);
        long discountedQuantity = calcDiscountedQuantity(availableQuantity);

        return new PromotionPurchaseQuantity(
                availableQuantity,
                isQuantityExceeded(totalPurchaseQuantity),
                totalPurchaseQuantity - discountedQuantity,
                discountedQuantity,
                calcFreeQuantity(availableQuantity));
    }

    private long calcAvailableQuantity(long quantity) {
        return Math.min(this.quantity, quantity);
    }

    private boolean isQuantityExceeded(long totalQuantity) {
        return this.quantity < totalQuantity;
    }

    private long calcFreeQuantity(long buyQuantity) {
        return promotion.calcFreeQuantity(buyQuantity);
    }

    private long calcDiscountedQuantity(long buyQuantity) {
        return promotion.calcCurAppliedQuantity(buyQuantity);
    }

    public boolean needOneMoreForPromotion(long unDiscountedQuantity, long buyQuantity) {
        if (++buyQuantity > this.quantity) {
            return false;
        }
        return promotion.promotionIfPurchaseOneMore(unDiscountedQuantity);
    }

    public void purchaseAll() {
        super.purchase(this.quantity);
    }

    public long calcUnDiscountedQuantity(long discountedQuantity) {
        return this.quantity - discountedQuantity;
    }

    @Override
    public String toString() {
        return super.toString() + " " + promotion;
    }
}
