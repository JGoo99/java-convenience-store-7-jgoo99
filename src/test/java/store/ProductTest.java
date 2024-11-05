package store;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProductTest {

    @DisplayName("결제 금액 만큼의 각 상품의 재고 수량이 있는지 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1:true", "10:true", "12:false"}, delimiter = ':')
    void test1(long quantity, boolean isAvailable) {
        // given
        Product product = new Product("콜라", 1000L, 10L, "탄산2+1");
        // when & then
        assertThat(product.isAvailablePurchase(quantity))
                .isEqualTo(isAvailable);
    }
}