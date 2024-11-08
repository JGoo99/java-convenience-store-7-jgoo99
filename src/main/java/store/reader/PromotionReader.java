package store.reader;

import store.model.entity.Promotion;
import store.reader.parser.LineParser;
import store.repository.PromotionRepository;

public class PromotionReader extends MdFileLineReader<Promotion> {

    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";
    private final PromotionRepository repository = PromotionRepository.getInstance();

    @Override
    protected String getFilePath() {
        return PROMOTION_FILE_PATH;
    }

    public Promotion read(String line) {
        Promotion promotion = LineParser.withPromotionRegex(line).parse();
        return repository.save(promotion);
    }
}
