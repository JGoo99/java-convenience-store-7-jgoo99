package store.core.utils;

import static store.constants.ReceiptElement.ITEM_NAME;
import static store.constants.ReceiptElement.ITEM_PAYMENT;
import static store.constants.ReceiptElement.ITEM_QUANTITY;
import static store.constants.ReceiptFormat.AMOUNT_LINE;
import static store.constants.ReceiptFormat.FREE_ITEM_LINE;
import static store.constants.ReceiptFormat.ITEM_FRAME_LINE;
import static store.constants.ReceiptFormat.ITEM_LINE;

import java.text.DecimalFormat;
import store.constants.ReceiptElement;

public class ReceiptFormater {

    private static final DecimalFormat AMOUNT_FORMAT = new DecimalFormat("###,###");
    private static final DecimalFormat NEGATIVE_AMOUNT_FORMAT = new DecimalFormat("-###,###");

    public static String buildItemListFrameLine() {
        return String.format(ITEM_FRAME_LINE.toString(),
                ITEM_NAME, ITEM_QUANTITY, ITEM_PAYMENT);
    }

    public static String buildItemLine(String name, int quantity, long amount) {
        return String.format(ITEM_LINE.toString(),
                name, quantity, AMOUNT_FORMAT.format(amount));
    }

    public static String buildFreeItemLine(String name, int quantity) {
        return String.format(FREE_ITEM_LINE.toString(),
                name, quantity);
    }

    public static String buildAmountLine(ReceiptElement title, long amount) {
        return String.format(AMOUNT_LINE.toString(),
                title.toString(), AMOUNT_FORMAT.format(amount));
    }

    public static String buildDiscountedLine(ReceiptElement title, long amount) {
        return String.format(AMOUNT_LINE.toString(),
                title.toString(), NEGATIVE_AMOUNT_FORMAT.format(amount));
    }
}
