package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.core.PosMachine;
import store.repository.ProductQuantityRepository;

class PosMachineTest {

    private Promotion getPromotion(int buyCnt, int getCnt) {
        LocalDate now = LocalDate.from(DateTimes.now());
        return new Promotion("탄산2+1", buyCnt, getCnt, now, now);
    }

    @DisplayName("")
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