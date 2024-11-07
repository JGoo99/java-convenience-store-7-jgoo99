package store.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.Promotion;

class OutViewTest {

    private Promotion getExpiredPromotion() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return new Promotion("탄산2+1", 2, 1, yesterday, yesterday);
    }

    @DisplayName("환영 인사를 출력한다.")
    @Test
    void test1() {
        // given
        OutView view = new OutView();
        // when & then
        assertThat(view.printWelcome())
                .isEqualTo("안녕하세요. W편의점입니다.");
    }

    @DisplayName("현재 재고를 출력한다.")
    @Test
    void test2() {
        // given
        OutView view = new OutView();
        // when & then
        assertThat(view.printProducts("- 탄산수 1,200원 5개\n- 감자칩 1,500원 5개 탄산2+1\n"))
                .isEqualTo("현재 보유하고 있는 상품입니다.\n\n" +
                        "- 탄산수 1,200원 5개\n" +
                        "- 감자칩 1,500원 5개 탄산2+1\n");
    }
}