package store.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import store.model.Promotion;
import store.repository.PromotionRepository;

public class PromotionReader {

    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";

    private final File file;

    public PromotionReader() {
        this.file = new File(PROMOTION_FILE_PATH);
    }

    public List<Promotion> readAll() {
        List<Promotion> promotions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                promotions.add(read(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PromotionRepository.getInstance().saveAll(promotions);
        return promotions;
    }

    public Promotion read(String line) {
        String[] split = line.split(",");
        return new Promotion(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]), LocalDate.parse(split[3]),
                LocalDate.parse(split[4]));
    }
}
