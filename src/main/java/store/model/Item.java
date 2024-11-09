package store.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.exception.BusinessException;
import store.exception.ErrorMessage;
import store.repository.ProductQuantityRepository;

public class Item {

    private static final String REGEX = "\\[([가-힣]+)-([0-9]+)\\]";

    private final String name;
    private int quantity;

    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public static Item from(final String itemInput) {
        Matcher matcher = Pattern.compile(REGEX).matcher(itemInput);
        if (matcher.find()) {
            String productName = matcher.group(1);
            int quantity = Integer.parseInt(matcher.group(2));

            validate(productName, quantity);
            return new Item(productName, quantity);
        }
        throw new BusinessException(ErrorMessage.INVALID_INPUT);
    }

    private static void validate(final String productName, final int quantity) {
        if (quantity <= 0) {
            throw new BusinessException(ErrorMessage.INVALID_INPUT);
        }
        Integer productQuantity = ProductQuantityRepository.getInstance().findByName(productName);
        if (productQuantity == null) {
            throw new BusinessException(ErrorMessage.NOTFOUND_PRODUCT);
        }
        if (productQuantity < quantity) {
            throw new BusinessException(ErrorMessage.OVER_PRODUCT_QUANTITY);
        }
    }

    public void addOneMoreQuantity() {
        this.quantity++;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void pay(final int purchaseQuantity) {
        this.quantity -= purchaseQuantity;
    }

    public void subtractUnDiscountedQuantity(final int unDiscountedQuantity) {
        this.quantity -= unDiscountedQuantity;
    }

    @Override
    public String toString() {
        return name;
    }
}
