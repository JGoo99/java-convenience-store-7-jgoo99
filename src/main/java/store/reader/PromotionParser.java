package store.reader;

import static store.constants.PromotionParserRegex.DATE;
import static store.constants.PromotionParserRegex.NAME;
import static store.constants.PromotionParserRegex.QUANTITY;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import store.constants.PromotionParserRegex;
import store.exception.BusinessException;
import store.model.Promotion;

public class PromotionParser {

    private static final String PROMOTION_REGEX = Stream.of(NAME, QUANTITY, QUANTITY, DATE, DATE)
            .map(PromotionParserRegex::toString)
            .collect(Collectors.joining(","));

    private static final Pattern PROMOTION_PATTER = Pattern.compile("^" + PROMOTION_REGEX + "$");

    public static Promotion parse(String line) {
        Matcher matcher = PROMOTION_PATTER.matcher(line);
        if (!matcher.matches()) {
            throw new BusinessException("잘못된 입력입니다. 다시 입력해 주세요.");
        }

        String name = matcher.group(1);
        int buyQuantity = Integer.parseInt(matcher.group(2));
        int getQuantity = Integer.parseInt(matcher.group(3));
        LocalDate start = LocalDate.parse(matcher.group(4));
        LocalDate end = LocalDate.parse(matcher.group(5));

        return new Promotion(name, buyQuantity, getQuantity, start, end);
    }
}
