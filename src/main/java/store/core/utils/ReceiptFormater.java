package store.core.utils;

import static store.constants.ReceiptElement.ITEM_NAME;
import static store.constants.ReceiptElement.ITEM_PAYMENT;
import static store.constants.ReceiptElement.ITEM_QUANTITY;
import static store.constants.ReceiptFormat.AMOUNT;
import static store.constants.ReceiptFormat.FREE_ITEM;
import static store.constants.ReceiptFormat.NAME_QUANTITY_PRICE_FRAME;
import static store.constants.ReceiptFormat.PURCHASED_ITEM;

import java.text.DecimalFormat;
import store.constants.ReceiptElement;

public class ReceiptFormater {

    private static final DecimalFormat AMOUNT_FORMAT = new DecimalFormat("###,###");
    private static final DecimalFormat NEGATIVE_AMOUNT_FORMAT = new DecimalFormat("-###,###");

    public static String buildPurchasedItemFrame() {
        return String.format(NAME_QUANTITY_PRICE_FRAME.toString(),
                ITEM_NAME, ITEM_QUANTITY, ITEM_PAYMENT);
    }

    public static String buildPurchasedItemFormat(String name, int quantity, long amount) {
        return String.format(PURCHASED_ITEM.toString(),
                name, quantity, AMOUNT_FORMAT.format(amount));
    }

    public static String buildFreeItemFormat(String name, int quantity) {
        return String.format(FREE_ITEM.toString(),
                name, quantity);
    }

    public static String buildAmountFormat(ReceiptElement title, long amount) {
        return String.format(AMOUNT.toString(),
                title.toString(), AMOUNT_FORMAT.format(amount));
    }

    public static String buildNegativeAmountFormat(ReceiptElement title, long amount) {
        return String.format(AMOUNT.toString(),
                title.toString(), NEGATIVE_AMOUNT_FORMAT.format(amount));
    }
}
