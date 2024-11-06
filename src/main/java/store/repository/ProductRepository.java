package store.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.model.Product;

public class ProductRepository {

    private static ProductRepository productRepository;

    private final Map<String, Product> PRODUCTS = new HashMap<>();

    private ProductRepository() {
    }

    public static ProductRepository getInstance() {
        if (productRepository == null) {
            productRepository = new ProductRepository();
        }
        return productRepository;
    }

    public void saveAll(List<Product> products) {
        products.forEach(this::save);
    }

    public Product save(Product product) {
        return PRODUCTS.put(product.getName(), product);
    }

    public Product findByName(String productName) {
        return PRODUCTS.get(productName);
    }
}
