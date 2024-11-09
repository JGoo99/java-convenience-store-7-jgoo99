package store.model.entity;

import java.text.DecimalFormat;
import store.exception.BusinessException;
import store.exception.ErrorMessage;

public class Product implements ConvenienceEntity {

    protected final String name;
    protected final long price;
    protected int quantity;

    public Product(String name, long price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void purchase(int buyQuantity) {
        this.quantity -= buyQuantity;
    }

    public boolean isSoldOut() {
        return this.quantity == 0;
    }

    public void validateAvailablePurchase(int quantity) {
        validateNegative(quantity);
    }

    protected void validateNegative(int quantity) {
        if (quantity <= 0) {
            throw new BusinessException(ErrorMessage.INVALID_INPUT);
        }
    }

    public String getQuantityStatus() {
        DecimalFormat df = new DecimalFormat("###,###");
        if (quantity == 0) {
            return "재고 없음";
        }
        return df.format(quantity) + "개";
    }

    public long getPrice() {
        return price;
    }

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    public long calcPayment(int buyQuantity) {
        return this.price * buyQuantity;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("###,###");
        return name + " " +
                df.format(price) + "원 " +
                getQuantityStatus();
    }
}
