package store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        PromotionPurchaseStatus status = product.getPurchaseStatus(10L);
        // then
        assertEquals(7L, status.buyQ());       // 총 구매 가능 수량
        assertTrue(status.isOverQ());                    // 구매 수량이 재고 수량을 넘는지
        assertEquals(4L, status.unAppliedQ()); // 할인이 적용 되지 않는 수량 (정가 구매 수량)
        assertEquals(6L, status.appliedQ());   // 할인이 적용된 수량
        assertEquals(2L, status.freeQ());       // 무료 증정 수량
    }

    @DisplayName("구매 수량을 통해 프로모션 상품 구매 시 상태를 확인할 수 있다. (1+1)")
    @Test
    void test4() {
        // given
        PromotionProduct product = new PromotionProduct("콜라", 1000L, 7L, getPromotion(1, 1));
        // when
        PromotionPurchaseStatus status = product.getPurchaseStatus(10L);
        // then
        assertEquals(7L, status.buyQ());       // 총 구매 가능 수량
        assertTrue(status.isOverQ());                    // 구매 수량이 재고 수량을 넘는지
        assertEquals(4L, status.unAppliedQ()); // 할인이 적용 되지 않는 수량 (정가 구매 수량)
        assertEquals(6L, status.appliedQ());   // 할인이 적용된 수량
        assertEquals(3L, status.freeQ());       // 무료 증정 수량
    }

}