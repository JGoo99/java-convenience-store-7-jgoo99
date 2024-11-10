package store.constants;

public enum ReceiptElement {

    CONVENIENCE_NAME_FRAME("==============W 편의점================\n"),
    FREE_ITEM_FRAME("=============증\t\t정===============\n"),
    AMOUNT_FRAME("====================================\n"),

    ITEM_NAME("상품명"),
    ITEM_QUANTITY("수량"),
    ITEM_PAYMENT("금액"),
    TOTAL_AMOUNT("총구매액"),
    FREE_DISCOUNT("행사할인"),
    MEMBERSHIP_DISCOUNT("멤버십할인"),
    TOTAL_PAYMENT("내실돈")
    ;


    private final String element;

    ReceiptElement(String element) {
        this.element = element;
    }

    @Override
    public String toString() {
        return element;
    }
}
