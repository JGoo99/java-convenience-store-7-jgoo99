package store.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import store.model.Promotion;

public class PromotionReader {

    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";

    private final File file;

    public PromotionReader() {
        this.file = new File(PROMOTION_FILE_PATH);
    }

    public List<Promotion> readAll() {
        List<Promotion> products = new ArrayList<>();
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

    public Promotion read(String line) {
        String[] split = line.split(",");
        return new Promotion(split[0], Long.parseLong(split[1]), Long.parseLong(split[2]), LocalDate.parse(split[3]),
                LocalDate.parse(split[4]));
    }
}
