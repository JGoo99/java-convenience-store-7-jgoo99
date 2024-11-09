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

    public PromotionPurchaseQuantity getPurchaseQuantityStatus(int totalPurchaseQuantity) {
        int availableQuantity = calcAvailableQuantity(totalPurchaseQuantity);
        int discountedQuantity = calcDiscountedQuantity(availableQuantity);

        return new PromotionPurchaseQuantity(
                availableQuantity,
                isQuantityExceeded(totalPurchaseQuantity),
                totalPurchaseQuantity - discountedQuantity,
                discountedQuantity,
                calcFreeQuantity(availableQuantity));
    }

    private int calcAvailableQuantity(int quantity) {
        return Math.min(this.quantity, quantity);
    }

    private boolean isQuantityExceeded(long totalQuantity) {
        return this.quantity < totalQuantity;
    }

    private int calcFreeQuantity(int purchaseQuantity) {
        return promotion.calcFreeQuantity(purchaseQuantity);
    }

    private int calcDiscountedQuantity(int purchaseQuantity) {
        return promotion.calcCurAppliedQuantity(purchaseQuantity);
    }

    public boolean needOneMoreForPromotion(int unDiscountedQuantity, int purchaseQuantity) {
        if (++purchaseQuantity > this.quantity) {
            return false;
        }
        return promotion.promotionIfPurchaseOneMore(unDiscountedQuantity);
    }

    public void purchaseAll() {
        super.purchase(this.quantity);
    }

    public long calcUnDiscountedAmount(int discountedQuantity) {
        return super.calcPayment(this.quantity - discountedQuantity);
    }

    @Override
    public String toString() {
        return super.toString() + " " + promotion;
    }
}
