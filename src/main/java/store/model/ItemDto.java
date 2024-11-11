package store.model;

import store.exception.BusinessException;
import store.exception.ErrorMessage;
import store.reader.parser.Parsable;
import store.repository.ProductQuantityRepository;

public class ItemDto implements Parsable {

    private final String name;
    private int quantity;

    public ItemDto(String name, int quantity) {
        validate(name, quantity);
        this.name = name;
        this.quantity = quantity;
    }

    private void validate(String name, int quantity) {
        validateQuantityIsPositive(quantity);
        Integer productQuantity = ProductQuantityRepository.getInstance().findByName(name);
        validateProductExists(productQuantity);
        validateQuantityIsWithinStock(quantity, productQuantity);
    }

    private void validateQuantityIsPositive(int quantity) {
        if (quantity <= 0) {
            throw new BusinessException(ErrorMessage.INVALID_INPUT);
        }
    }

    private void validateProductExists(Integer productQuantity) {
        if (productQuantity == null) {
            throw new BusinessException(ErrorMessage.NOTFOUND_PRODUCT);
        }
    }

    private void validateQuantityIsWithinStock(int quantity, Integer productQuantity) {
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

    public void subtractQuantity(final int quantity) {
        this.quantity -= quantity;
    }

    @Override
    public String toString() {
        return name;
    }
}
