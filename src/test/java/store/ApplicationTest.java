package store;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ApplicationTest extends NsTest {

    @DisplayName("파일에 있는 상품 목록 출력")
    @Test
    void readMdFileAndPrintProducts() {
        assertSimpleTest(() -> {
            run("[물-1]", "N", "N");
            assertThat(output()).contains(
                "- 콜라 1,000원 10개 탄산2+1",
                "- 콜라 1,000원 10개",
                "- 사이다 1,000원 8개 탄산2+1",
                "- 사이다 1,000원 7개",
                "- 오렌지주스 1,800원 9개 MD추천상품",
                "- 오렌지주스 1,800원 재고 없음",
                "- 탄산수 1,200원 5개 탄산2+1",
                "- 탄산수 1,200원 재고 없음",
                "- 물 500원 10개",
                "- 비타민워터 1,500원 6개",
                "- 감자칩 1,500원 5개 반짝할인",
                "- 감자칩 1,500원 5개",
                "- 초코바 1,200원 5개 MD추천상품",
                "- 초코바 1,200원 5개",
                "- 에너지바 2,000원 5개",
                "- 정식도시락 6,400원 8개",
                "- 컵라면 1,700원 1개 MD추천상품",
                "- 컵라면 1,700원 10개"
            );
        });
    }

    @DisplayName("여러 개의 일반 상품 구매")
    @Test
    void purchaseDefault() {
        assertSimpleTest(() -> {
            run("[비타민워터-3],[물-2],[정식도시락-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈18,300");
        });
    }

    @DisplayName("기간에 해당하지 않는 프로모션 적용")
    @Test
    void purchaseExpiredPromotion() {
        assertNowTest(() -> {
            run("[감자칩-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈3,000");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @DisplayName("예외 테스트 : 재고 수량 초과")
    @Test
    void exceedQuantity() {
        assertSimpleTest(() -> {
            runException("[컵라면-12]", "N", "N");
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @DisplayName("멤버십 최대 한도는 8000원 이다.")
    @Test
    void exceedMembershipMaxAmount() {
        assertSimpleTest(() -> {
            runException("[정식도시락-5]", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("멤버십할인-8,000");
        });
    }

    @DisplayName("홀수 수량의 1+1 프로모션 재고와 일치하는 수량으로 구매 시 남은 1개는 정가 결제한다.")
    @Test
    void oddQuantityBuyOneGetOneWillPayFullPriceForOne() {
        assertSimpleTest(() -> {
            runException("[감자칩-5]", "Y", "N");
            assertThat(output()).contains("현재 감자칩 1개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        });
    }

    @DisplayName("홀수 수량의 1+1 프로모션 재고와 일치하는 수량으로 구매 시 남은 1개는 정가 결제한다.")
    @Test
    void lessThanPromotionBuyQuantity() {
        assertSimpleTest(() -> {
            runException("[콜라-1]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈1,000");
        });
    }

    @DisplayName("2+1 에서 2개 구매 시 증정 여부를 물어본다.")
    @Test
    void askMoreFreeGet() {
        assertSimpleTest(() -> {
            runException("[콜라-2]", "N", "N");
            assertThat(output()).contains("현재 콜라은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        });
    }

    @DisplayName("1+1 에서 1개 구매 시 증정 상품 추가 여부를 물어본다.")
    @Test
    void askOneMoreFree() {
        assertSimpleTest(() -> {
            runException("[오렌지주스-1]", "N", "N");
            assertThat(output()).contains("현재 오렌지주스은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        });
    }

    @DisplayName("프로모션과 일반 상품 섞어서 결제")
    @Test
    void defaultAndPromotion() {
        assertSimpleTest(() -> {
            runException("[콜라-3],[에너지바-5]", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈9,000");
        });
    }

    @DisplayName("프로모션 수량 초과 결제")
    @Test
    void exceededPromotionQuantity() {
        assertSimpleTest(() -> {
            runException("[콜라-13]", "Y", "Y");
            assertThat(output()).contains("현재 콜라 4개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
            assertThat(output().replaceAll("\\s", "")).contains("행사할인-3,000");
            assertThat(output().replaceAll("\\s", "")).contains("멤버십할인-1,200");
        });
    }

    @DisplayName("결제한 금액이 없으면 할인 여부를 묻지 않는다.")
    @Test
    void doNotAskWhenNotPurchased() {
        assertSimpleTest(() -> {
            runException("[컵라면-2]", "N", "N");
            assertThat(output()).doesNotContain("멤버십 할인을 받으시겠습니까? (Y/N)");
        });
    }

    @DisplayName("무료 증정을 받지 않겠다고 선택한 경우에 자의적으로 정가 지불한 금액은 멤버심 할인 대상 금액이 아니다.")
    @Test
    void doesNotTakeFreeProductIsNotTargetOfMembershipDiscount() {
        assertSimpleTest(() -> {
            runException("[콜라-5]", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("멤버십할인-0");
        });
    }

    @DisplayName("프로모션 재고가 부족하여 일부 수량을 정가로 결제해야 하는 경우, 그 사실을 안내한다.")
    @Test
    void announceFullPrice() {
        assertSimpleTest(() -> {
            runException("[컵라면-1]", "Y", "N");
            assertThat(output()).contains("현재 컵라면 1개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        });
    }

    @DisplayName("프로모션 개수 조건을 미충족한 경우는 멤버십 할인 대상이 아니다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 4, 7})
    void notTargetOfMembershipDiscountWhenLessThanPromotionBuyQuantity(int quantity) {
        assertSimpleTest(() -> {
            runException("[콜라-" + quantity + "]", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("멤버십할인-0");
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
