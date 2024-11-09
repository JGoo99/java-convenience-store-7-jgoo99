package store.core.utils;

import java.text.DecimalFormat;
import java.util.List;
import store.model.PurchasedItem;

public class ReceiptWriter {

    private static final StringBuffer BUFFER = new StringBuffer();
    private static final DecimalFormat AMOUNT_FORMAT = new DecimalFormat("###,###");
    private static final DecimalFormat NEGATIVE_AMOUNT_FORMAT = new DecimalFormat("-###,###");

    private final PaymentCalculator payment;

    public ReceiptWriter(PaymentCalculator payment) {
        this.payment = payment;
    }

    public String run(List<PurchasedItem> purchasedItems, List<PurchasedItem> freeItems) {
        writeConvenienceName();
        writePurchasedItems(purchasedItems);
        if (!freeItems.isEmpty()) {
            writeFreeItems(freeItems);
        }
        writeTotalAmount();
        writeFreeAmount();
        writeMembershipDiscountedAmount();
        writeTotalPayment();
        return print();
    }

    private String print() {
        String receipt = BUFFER.toString();
        BUFFER.setLength(0);
        return receipt;
    }

    private void writeConvenienceName() {
        BUFFER.append("==============W 편의점================\n");
    }

    private void writePurchasedItems(List<PurchasedItem> purchasedItems) {
        BUFFER.append(String.format("%-11s\t\t%-10s\t%s\n", "상품명", "수량", "금액"));
        purchasedItems.forEach(item -> BUFFER.append(item.getPurchasedStatus() + "\n"));
    }

    private void writeFreeItems(List<PurchasedItem> freeItems) {
        BUFFER.append("=============증\t\t정===============\n");
        freeItems.forEach(item -> BUFFER.append(item.getFreeStatus() + "\n"));
    }

    private void writeTotalAmount() {
        BUFFER.append("=====================================\n");
        BUFFER.append(String.format("%-11s\t\t%-10d\t%s",
                "총구매액", payment.getTotalQuantity(), AMOUNT_FORMAT.format(payment.getTotalAmount())) + "\n");
    }

    private void writeFreeAmount() {
        BUFFER.append(String.format("%-11s\t\t\t\t\t%s",
                "행사할인", NEGATIVE_AMOUNT_FORMAT.format(payment.getFreeAmount())) + "\n");
    }

    private void writeMembershipDiscountedAmount() {
        BUFFER.append(String.format("%-11s\t\t\t\t\t%s",
                "멤버십할인", NEGATIVE_AMOUNT_FORMAT.format(payment.getMembershipDiscountedAmount())) + "\n");
    }

    private void writeTotalPayment() {
        BUFFER.append(String.format("%-11s\t\t\t\t\t%s",
                "내실돈", AMOUNT_FORMAT.format(payment.calcTotalPayment()) + "\n"));
    }
}
