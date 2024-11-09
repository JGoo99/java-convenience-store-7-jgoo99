package store.reader.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.entity.Product;

class ProductParserTest {

    @DisplayName("products.md 의 한 라인이 주어지면 product 을 반환한다.")
    @Test
    void test1() {
        // given
        String line = "사이다,1000,7,null";
        // when & then
        assertThat(new ProductParser(line).parse())
                .isInstanceOf(Product.class);
    }
}