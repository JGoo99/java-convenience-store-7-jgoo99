package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.repository.ProductRepository;

class ItemTest {

    @DisplayName("[상품명-수량] 문자열로 입력되면 이름과 수량 정보를 추출하여 객체를 생성한다.")
    @Test
    void test1() {
        // given
        ProductRepository.getInstance().save(new Product("콜라", 1000L, 10L, null));
        String itemInput = "[콜라-3]";
        // when & then
        assertThat(Item.from(itemInput).toString())
                .isEqualTo("콜라");
    }
}