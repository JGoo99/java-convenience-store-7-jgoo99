package store.constants;

public enum InputViewRequest {

    ITEMS("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    KEEP_GOING("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"),
    ONE_MORE_FOR_PROMOTION("은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    UN_DISCOUNTED_PURCHASE("개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
    MEMBER_SHIP_DISCOUNT("멤버십 할인을 받으시겠습니까? (Y/N)");

    private final String question;

    InputViewRequest(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return question;
    }
}
