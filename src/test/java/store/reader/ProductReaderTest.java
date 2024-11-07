package store.reader;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.model.Promotion;
import store.repository.PromotionRepository;

class ProductReaderTest {

    @DisplayName("name,price,quantity,promotion 정보를 읽어 Product 객체를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"콜라,1000,10,null:콜라 1,000원 10개",
            "오렌지주스,1800,9,null:오렌지주스 1,800원 9개"}, delimiter = ':')
    void test1(String line, String expected) {
        // given
        ProductReader reader = new ProductReader();
        // when & then
        assertThat(reader.read(line).toString())
                .contains(expected);
    }

    @DisplayName("Products 정보가 나열된 문자열을 읽어 Product 객체 리스트를 반환한다.")
    @Test
    void test2() {
        // given
        ProductReader reader = new ProductReader();
        // when & then
        assertThat(reader.readAll())
                .size().isEqualTo(18);
    }

    @DisplayName("name,price,quantity,promotion 정보를 읽어 PromotionProduct 객체를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"콜라,1000,10,탄산2+1:콜라 1,000원 10개 탄산2+1",
            "오렌지주스,1800,9,MD추천상품:오렌지주스 1,800원 9개 MD추천상품"}, delimiter = ':')
    void test3(String line, String expected) {
        // given
        PromotionRepository.getInstance().save(new Promotion("탄산2+1", 2, 1, null, null));
        PromotionRepository.getInstance().save(new Promotion("MD추천상품", 2, 1, null, null));
        ProductReader reader = new ProductReader();
        // when & then
        assertThat(reader.read(line).toString())
                .contains(expected);
    }
}