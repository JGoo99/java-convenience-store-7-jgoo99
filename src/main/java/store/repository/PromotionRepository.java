package store.repository;

import java.util.HashMap;
import java.util.List;
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

    public void saveAll(List<Promotion> promotions) {
        promotions.forEach(this::save);
    }

    public Promotion save(Promotion promotion) {
        return PROMOTIONS.put(promotion.getName(), promotion);
    }

    public Promotion findByName(String promotionName) {
        return PROMOTIONS.get(promotionName);
    }

    public void clear() {
        PROMOTIONS.clear();
    }
}
