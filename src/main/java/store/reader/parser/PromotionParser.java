package store.reader.parser;

import static store.constants.ParseModelRegex.DATE;
import static store.constants.ParseModelRegex.NUMBER;
import static store.constants.ParseModelRegex.PROMOTION_NAME;

import java.time.LocalDate;
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

        return new Promotion(name, buyQuantity, getQuantity, start, end);
    }
}
