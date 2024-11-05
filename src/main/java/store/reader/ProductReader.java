package store.reader;

import store.Product;

public class ProductReader {

    public Product read(String line) {
        String[] split = line.split(",");
        return new Product(split[0], Long.parseLong(split[1]), Long.parseLong(split[2]), split[3]);
    }
}
