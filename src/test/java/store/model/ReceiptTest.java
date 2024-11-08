package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReceiptTest {

    @DisplayName("구매한 상품리스트를 영수증 형식에 맞추어 문자열로 반환한다.")
    @Test
    void test1() {
        // given
        Receipt receipt = new Receipt();
        receipt.addPurchasedItem(new PurchasedItem("콜라", 3L, 1000L));
        receipt.addPurchasedItem(new PurchasedItem("에너지바", 5L, 2000L));
        // when & then
        assertThat(receipt.printPurchasedItems())
                .contains("콜라", "3", "3,000")
                .contains("에너지바", "5", "10,000");
    }

    @DisplayName("같은 이름의 상품리스트는 수량과 금액을 합산하여 출력된다.")
    @Test
    void test2() {
        // given
        Receipt receipt = new Receipt();
        receipt.addPurchasedItem(new PurchasedItem("콜라", 7L, 1000L));
        receipt.addPurchasedItem(new PurchasedItem("콜라", 3L, 1000L));
        // when & then
        assertThat(receipt.printPurchasedItems())
                .contains("콜라", "10", "10,000");
    }

    @DisplayName("총 구매 개수와 금액을 출력한다.")
    @Test
    void test3() {
        // given
        Receipt receipt = new Receipt();
        receipt.addPurchasedItem(new PurchasedItem("콜라", 7L, 1000L));
        receipt.addPurchasedItem(new PurchasedItem("콜라", 3L, 1000L));
        // when & then
        System.out.println(receipt.printTotalAmount());
        assertThat(receipt.printTotalAmount())
                .containsIgnoringWhitespaces("총구매액 10 10,000");

    }

    @DisplayName("행사 할인을 출력한다.")
    @Test
    void test4() {
        // given
        Receipt receipt = new Receipt();
        receipt.addFreeItem(new PurchasedItem("오렌지주스", 1L, 1800L));
        receipt.addFreeItem(new PurchasedItem("콜라", 1L, 1000L));
        // when & then
        System.out.println(receipt.printFreeAmount());
        assertThat(receipt.printFreeAmount())
                .contains("행사할인", "-2,800");

    }
}