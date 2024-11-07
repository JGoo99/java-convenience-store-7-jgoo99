package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.core.PosMachine;
import store.repository.ProductQuantityRepository;
import store.repository.PromotionRepository;

class PosMachineTest {

    @BeforeEach
    public void repositoryClear() {
        ProductQuantityRepository.getInstance().clear();
        PromotionRepository.getInstance().clear();
    }

    private Promotion getPromotion(int buyCnt, int getCnt) {
        LocalDate now = LocalDate.from(DateTimes.now());
        return new Promotion("탄산2+1", buyCnt, getCnt, now, now);
    }

    @DisplayName("구매 시 재고 수량을 업데이트 한다.")
    @Test
    void test1() {
        // given
        ProductQuantityRepository repository = ProductQuantityRepository.getInstance();
        repository.save("에너지바", 5L);

        PosMachine pos = new PosMachine();
        Item item = new Item("에너지바", 5L);
        Product product = new Product("에너지바", 2000L, 5L);
        // when
        pos.buyDefault(item, product);
        // then
        assertThat(repository.findByName(product.getName()))
                .isEqualTo(0L);
    }

}