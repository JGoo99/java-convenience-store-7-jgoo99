package store.reader.parser;

import static store.constants.ParseModelRegex.COMMA;
import static store.constants.ParseModelRegex.NAME;
import static store.constants.ParseModelRegex.NUMBER;

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
        return buildRegex(COMMA.toString(), NAME, NUMBER, NUMBER, NAME);
    }

    @Override
    public Product parse() {
        String name = matcher.group(1);
        long price = Long.parseLong(matcher.group(2));
        int quantity = Integer.parseInt(matcher.group(3));
        String promotionName = getPromotionName();

        ProductQuantityRepository.getInstance().save(name, quantity);
        return parse(name, price, quantity, promotionName);
    }

    private String getPromotionName() {
        String promotionName = matcher.group(4);
        if (promotionName.equals("null")) {
            return null;
        }
        return promotionName;
    }

    private Product parse(String name, final long price, final int quantity, String promotionName) {
        Promotion promotion = PromotionRepository.getInstance().findByName(promotionName);
        if (isPromotion(promotion)) {
            validateOnePromotionPerOneProduct(name, promotionName);
            return new PromotionProduct(name, price, quantity, promotion);
        }
        validatePromotionNameIsNull(promotionName);
        return new Product(name, price, quantity);
    }

    private boolean isPromotion(Promotion promotion) {
        return promotion != null;
    }

    private void validateOnePromotionPerOneProduct(String name, String promotionName) {
        ProductPromotionRepository repository = ProductPromotionRepository.getInstance();
        String existPromotionName = repository.findByName(name);
        if (isPromotionDuplicated(existPromotionName)) {
            throw new BusinessException(ErrorMessage.INVALID_FILE_VALUE);
        }
        repository.save(name, promotionName);
    }

    private void validatePromotionNameIsNull(String promotionName) {
        if (promotionName != null) {
            throw new BusinessException(ErrorMessage.INVALID_FILE_VALUE);
        }
    }

    private static boolean isPromotionDuplicated(String existPromotionName) {
        return existPromotionName != null;
    }
}
