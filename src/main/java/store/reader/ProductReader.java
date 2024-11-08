package store.reader;

import store.model.entity.Product;
import store.reader.parser.LineParser;

public class ProductReader extends MdFileLineReader<Product> {

    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";

    @Override
    protected String getFilePath() {
        return PRODUCTS_FILE_PATH;
    }

    public Product read(String line) {
        return LineParser.withProductRegex(line).parse();
    }
}
