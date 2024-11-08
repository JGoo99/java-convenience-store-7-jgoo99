package store.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.model.entity.Promotion;
import store.model.entity.PromotionProduct;

class PromotionProductTest {

    private Promotion getPromotion(int buyCnt, int getCnt) {
        LocalDate now = LocalDate.from(DateTimes.now());
        return new Promotion("탄산2+1", buyCnt, getCnt, now, now);
    }

    private Promotion getExpiredPromotion(int buyCnt, int getCnt) {
        LocalDate yesterday = LocalDate.from(DateTimes.now()).minusDays(1);
        return new Promotion("탄산2+1", buyCnt, getCnt, yesterday, yesterday);
    }

    @DisplayName("오늘 날짜와 프로모션 기간을 비교하여 할인 가능 여부를 반환한다. (기간 내)")
    @Test
    void test1() {
        // given
        PromotionProduct product = new PromotionProduct("콜라", 1000L, 10L, getPromotion(2, 1));
        // when & then
        assertFalse(product.expiredPromotion());
    }

    @DisplayName("오늘 날짜와 프로모션 기간을 비교하여 할인 가능 여부를 반환한다. (기간 외)")
    @Test
    void test2() {
        // given
        PromotionProduct product = new PromotionProduct("콜라", 1000L, 10L, getExpiredPromotion(2, 1));
        // when & then
        assertTrue(product.expiredPromotion());
    }

    @DisplayName("구매 수량을 통해 프로모션 상품 구매 시 상태를 확인할 수 있다. (2+1)")
    @Test
    void test3() {
        // given
        PromotionProduct product = new PromotionProduct("콜라", 1000L, 7L, getPromotion(2, 1));
        // when
        PromotionPurchaseQuantity status = product.getPurchaseStatus(10L);
        // then
        assertEquals(7L, status.purchase());       // 총 구매 가능 수량
        assertTrue(status.isExceeded());                    // 구매 수량이 재고 수량을 넘는지
        assertEquals(4L, status.unDiscounted()); // 할인이 적용 되지 않는 수량 (정가 구매 수량)
        assertEquals(6L, status.discounted());   // 할인이 적용된 수량
        assertEquals(2L, status.free());       // 무료 증정 수량
    }

    @DisplayName("구매 수량을 통해 프로모션 상품 구매 시 상태를 확인할 수 있다. (1+1)")
    @Test
    void test4() {
        // given
        PromotionProduct product = new PromotionProduct("콜라", 1000L, 7L, getPromotion(1, 1));
        // when
        PromotionPurchaseQuantity status = product.getPurchaseStatus(10L);
        // then
        assertEquals(7L, status.purchase());       // 총 구매 가능 수량
        assertTrue(status.isExceeded());                    // 구매 수량이 재고 수량을 넘는지
        assertEquals(4L, status.unDiscounted()); // 할인이 적용 되지 않는 수량 (정가 구매 수량)
        assertEquals(6L, status.discounted());   // 할인이 적용된 수량
        assertEquals(3L, status.free());       // 무료 증정 수량
    }

    @DisplayName("프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우를 판별한다. (2+1)")
    @ParameterizedTest
    @CsvSource(value = {"2:true", "5:true", "1:false", "3:false", "4:false", "6:false", "7:false"}, delimiter = ':')
    void test5(long buyQ, boolean expected) {
        // given
        PromotionProduct product = new PromotionProduct("콜라", 1000L, 7L, getPromotion(2, 1));
        // when
        PromotionPurchaseQuantity status = product.getPurchaseStatus(buyQ);
        // then
        assertThat(product.needOneMoreForPromotion(status.unDiscounted(), buyQ))
                .isEqualTo(expected);
    }

    @DisplayName("프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우를 판별한다. (1+1)")
    @ParameterizedTest
    @CsvSource(value = {"1:true", "3:true", "5:true", "2:false", "4:false", "6:false"}, delimiter = ':')
    void test6(long buyQ, boolean expected) {
        // given
        PromotionProduct product = new PromotionProduct("콜라", 1000L, 7L, getPromotion(1, 1));
        // when
        PromotionPurchaseQuantity status = product.getPurchaseStatus(buyQ);
        // then
        assertThat(product.needOneMoreForPromotion(status.unDiscounted(), buyQ))
                .isEqualTo(expected);
    }

    @DisplayName("무료 증정 수량을 추가할 때, 재고가 없으면 추가 여부를 묻지 않도록 한다. (2+1)")
    @ParameterizedTest
    @ValueSource(longs = {7L, 8L})
    void test7(long buyQ) {
        // given
        PromotionProduct product = new PromotionProduct("콜라", 1000L, 7L, getPromotion(2, 1));
        // when
        PromotionPurchaseQuantity status = product.getPurchaseStatus(buyQ);
        // then
        assertFalse(product.needOneMoreForPromotion(status.unDiscounted(), buyQ));
    }

    @DisplayName("무료 증정 수량을 추가할 때, 재고가 없으면 추가 여부를 묻지 않도록 한다. (1+1)")
    @ParameterizedTest
    @ValueSource(longs = {7L, 8L})
    void test8(long buyQ) {
        // given
        PromotionProduct product = new PromotionProduct("콜라", 1000L, 7L, getPromotion(1, 1));
        // when
        PromotionPurchaseQuantity status = product.getPurchaseStatus(buyQ);
        // then
        assertFalse(product.needOneMoreForPromotion(status.unDiscounted(), buyQ));
    }

}