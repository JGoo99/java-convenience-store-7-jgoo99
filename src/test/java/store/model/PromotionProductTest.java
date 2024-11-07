package store.model;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PromotionProductTest {

    private Promotion getPromotion(int buyCnt, int getCnt) {
        LocalDate now = LocalDate.now();
        return new Promotion("탄산2+1", buyCnt, getCnt, now, now);
    }

    @DisplayName("오늘 날짜가 프로모션 기간을 비교하여 할인 가능 여부를 반환한다. (단, 프로모션 적용 가능 개수 이상일 경우)")
    @Test
    void test1() {
//        // given
//        PromotionProduct product = new PromotionProduct("콜라", 1000L, 10L, getPromotion(2, 1));
//        // when & then
//        assertTrue(product.isAvailablePromotion(3L));
    }

    @DisplayName("특정 수량을 구매하면 정가 결제가 필요한 개수를 반환한다. (2+1)")
    @ParameterizedTest
    @CsvSource(value = {"1:0", "2:0", "3:0", "4:0", "5:0", "6:0", "7:1", "8:2", "9:3", "10:4"}, delimiter = ':')
    void test7(long quantity, long payment) {
//        // given
//        PromotionProduct product = new PromotionProduct("콜라", 1000L, 7L, getPromotion(2, 1));
//        // when & then
//        assertThat(product.buy(quantity))
//                .isEqualTo(payment);
    }

    @DisplayName("특정 수량을 구매하면 프로모션 할인이 적용된 지불 금액을 반환한다. (1+1)")
    @ParameterizedTest
    @CsvSource(value = {"1:1000", "2:1000", "3:2000", "4:2000", "5:3000", "6:3000", "7:4000", "8:4000", "9:5000",
            "10:5000"}, delimiter = ':')
    void test8(long quantity, long payment) {
//        // given
//        PromotionProduct product = new PromotionProduct("콜라", 1000L, 10L, getPromotion(1, 1));
//        // when & then
//        assertThat(product.buy(quantity))
//                .isEqualTo(payment);
    }

    @DisplayName("프로모션 적용이 가능한 상품에 대해 고객이 해당 수량만큼 가져오지 않았을 경우를 판별한다. (2 + 1)")
    @ParameterizedTest
    @CsvSource(value = {"1:false", "2:true", "3:false", "4:false", "5:true", "6:false", "7:false", "8:true", "9:false",
            "10:false"}, delimiter = ':')
    void test9(long quantity, boolean expected) {
//        // given
//        PromotionProduct product = new PromotionProduct("콜라", 1000L, 10L, getPromotion(2, 1));
//        // when & then
//        assertThat(product.needOneMorePromotionQuantity(quantity))
//                .isEqualTo(expected);
    }

}