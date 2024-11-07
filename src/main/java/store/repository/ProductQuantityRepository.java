package store.repository;

import java.util.HashMap;
import java.util.Map;

public class ProductQuantityRepository {

    private static ProductQuantityRepository promotionRepository;

    private final Map<String, Long> PRODUCT_QUANTITIES = new HashMap<>();

    private ProductQuantityRepository() {
    }

    public static ProductQuantityRepository getInstance() {
        if (promotionRepository == null) {
            promotionRepository = new ProductQuantityRepository();
        }
        return promotionRepository;
    }

    public void save(String productName, long quantity) {
        long prevQuantity = PRODUCT_QUANTITIES.getOrDefault(productName, 0L);
        PRODUCT_QUANTITIES.put(productName, prevQuantity + quantity);
    }

    public Long findByName(String promotionName) {
        return PRODUCT_QUANTITIES.get(promotionName);
    }
}
