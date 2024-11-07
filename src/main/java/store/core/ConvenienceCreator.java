package store.core;

import java.util.ArrayList;
import java.util.List;
import store.model.Product;
import store.reader.ProductReader;
import store.reader.PromotionReader;

public class ConvenienceCreator {

    private final PromotionReader promotionReader;
    private final ProductReader productReader;
    private final List<Product> products;

    public ConvenienceCreator() {
        this.promotionReader = new PromotionReader();
        this.productReader = new ProductReader();
        this.products = new ArrayList<>();
    }

    private List<Product> readProducts() {
        promotionReader.readAll();
        return productReader.readAll();
    }

    public List<Product> getProducts() {
        if (products.isEmpty()) {
            products.addAll(readProducts());
        }
        return this.products;
    }
}
