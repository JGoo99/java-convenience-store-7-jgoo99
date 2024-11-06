package store.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.model.Product;
import store.model.Promotion;
import store.repository.ProductRepository;
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
        ProductRepository.getInstance().saveAll(products);
        return products;
    }

    public Product read(String line) {
        String[] split = line.split(",");
        Promotion promotion = PromotionRepository.getInstance().findByName(split[3]);
        return new Product(split[0], Long.parseLong(split[1]), Long.parseLong(split[2]), promotion);
    }
}
