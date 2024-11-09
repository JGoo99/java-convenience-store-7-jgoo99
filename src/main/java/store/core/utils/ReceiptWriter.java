package store.core.utils;

import static store.constants.ReceiptElement.AMOUNT_FRAME;
import static store.constants.ReceiptElement.CONVENIENCE_NAME_FRAME;
import static store.constants.ReceiptElement.FREE_DISCOUNT;
import static store.constants.ReceiptElement.FREE_ITEM_FRAME;
import static store.constants.ReceiptElement.MEMBERSHIP_DISCOUNT;
import static store.constants.ReceiptElement.TOTAL_AMOUNT;
import static store.constants.ReceiptElement.TOTAL_PAYMENT;

import java.util.List;
import store.model.PurchasedItem;

public class ReceiptWriter {

    private static final StringBuffer BUFFER = new StringBuffer();

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
        BUFFER.append(CONVENIENCE_NAME_FRAME);
    }

    private void writePurchasedItems(List<PurchasedItem> purchasedItems) {
        BUFFER.append(ReceiptFormater.buildPurchasedItemFrame());
        purchasedItems.forEach(item -> BUFFER.append(item.getPurchasedStatus()));
    }

    private void writeFreeItems(List<PurchasedItem> freeItems) {
        BUFFER.append(FREE_ITEM_FRAME);
        freeItems.forEach(item -> BUFFER.append(item.getFreeStatus()));
    }

    private void writeTotalAmount() {
        BUFFER.append(AMOUNT_FRAME);
        BUFFER.append(ReceiptFormater.buildPurchasedItemFormat(
                TOTAL_AMOUNT.toString(), payment.getTotalQuantity(), payment.getTotalAmount()));
    }

    private void writeFreeAmount() {
        BUFFER.append(ReceiptFormater.buildNegativeAmountFormat(
                FREE_DISCOUNT, payment.getFreeAmount()));
    }

    private void writeMembershipDiscountedAmount() {
        BUFFER.append(ReceiptFormater.buildNegativeAmountFormat(
                MEMBERSHIP_DISCOUNT, payment.getMembershipDiscountedAmount()));
    }

    private void writeTotalPayment() {
        BUFFER.append(ReceiptFormater.buildAmountFormat(
                TOTAL_PAYMENT, payment.calcTotalPayment()));
    }
}
