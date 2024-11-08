package store.reader;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.entity.Promotion;

class PromotionReaderTest {

    @DisplayName("name,buy,get,start_date,end_date 정보를 읽어 Promotion 객체를 반환한다.")
    @Test
    void test1() {
        // given
        String line = "탄산2+1,2,1,2024-01-01,2024-12-31";
        PromotionReader reader = new PromotionReader();
        // when
        Promotion promotion = reader.read(line);
        // then
        assertThat(promotion.getName())
                .isEqualTo("탄산2+1");
    }

    @DisplayName("Products 정보가 나열된 문자열을 읽어 Product 객체 리스트를 반환한다.")
    @Test
    void test2() {
        // given
        PromotionReader reader = new PromotionReader();
        // when & then
        assertThat(reader.readAll())
                .size().isEqualTo(3);
    }
}