package store.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.exception.BusinessException;
import store.repository.ProductQuantityRepository;

class ItemTest {

    @DisplayName("[상품명-수량] 문자열로 입력되면 이름과 수량 정보를 추출하여 객체를 생성한다.")
    @Test
    void test1() {
        // given
        ProductQuantityRepository.getInstance().save("콜라", 10L);
        // when
        String itemInput = "[콜라-3]";
        // then
        assertThat(Item.from(itemInput).toString())
                .isEqualTo("콜라");
    }

    @DisplayName("재고 중에 상품명이 일치하는 것이 없는 경우 예외가 발생한다.")
    @Test
    void test2() {
        // given
        String itemInput = "[콜라-3]";
        // when & then
        assertThatThrownBy(() -> Item.from(itemInput))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
    }

    @DisplayName("형식에 맞지 않는 입력 시 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"콜라", "콜라-3", "[콜라+3]", "[콜라-3"})
    void test3(String itemInput) {
        // given
        ProductQuantityRepository.getInstance().save("콜라", 10L);
        // when & then
        assertThatThrownBy(() -> Item.from(itemInput))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
    }
}