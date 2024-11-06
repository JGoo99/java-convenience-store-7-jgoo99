package store.reader;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProductReaderTest {

    @DisplayName("name,price,quantity,promotion 정보를 읽어 Product 객체를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"콜라,1000,10,탄산2+1:- 콜라 1,000원 10개 탄산2+1",
            "오렌지주스,1800,9,MD추천상품:- 오렌지주스 1,800원 9개 MD추천상품"}, delimiter = ':')
    void test(String line, String expected) {
        // given
        ProductReader reader = new ProductReader();
        // when & then
        assertThat(reader.read(line).toString())
                .isEqualTo(expected);
    }

    @DisplayName("Products 정보가 나열된 문자열을 읽어 Product 객체 리스트를 반환한다.")
    @Test
    void test2() {
        // given
        ProductReader reader = new ProductReader();
        // when & then
        assertThat(reader.readAll())
                .size().isEqualTo(16);
    }
}