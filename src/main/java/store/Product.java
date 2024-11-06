package store;

import java.text.DecimalFormat;
import store.exception.BusinessException;

public class Product {
    private final String name;
    private final long price;
    private long quantity;
    private final String promotion;

    public Product(String name, long price, long quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
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

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("###,###");
        return name + " " +
                df.format(price) + "원 " +
                df.format(quantity) + "개 " +
                promotion;
    }
}
