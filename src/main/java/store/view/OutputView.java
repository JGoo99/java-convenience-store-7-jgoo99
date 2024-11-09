package store.view;


import store.constants.ViewLine;
import store.core.Receipt;

public class OutputView implements Printable {

    public String printWelcome() {
        return print(ViewLine.WELCOME);
    }

    public String printProducts(String products) {
        return print(ViewLine.PRODUCT_QUANTITY_STATUS + "\n\n" + products);
    }

    public String printReceipt(Receipt receipt) {
        return print(receipt.write());
    }
}
