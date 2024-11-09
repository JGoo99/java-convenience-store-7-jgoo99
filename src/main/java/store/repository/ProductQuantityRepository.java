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

    public void save(String name, long quantity) {
        long prevQuantity = PRODUCT_QUANTITIES.getOrDefault(name, 0L);
        PRODUCT_QUANTITIES.put(name, prevQuantity + quantity);
    }

    public void update(String name, long quantity) {
        PRODUCT_QUANTITIES.put(name, findByName(name) - quantity);
    }

    public Long findByName(String promotionName) {
        return PRODUCT_QUANTITIES.get(promotionName);
    }

    public void clear() {
        PRODUCT_QUANTITIES.clear();
    }
}
