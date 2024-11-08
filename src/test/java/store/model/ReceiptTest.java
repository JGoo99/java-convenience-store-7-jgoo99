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
        System.out.println(receipt.printPurchasedItems());
        assertThat(receipt.printPurchasedItems())
                .contains("상품명", "수량", "금액")
                .contains("콜라", "3", "3,000")
                .contains("에너지바", "5", "10,000");
    }

}