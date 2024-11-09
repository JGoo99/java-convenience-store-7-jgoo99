package store.core;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.ItemDto;
import store.model.entity.Product;
import store.model.entity.Promotion;
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
        String productName = "에너지바";
        ProductQuantityRepository repository = ProductQuantityRepository.getInstance();
        repository.save(productName, 5);

        PosMachine pos = new PosMachine();
        ItemDto itemDto = new ItemDto(productName, 5);
        Product product = new Product(productName, 2000L, 5);
        // when
        pos.scanBarcode(itemDto, List.of(product));
        // then
        assertThat(repository.findByName(productName))
                .isEqualTo(0L);
    }
}