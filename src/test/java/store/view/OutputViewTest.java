package store.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.entity.Promotion;
import store.model.Item;
import store.core.Receipt;

class OutputViewTest {

    @DisplayName("환영 인사를 출력한다.")
    @Test
    void test1() {
        // given
        OutputView view = new OutputView();
        // when & then
        assertThat(view.printWelcome())
                .isEqualTo("안녕하세요. W편의점입니다.");
    }

    @DisplayName("현재 재고를 출력한다.")
    @Test
    void test2() {
        // given
        OutputView view = new OutputView();
        // when & then
        assertThat(view.printProducts("- 탄산수 1,200원 5개\n- 감자칩 1,500원 5개 탄산2+1\n"))
                .isEqualTo("현재 보유하고 있는 상품입니다.\n\n" +
                        "- 탄산수 1,200원 5개\n" +
                        "- 감자칩 1,500원 5개 탄산2+1\n");
    }

    @DisplayName("구매 내역 정보를 영수증으로 출력한다.")
    @Test
    void test3() {
        // given
        OutputView view = new OutputView();
        Receipt receipt = new Receipt();
        receipt.addItem(new Item("콜라", 3, 1000L));
        receipt.addItem(new Item("에너지바", 5, 2000L));
        receipt.addItem(new Item("오렌지주스", 2, 1800L));
        receipt.addItem(new Item("물", 1, 500L));
        // when & then
        assertThat(view.printReceipt(receipt))
                .contains("==============W 편의점================")
                .contains("콜라", "3", "3,000")
                .contains("에너지바", "5", "10,000")
                .contains("오렌지주스", "2", "3,600")
                .contains("물", "1", "500");
    }

    @DisplayName("증정 내역 정보를 영수증으로 출력한다.")
    @Test
    void test4() {
        // given
        OutputView view = new OutputView();
        Receipt receipt = new Receipt();
        receipt.addFreeItem(new Item("콜라", 1, 1000L));
        receipt.addFreeItem(new Item("감자칩", 2, 1500L));
        receipt.addFreeItem(new Item("오렌지주스", 1, 1800L));
        receipt.addFreeItem(new Item("컵라면", 1, 1700L));
        // when & then
        assertThat(view.printReceipt(receipt))
                .contains("==============W 편의점================")
                .contains("콜라", "1")
                .contains("감자칩", "2")
                .contains("오렌지주스", "1")
                .contains("컵라면", "1");
    }
}