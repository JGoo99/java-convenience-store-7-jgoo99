package store.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.model.Product;
import store.model.Promotion;
import store.model.PromotionProduct;
import store.repository.ProductQuantityRepository;
import store.repository.PromotionRepository;

public class ProductReader {

    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";

    private final File file;

    public ProductReader() {
        this.file = new File(PRODUCTS_FILE_PATH);
    }

    public List<Product> readAll() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                products.add(read(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public Product read(String line) {
        String[] split = line.split(",");
        String name = split[0];
        long price = Long.parseLong(split[1]);
        long quantity = Long.parseLong(split[2]);

        ProductQuantityRepository.getInstance().save(name, price);
        Promotion promotion = PromotionRepository.getInstance().findByName(split[3]);

        if (promotion == null) {
            return new Product(name, price, quantity);
        }
        return new PromotionProduct(name, price, quantity, promotion);
    }
}
