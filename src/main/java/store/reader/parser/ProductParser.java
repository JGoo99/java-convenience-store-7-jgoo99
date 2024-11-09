package store.reader.parser;

import static store.constants.MdFileRegex.LONG;
import static store.constants.MdFileRegex.PRODUCT_NAME;
import static store.constants.MdFileRegex.PROMOTION_NAME;

import store.model.entity.Product;
import store.model.entity.Promotion;
import store.model.entity.PromotionProduct;
import store.repository.ProductQuantityRepository;
import store.repository.PromotionRepository;

public class ProductParser extends LineParser {

    private ProductParser(String regex, String line) {
        super(regex, line);
    }

    public static ProductParser read(String line) {
        return new ProductParser(line, buildRegex(
                PRODUCT_NAME, LONG, LONG, PROMOTION_NAME));
    }

    public Product parse() {
        validate();
        String name = matcher.group(1);
        long price = Long.parseLong(matcher.group(2));
        int quantity = Integer.parseInt(matcher.group(3));
        String promotionName = matcher.group(4);

        ProductQuantityRepository.getInstance().save(name, quantity);
        return parse(name, price, quantity, promotionName);
    }

    private Product parse(String name, final long price, final int quantity, String promotionName) {
        Promotion promotion = PromotionRepository.getInstance().findByName(promotionName);
        if (isPromotion(promotion)) {
            return new PromotionProduct(name, price, quantity, promotion);
        }
        return new Product(name, price, quantity);
    }

    private boolean isPromotion(Promotion promotion) {
        return promotion != null;
    }
}
