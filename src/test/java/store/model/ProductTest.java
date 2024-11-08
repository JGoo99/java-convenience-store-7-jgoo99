package store.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.exception.BusinessException;

class ProductTest {

    @DisplayName("결제 금액 만큼의 상품 수량을 구매할 수 있는지 확인한다.")
    @ParameterizedTest
    @ValueSource(longs = {1L, 10L})
    void test1(long quantity) {
        // given
        Product product = new Product("콜라", 1000L, 10L);
        // when & then
        assertDoesNotThrow(() -> product.validateAvailablePurchase(quantity));
    }

    @DisplayName("결제된 수량만큼 재고를 차감한다.")
    @ParameterizedTest
    @CsvSource(value = {"1:9개", "10:재고 없음"}, delimiter = ':')
    void test2(long paymentQuantity, String quantityStatus) {
        // given
        Product product = new Product("콜라", 1000L, 10L);
        // when
        product.buy(paymentQuantity);
        // then
        assertThat(product.toString())
                .contains(quantityStatus);
    }

    @DisplayName("만약 재고가 0개라면 재고 없음을 출력한다.")
    @Test
    void test5() {
        // given
        Product product = new Product("탄산수", 1200L, 0L);
        // when & then
        assertThat(product.toString())
                .isEqualTo("탄산수 1,200원 재고 없음");
    }
}