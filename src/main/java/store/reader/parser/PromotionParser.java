package store.reader.parser;

import static store.constants.ParseModelRegex.DATE;
import static store.constants.ParseModelRegex.NUMBER;
import static store.constants.ParseModelRegex.PROMOTION_NAME;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import store.exception.BusinessException;
import store.exception.ErrorMessage;
import store.model.entity.Promotion;

public class PromotionParser extends LineParser<Promotion> {

    public PromotionParser(String line) {
        super(line);
    }

    @Override
    protected String getRegex() {
        return buildRegex(",", PROMOTION_NAME, NUMBER, NUMBER, DATE, DATE);
    }

    @Override
    public Promotion parse() {
        String name = matcher.group(1);
        int buyQuantity = Integer.parseInt(matcher.group(2));
        int getQuantity = Integer.parseInt(matcher.group(3));
        LocalDate start = LocalDate.parse(matcher.group(4));
        LocalDate end = LocalDate.parse(matcher.group(5));

        validateGetQuantity(getQuantity);
        validateDate(start, end);
        return new Promotion(name, buyQuantity, getQuantity, start, end);
    }

    private void validateDate(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new BusinessException(ErrorMessage.INVALID_FILE_VALUE);
        }
    }

    private void validateGetQuantity(int getQuantity) {
        if (getQuantity != 1) {
            throw new BusinessException(ErrorMessage.INVALID_FILE_VALUE);
        }
    }
}
