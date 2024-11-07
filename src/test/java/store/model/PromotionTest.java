package store.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionTest {

    private Promotion getWithinPeriodPromotion(int buyCnt, int getCnt) {
        LocalDate now = LocalDate.now();
        return new Promotion("탄산2+1", buyCnt, getCnt, now, now);
    }

    private Promotion getExpiredPromotion(int buyCnt, int getCnt) {
        LocalDate now = LocalDate.now().minusDays(1);
        return new Promotion("탄산2+1", buyCnt, getCnt, now, now);
    }

    @DisplayName("오늘 날짜가 프로모션 기간을 비교하여 할인 가능 여부를 반환한다.")
    @Test
    void test1() {
        // given
        Promotion within = getWithinPeriodPromotion(2, 1);
        Promotion expired = getExpiredPromotion(2, 1);
        // when
        LocalDate now = LocalDate.now();
        // then
        assertTrue(within.withinPeriod(now));
        assertFalse(expired.withinPeriod(now));
    }
}