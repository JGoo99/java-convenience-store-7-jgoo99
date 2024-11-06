package store.repository;

import java.util.HashMap;
import java.util.Map;
import store.model.Promotion;

public class PromotionRepository {

    private static PromotionRepository promotionRepository;

    private final Map<String, Promotion> PROMOTIONS = new HashMap<>();

    private PromotionRepository() {
    }

    public static PromotionRepository getInstance() {
        if (promotionRepository == null) {
            promotionRepository = new PromotionRepository();
        }
        return promotionRepository;
    }

    public Promotion save(Promotion promotion) {
        return PROMOTIONS.put(promotion.getName(), promotion);
    }

    public Promotion findByName(String promotionName) {
        return PROMOTIONS.get(promotionName);
    }
}
