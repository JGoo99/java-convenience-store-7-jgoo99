package store.reader.parser;

import static store.constants.ParseModelRegex.NUMBER;
import static store.constants.ParseModelRegex.KOREAN;
import static store.constants.ParseModelRegex.PROMOTION_NAME;

import store.exception.BusinessException;
import store.exception.ErrorMessage;
import store.model.entity.Product;
import store.model.entity.Promotion;
import store.model.entity.PromotionProduct;
import store.repository.ProductPromotionRepository;
import store.repository.ProductQuantityRepository;
import store.repository.PromotionRepository;

public class ProductParser extends LineParser<Product> {

    public ProductParser(String line) {
        super(line);
    }

    @Override
    protected String getRegex() {
        return buildRegex(",", KOREAN, NUMBER, NUMBER, PROMOTION_NAME);
    }

    @Override
    public Product parse() {
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
            String existPromotionName = ProductPromotionRepository.getInstance().findByName(name);
            if (existPromotionName != null) {
                throw new BusinessException(ErrorMessage.INVALID_FILE_VALUE);
            }
            ProductPromotionRepository.getInstance().save(name, promotionName);
            return new PromotionProduct(name, price, quantity, promotion);
        }
        return new Product(name, price, quantity);
    }

    private boolean isPromotion(Promotion promotion) {
        return promotion != null;
    }
}
