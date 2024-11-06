package store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.exception.BusinessException;
import store.model.Product;
import store.model.Promotion;

class ProductTest {

    private Promotion getExpiredPromotion() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return new Promotion("탄산2+1", 2, 1, yesterday, yesterday);
    }

    private Promotion getPromotion(int buyCnt, int getCnt) {
        LocalDate now = LocalDate.now();
        return new Promotion("탄산2+1", buyCnt, getCnt, now, now);
    }

    @DisplayName("결제 금액 만큼의 상품 수량을 구매할 수 있는지 확인한다.")
    @ParameterizedTest
    @ValueSource(longs = {1L, 10L})
    void test1(long quantity) {
        // given
        Product product = new Product("콜라", 1000L, 10L, getExpiredPromotion());
        // when & then
        assertDoesNotThrow(() -> product.validateAvailablePurchase(quantity));
    }

    @DisplayName("결제된 수량만큼 재고를 차감한다.")
    @ParameterizedTest
    @CsvSource(value = {"1:9개", "10:재고없음"}, delimiter = ':')
    void test2(long paymentQuantity, String quantityStatus) {
        // given
        Product product = new Product("콜라", 1000L, 10L, getExpiredPromotion());
        // when
        product.buy(paymentQuantity);
        // then
        assertThat(product.toString())
                .contains(quantityStatus);
    }

    @DisplayName("결제된 수량이 재고를 초과하는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {11, 15})
    void test3(long quantity) {
        // given
        Product product = new Product("콜라", 1000L, 10L, getExpiredPromotion());
        // when & then
        assertThatThrownBy(() -> product.validateAvailablePurchase(quantity))
                .isInstanceOf(BusinessException.class);
    }

    @DisplayName("결제된 수량이 0 이하인 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {0, -3})
    void test4(long quantity) {
        // given
        Product product = new Product("콜라", 1000L, 10L, getExpiredPromotion());
        // when & then
        assertThatThrownBy(() -> product.buy(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("만약 재고가 0개라면 재고 없음을 출력한다.")
    @Test
    void test5() {
        // given
        Product product = new Product("탄산수", 1200L, 0L, getExpiredPromotion());
        // when & then
        assertThat(product.toString())
                .isEqualTo("탄산수 1,200원 재고없음 탄산2+1");
    }

    @DisplayName("오늘 날짜가 프로모션 기간을 비교하여 할인 가능 여부를 반환한다. (단, 프로모션 적용 가능 개수 이상일 경우)")
    @Test
    void test6() {
        // given
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(1);
        Promotion promotion = new Promotion("탄산2+1", 2, 1, start, end);

        Product product = new Product("콜라", 1000L, 10L, promotion);
        // when & then
        assertTrue(product.isAvailablePromotion(3L));
    }

    @DisplayName("특정 수량을 구매하면 프로모션 할인이 적용된 지불 금액을 반환한다. (2+1)")
    @ParameterizedTest
    @CsvSource(value = {"1:1000", "2:2000", "3:2000", "4:3000", "5:4000", "6:4000", "7:5000", "8:6000", "9:6000", "10:7000"}, delimiter = ':')
    void test7(long quantity, long payment) {
        // given
        Product product = new Product("콜라", 1000L, 10L, getPromotion(2, 1));
        // when & then
        assertThat(product.buy(quantity))
                .isEqualTo(payment);
    }

    @DisplayName("특정 수량을 구매하면 프로모션 할인이 적용된 지불 금액을 반환한다. (1+1)")
    @ParameterizedTest
    @CsvSource(value = {"1:1000", "2:1000", "3:2000", "4:2000", "5:3000", "6:3000", "7:4000", "8:4000", "9:5000", "10:5000"}, delimiter = ':')
    void test8(long quantity, long payment) {
        // given
        Product product = new Product("콜라", 1000L, 10L, getPromotion(1, 1));
        // when & then
        assertThat(product.buy(quantity))
                .isEqualTo(payment);
    }
}