package store.reader.parser;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.exception.BusinessException;
import store.exception.ErrorMessage;
import store.model.Item;
import store.repository.ProductQuantityRepository;
import store.repository.PromotionRepository;

class ItemParserTest {

    @BeforeEach
    public void repositoryClear() {
        ProductQuantityRepository.getInstance().clear();
        PromotionRepository.getInstance().clear();
    }

    @DisplayName("[상품명-수량] 문자열로 입력되면 이름과 수량 정보를 추출하여 객체를 생성한다.")
    @ParameterizedTest
    @CsvSource(value = {"콜라:3", "사이다:6", "초코바:5"}, delimiter = ':')
    void test1(String productName, int quantity) {
        // given
        ProductQuantityRepository.getInstance().save(productName, 10);
        String itemInput = "[" + productName + "-" + quantity + "]";
        // when & then
        Item item = new ItemParser(itemInput).parse();
        assertEquals(productName, item.getName());
        assertEquals(quantity, item.getQuantity());
    }

    @DisplayName("재고 중에 상품명이 일치하는 것이 없는 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"오렌지주스:6", "탄산수:5"}, delimiter = ':')
    void test2(String productName, int quantity) {
        // given
        ProductQuantityRepository.getInstance().save("콜라", 10);
        String itemInput = "[" + productName + "-" + quantity + "]";
        // when & then
        assertThatThrownBy(() -> new ItemParser(itemInput).parse())
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessage.NOTFOUND_PRODUCT.toString());
    }

    @DisplayName("형식에 맞지 않는 입력 시 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"콜라", "콜라-3", "[콜라+3]", "[콜라-3"})
    void test3(String itemInput) {
        // given
        ProductQuantityRepository.getInstance().save("콜라", 10);
        // when & then
        assertThatThrownBy(() -> new ItemParser(itemInput).parse())
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessage.INVALID_INPUT.toString());
    }

    @DisplayName("구매 수량이 재고 수량을 초과하는 경우 예외가 발생한다.")
    @Test
    void test4() {
        // given
        ProductQuantityRepository.getInstance().save("컵라면", 10);
        String itemInput = "[컵라면-11]";
        // when & then
        assertThatThrownBy(() -> new ItemParser(itemInput).parse())
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessage.OVER_PRODUCT_QUANTITY.toString());
    }

    @DisplayName("구매 수량이 0 이하인 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void test5(long quantity) {
        // given
        ProductQuantityRepository.getInstance().save("콜라", 10);
        String itemInput = "[콜라-" + quantity + "]";
        // when & then
        assertThatThrownBy(() -> new ItemParser(itemInput).parse())
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessage.INVALID_INPUT.toString());
    }
}