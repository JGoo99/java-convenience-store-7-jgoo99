package store.reader;

import static store.constants.MdFileRegex.DATE;
import static store.constants.MdFileRegex.LONG;
import static store.constants.MdFileRegex.PRODUCT_NAME;
import static store.constants.MdFileRegex.PROMOTION_NAME;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import store.constants.MdFileRegex;
import store.exception.BusinessException;
import store.exception.ErrorMessage;
import store.model.Product;
import store.model.Promotion;
import store.model.PromotionProduct;
import store.repository.ProductQuantityRepository;
import store.repository.PromotionRepository;

public class PromotionParser extends LineParser {

    protected PromotionParser(String regex, String line) {
        super(regex, line);
    }

    public static PromotionParser read(String line) {
        return new PromotionParser(buildRegex(PROMOTION_NAME, LONG, LONG, DATE, DATE), line);
    }

    public Promotion parse() {
        validate();
        String name = matcher.group(1);
        int buyQuantity = Integer.parseInt(matcher.group(2));
        int getQuantity = Integer.parseInt(matcher.group(3));
        LocalDate start = LocalDate.parse(matcher.group(4));
        LocalDate end = LocalDate.parse(matcher.group(5));

        return new Promotion(name, buyQuantity, getQuantity, start, end);
    }
}
