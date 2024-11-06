package store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.model.Product;

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

    @DisplayName("결제된 수량만큼 재고를 차감한다.")
    @ParameterizedTest
    @CsvSource(value = {"1:9", "10:0"}, delimiter = ':')
    void test2(long paymentQuantity, long remainingQuantity) {
        // given
        Product product = new Product("콜라", 1000L, 10L, "탄산2+1");
        // when & then
        assertThat(product.buy(paymentQuantity))
                .isEqualTo(remainingQuantity);
    }

    @DisplayName("결제된 수량이 재고를 초과하는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {11, 15})
    void test3(long quantity) {
        // given
        Product product = new Product("콜라", 1000L, 10L, "탄산2+1");
        // when & then
        assertThatThrownBy(() -> product.buy(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("결제된 수량이 0 이하인 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {0, -3})
    void test4(long quantity) {
        // given
        Product product = new Product("콜라", 1000L, 10L, "탄산2+1");
        // when & then
        assertThatThrownBy(() -> product.buy(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("만약 재고가 0개라면 재고 없음을 출력한다.")
    @Test
    void test5() {
        // given
        Product product = new Product("탄산수", 1200L, 0L, "탄산2+1");
        // when & then
        assertThat(product.toString())
                .isEqualTo("탄산수 1,200원 재고없음 탄산2+1");
    }

}