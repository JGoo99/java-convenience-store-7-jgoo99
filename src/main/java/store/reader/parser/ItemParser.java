package store.reader.parser;

import static store.constants.ParseModelRegex.CLOSED_SQUARE_BRACKET;
import static store.constants.ParseModelRegex.DASH;
import static store.constants.ParseModelRegex.NUMBER;
import static store.constants.ParseModelRegex.OPENED_SQUARE_BRACKET;
import static store.constants.ParseModelRegex.KOREAN;

import java.util.HashMap;
import java.util.Map;
import store.exception.BusinessException;
import store.exception.ErrorMessage;
import store.model.ItemDto;
import store.repository.ProductQuantityRepository;

public class ItemParser extends LineParser<ItemDto> {

    public ItemParser(String line) {
        super(line);
    }

    @Override
    protected String getRegex() {
        return buildRegex("", OPENED_SQUARE_BRACKET, KOREAN, DASH, NUMBER, CLOSED_SQUARE_BRACKET);
    }

    @Override
    public ItemDto parse() {
        String productName = matcher.group(1);
        int quantity = Integer.parseInt(matcher.group(2));
        return new ItemDto(productName, quantity);
    }

    @Override
    protected void validate() {
        if (!matcher.matches()) {
            throw new BusinessException(ErrorMessage.INVALID_INPUT);
        }
    }
}
