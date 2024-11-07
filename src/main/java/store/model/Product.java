package store.model;

import java.text.DecimalFormat;
import store.exception.BusinessException;

public class Product {

    private final String name;
    private final long price;
    long quantity;

    public Product(String name, long price, long quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void validateAvailablePurchase(long quantity) {
        validateNegative(quantity);
    }

    public void validateNegative(long quantity) {
        if (quantity <= 0) {
            throw new BusinessException("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public long buy(long quantity) {
        this.quantity -= quantity;
        return quantity;
    }

    public String getQuantityStatus() {
        DecimalFormat df = new DecimalFormat("###,###");
        if (quantity == 0) {
            return "재고 없음";
        }
        return df.format(quantity) + "개";
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("###,###");
        return name + " " +
                df.format(price) + "원 " +
                getQuantityStatus();
    }
}
