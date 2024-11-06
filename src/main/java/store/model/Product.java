package store.model;

import java.text.DecimalFormat;
import java.time.LocalDate;
import store.exception.BusinessException;
import store.repository.PromotionRepository;

public class Product {

    private final String name;
    private final long price;
    private long quantity;
    private final Promotion promotion;

    public Product(String name, long price, long quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public Product(String name, long price, long quantity, String promotionName) {
        this(name, price, quantity, PromotionRepository.getInstance().findByName(promotionName));
    }

    public void validateAvailablePurchase(long quantity) {
        if (quantity <= 0) {
            throw new BusinessException("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
        if (this.quantity < quantity) {
            throw new BusinessException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public long buy(long quantity) {
        validateAvailablePurchase(quantity);
        this.quantity -= quantity;
        if (isAvailablePromotion(quantity)) {
            long freeQuantity = promotion.calcFreeQuantity(quantity);
            long paymentQuantity = quantity - freeQuantity;
            return price * paymentQuantity;
        }
        return price * quantity;
    }

    public String getQuantityStatus() {
        DecimalFormat df = new DecimalFormat("###,###");
        if (quantity == 0) {
            return "재고없음 ";
        }
        return df.format(quantity) + "개 ";
    }

    public boolean isAvailablePromotion(long quantity) {
        if (!promotion.overBuyCnt(quantity)) {
            return false;
        }
        return promotion.withinPeriod(LocalDate.now());
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("###,###");
        return name + " " +
                df.format(price) + "원 " +
                getQuantityStatus() +
                promotion;
    }
}
