package store.model;

import java.text.DecimalFormat;
import store.exception.BusinessException;
import store.exception.ErrorMessage;

public class Product {

    protected final String name;
    protected final long price;
    protected long quantity;

    public Product(String name, long price, long quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void purchase(long buyQ) {
        this.quantity -= buyQ;
    }

    public boolean isSoldOut() {
        return this.quantity == 0;
    }

    public void validateAvailablePurchase(long quantity) {
        validateNegative(quantity);
    }

    protected void validateNegative(long quantity) {
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

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    public long calcPayment(long buyQ) {
        return this.price * buyQ;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("###,###");
        return name + " " +
                df.format(price) + "원 " +
                getQuantityStatus();
    }
}
