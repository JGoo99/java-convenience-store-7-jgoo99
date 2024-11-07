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
import store.model.Product;
import store.model.Promotion;
import store.model.PromotionProduct;
import store.repository.ProductQuantityRepository;
import store.repository.PromotionRepository;

public class LineParser {

    private final Matcher matcher;

    private LineParser(String regex, String line) {
        this.matcher = Pattern.compile("^" + regex + "$").matcher(line);
    }

    public static LineParser withProductRegex(String line) {
        return new LineParser(buildRegex(PRODUCT_NAME, LONG, LONG, PROMOTION_NAME), line);
    }

    public static LineParser withPromotionRegex(String line) {
        return new LineParser(buildRegex(PROMOTION_NAME, LONG, LONG, DATE, DATE), line);
    }

    private static String buildRegex(MdFileRegex... regexes) {
        return Stream.of(regexes)
                .map(MdFileRegex::toString)
                .collect(Collectors.joining(","));
    }

    public Promotion toPromotion() {
        validate();
        String name = matcher.group(1);
        int buyQuantity = Integer.parseInt(matcher.group(2));
        int getQuantity = Integer.parseInt(matcher.group(3));
        LocalDate start = LocalDate.parse(matcher.group(4));
        LocalDate end = LocalDate.parse(matcher.group(5));

        return new Promotion(name, buyQuantity, getQuantity, start, end);
    }

    public Product toProduct() {
        validate();
        String name = matcher.group(1);
        long price = Long.parseLong(matcher.group(2));
        long quantity = Long.parseLong(matcher.group(3));
        String promotionName = matcher.group(4);

        ProductQuantityRepository.getInstance().save(name, quantity);
        Promotion promotion = PromotionRepository.getInstance().findByName(promotionName);
        if (promotion == null) {
            return new Product(name, price, quantity);
        }
        return new PromotionProduct(name, price, quantity, promotion);
    }

    private void validate() {
        if (!matcher.matches()) {
            throw new BusinessException("잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }
}
