package store.reader;

import store.model.entity.Promotion;
import store.reader.parser.PromotionParser;
import store.repository.PromotionRepository;

public class PromotionReader extends MdFileLineReader<Promotion> {

    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";
    private final PromotionRepository repository = PromotionRepository.getInstance();

    @Override
    protected String getFilePath() {
        return PROMOTION_FILE_PATH;
    }

    public Promotion read(String line) {
        Promotion promotion = new PromotionParser(line).parse();
        return repository.save(promotion);
    }
}
