package store.reader;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.entity.Promotion;
import store.reader.parser.LineParser;

class PromotionParserTest {

    @DisplayName("promotions.md 의 한 라인이 주어지면 Promotion 을 반환한다.")
    @Test
    void test1() {
        // given
        String line = "탄산2+1,2,1,2024-01-01,2024-12-31";
        // when & then
        assertThat(LineParser.withPromotionRegex(line).parse())
                .isInstanceOf(Promotion.class);
    }
}