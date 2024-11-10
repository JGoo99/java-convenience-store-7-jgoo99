package store.model;

import store.exception.BusinessException;
import store.exception.ErrorMessage;
import store.reader.parser.Parsable;
import store.repository.ProductQuantityRepository;

public class ItemDto implements Parsable {

    private final String name;
    private int quantity;

    public ItemDto(String name, int quantity) {
        validateQuantity(name, quantity);
        this.name = name;
        this.quantity = quantity;
    }

    private void validateQuantity(String name, int quantity) {
        if (quantity <= 0) {
            throw new BusinessException(ErrorMessage.INVALID_INPUT);
        }
        Integer productQuantity = ProductQuantityRepository.getInstance().findByName(name);
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

    public void subtractQuantity(final int quantity) {
        this.quantity -= quantity;
    }

    @Override
    public String toString() {
        return name;
    }
}
