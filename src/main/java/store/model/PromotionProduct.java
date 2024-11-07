package store.model;

import java.time.LocalDate;

public class PromotionProduct extends Product {

    private final Promotion promotion;

    public PromotionProduct(String name, long price, long quantity, Promotion promotion) {
        super(name, price, quantity);
        this.promotion = promotion;
    }

    @Override
    public long buy(long quantity) {
        validateNegative(quantity);
        long totalBuyQuantity = promotion.calcQuantity(Math.min(quantity, this.quantity));
        if (quantity > this.quantity) {
            totalBuyQuantity = this.quantity;
        }
        this.quantity -= totalBuyQuantity;
        return totalBuyQuantity;
    }

    public boolean isAvailablePromotion(long quantity) {
        if (promotion.lessThanBuyCnt(quantity)) {
            return false;
        }
        return promotion.withinPeriod(LocalDate.now());
    }

    public boolean needOneMorePromotionQuantity(long quantity) {
        return isAvailablePromotion(quantity) && promotion.needOneMoreQuantity(quantity) && super.quantity >= 1;
    }

    private String getPromotionStatus() {
        if (promotion == null) {
            return "";
        }
        return promotion.toString();
    }

    @Override
    public String toString() {
        return super.toString() + " " +
                getPromotionStatus();
    }
}
