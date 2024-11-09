package store.reader.parser;

import static store.constants.ParseModelRegex.CLOSED_SQUARE_BRACKET;
import static store.constants.ParseModelRegex.DASH;
import static store.constants.ParseModelRegex.LONG;
import static store.constants.ParseModelRegex.OPENED_SQUARE_BRACKET;
import static store.constants.ParseModelRegex.PRODUCT_NAME;

import store.exception.BusinessException;
import store.exception.ErrorMessage;
import store.model.Item;
import store.repository.ProductQuantityRepository;

public class ItemParser extends LineParser<Item> {

    public ItemParser(String line) {
        super(line);
    }

    @Override
    protected String getRegex() {
        return buildRegex("", OPENED_SQUARE_BRACKET, PRODUCT_NAME, DASH, LONG, CLOSED_SQUARE_BRACKET);
    }

    @Override
    public Item parse() {
        String productName = matcher.group(1);
        int quantity = Integer.parseInt(matcher.group(2));
        validateItem(productName, quantity);
        return new Item(productName, quantity);
    }

    private static void validateItem(String productName, final int quantity) {
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

    @Override
    protected void validate() {
        if (!matcher.matches()) {
            throw new BusinessException(ErrorMessage.INVALID_INPUT);
        }
    }
}
