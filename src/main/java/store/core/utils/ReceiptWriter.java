package store.core.utils;

import static store.constants.ReceiptElement.AMOUNT_FRAME;
import static store.constants.ReceiptElement.CONVENIENCE_NAME_FRAME;
import static store.constants.ReceiptElement.FREE_DISCOUNT;
import static store.constants.ReceiptElement.FREE_ITEM_FRAME;
import static store.constants.ReceiptElement.MEMBERSHIP_DISCOUNT;
import static store.constants.ReceiptElement.TOTAL_AMOUNT;
import static store.constants.ReceiptElement.TOTAL_PAYMENT;

import java.util.List;
import store.model.Item;

public class ReceiptWriter {

    private static final StringBuffer BUFFER = new StringBuffer();

    private final PaymentCalculator payment;

    public ReceiptWriter(PaymentCalculator payment) {
        this.payment = payment;
    }

    public String run(List<Item> items, List<Item> freeItems) {
        writeConvenienceName();
        writeItems(items);
        writeFreeItems(freeItems);
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

    private void writeItems(List<Item> items) {
        BUFFER.append(ReceiptFormater.buildItemListFrameLine());
        items.stream()
                .filter(Item::isQuantityGreaterThanZero)
                .forEach(item -> BUFFER.append(item.parseReceiptLineOfPurchased()));
    }

    private void writeFreeItems(List<Item> freeItems) {
        BUFFER.append(FREE_ITEM_FRAME);
        freeItems.stream()
                .filter(Item::isQuantityGreaterThanZero)
                .forEach(item -> BUFFER.append(item.parseReceiptLineOfFree()));
    }

    private void writeTotalAmount() {
        BUFFER.append(AMOUNT_FRAME);
        BUFFER.append(ReceiptFormater.buildItemLine(
                TOTAL_AMOUNT.toString(), payment.getTotalQuantity(), payment.getTotalAmount()));
    }

    private void writeFreeAmount() {
        BUFFER.append(ReceiptFormater.buildDiscountedLine(
                FREE_DISCOUNT, payment.getFreeAmount()));
    }

    private void writeMembershipDiscountedAmount() {
        BUFFER.append(ReceiptFormater.buildDiscountedLine(
                MEMBERSHIP_DISCOUNT, payment.getMembershipDiscountedAmount()));
    }

    private void writeTotalPayment() {
        BUFFER.append(ReceiptFormater.buildAmountLine(
                TOTAL_PAYMENT, payment.calcTotalPayment()));
    }
}
