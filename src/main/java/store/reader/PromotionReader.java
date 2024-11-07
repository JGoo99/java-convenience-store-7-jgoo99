package store.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
            br.lines()
                    .skip(1)
                    .forEach(line -> promotions.add(read(line)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PromotionRepository.getInstance().saveAll(promotions);
        return promotions;
    }

    public Promotion read(String line) {
        return LineParser.withPromotionRegex(line).parse();
    }
}
