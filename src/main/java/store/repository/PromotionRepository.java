package store.repository;

import java.util.HashMap;
import java.util.Map;
import store.model.entity.Promotion;

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
        PROMOTIONS.put(promotion.getName(), promotion);
        return promotion;
    }

    public Promotion findByName(String promotionName) {
        return PROMOTIONS.get(promotionName);
    }

    public void clear() {
        PROMOTIONS.clear();
    }
}
