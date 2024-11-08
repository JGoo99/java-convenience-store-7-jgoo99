package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;

public class PromotionProduct extends Product {

    private final Promotion promotion;

    public PromotionProduct(String name, long price, long quantity, Promotion promotion) {
        super(name, price, quantity);
        this.promotion = promotion;
    }

    public boolean expiredPromotion() {
        return !promotion.withinPeriod(LocalDate.from(DateTimes.now()));
    }

    public PromotionPurchaseStatus getPurchaseStatus(long totalQ) {
        long buyQ = getBuyQ(totalQ);
        long appliedQ = calcCurAppliedQuantity(buyQ);

        return new PromotionPurchaseStatus(
                buyQ, overQuantity(totalQ), totalQ - appliedQ, appliedQ, calcFreeQuantity(buyQ));
    }

    private long getBuyQ(long quantity) {
        return Math.min(quantity, this.quantity);
    }

    private boolean overQuantity(long totalQ) {
        return this.quantity < totalQ;
    }

    private long calcFreeQuantity(long buyQ) {
        return promotion.calcFreeQuantity(buyQ);
    }

    private long calcCurAppliedQuantity(long buyQ) {
        return promotion.calcCurAppliedQuantity(buyQ);
    }

    @Override
    public String toString() {
        return super.toString() + " " + promotion;
    }

    public boolean needMoreQuantity(long unAppliedQ, long buyQ) {
        if (++buyQ > this.quantity) {
            return false;
        }
        return promotion.appliableIfOneMore(unAppliedQ);
    }

    public long clear() {
        long prev = this.quantity;
        this.quantity = 0;
        return prev;
    }

    public long calcUnAppliedRemain(long appliedQ) {
        return this.quantity - appliedQ;
    }
}
