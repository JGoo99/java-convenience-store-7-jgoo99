package store.core.manager;

import java.util.ArrayList;
import java.util.List;
import store.model.entity.Product;
import store.reader.ProductReader;
import store.reader.PromotionReader;
import store.repository.ProductPromotionRepository;
import store.repository.ProductQuantityRepository;
import store.repository.PromotionRepository;

public class ConvenienceCreator {

    private final PromotionReader promotionReader = new PromotionReader();
    private final ProductReader productReader = new ProductReader();
    private final List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        if (products.isEmpty()) {
            products.addAll(readProducts());
        }
        return this.products;
    }

    private List<Product> readProducts() {
        clearPrevStocks();
        promotionReader.readAll();
        return productReader.readAll();
    }

    private void clearPrevStocks() {
        ProductPromotionRepository.getInstance().clear();
        ProductQuantityRepository.getInstance().clear();
        PromotionRepository.getInstance().clear();
    }
}
