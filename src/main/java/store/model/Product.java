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

    public boolean isAvailablePurchase(long quantity) {
        if (quantity <= 0) {
            throw new BusinessException("구매 수량은 0이하일 수 없습니다.");
        }
        return this.quantity >= quantity;
    }

    public long buy(long quantity) {
        if (isAvailablePurchase(quantity)) {
            return this.quantity = this.quantity - quantity;
        }
        throw new BusinessException("재고가 부족하여 구매할 수 없습니다.");
    }

    public String getQuantityStatus() {
        DecimalFormat df = new DecimalFormat("###,###");
        if (quantity == 0) {
            return "재고없음 ";
        }
        return df.format(quantity) + "개 ";
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("###,###");
        return name + " " +
                df.format(price) + "원 " +
                getQuantityStatus() +
                promotion;
    }

    public boolean isAvailablePromotion() {
        return promotion.withinPeriod(LocalDate.now());
    }
}
