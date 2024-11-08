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
        return new ProductParser(buildRegex(PRODUCT_NAME, LONG, LONG, PROMOTION_NAME), line);
    }

    public Product parse() {
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
}
