package store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemTest {

    @DisplayName("[상품명-수량] 문자열로 입력되면 이름과 수량 정보를 추출하여 객체를 생성한다.")
    @Test
    void test1() {
        // given
        String itemInput = "[콜라-3]";
        // when
        Item item = Item.from(itemInput);
        // then
        assertEquals(item.getName(), "콜라");
        assertEquals(item.getQuantity(), 3L);
    }
}