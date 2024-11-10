package store.repository;

import java.util.HashMap;
import java.util.Map;

public class ProductPromotionRepository {

    private static ProductPromotionRepository ProductPromotionRepository;

    private final Map<String, String> PRODUCT_PROMOTIONS = new HashMap<>();

    private ProductPromotionRepository() {
    }

    public static ProductPromotionRepository getInstance() {
        if (ProductPromotionRepository == null) {
            ProductPromotionRepository = new ProductPromotionRepository();
        }
        return ProductPromotionRepository;
    }

    public void save(String productName, String promotionName) {
        PRODUCT_PROMOTIONS.put(productName, promotionName);
    }

    public String findByName(String promotionName) {
        return PRODUCT_PROMOTIONS.get(promotionName);
    }

    public void clear() {
        PRODUCT_PROMOTIONS.clear();
    }
}
