package store.core;

import java.util.List;
import store.exception.BusinessException;
import store.model.Item;
import store.view.InputView;
import store.view.OutView;

public class ConvenienceRunner {

    private final Convenience convenience;
    private final OutView out;
    private final InputView input;

    private ConvenienceRunner(Convenience convenience) {
        this.convenience = convenience;
        this.out = new OutView();
        this.input = new InputView();
    }

    public static ConvenienceRunner stock() {
        return new ConvenienceRunner(Convenience.stockProduct(new ConvenienceCreator()));
    }

    public void open() {
        boolean keepGoing = true;
        while (keepGoing) {
            out.printWelcome();
            out.printProducts(convenience.getProductsStatus());

            purchase(input.readItems());

            keepGoing = input.askWantToKeepGoing();
        }
    }

    private void purchase(List<Item> items) {
        try {
            Receipt receipt = convenience.purchase(items);
            out.printReceipt(receipt);
        } catch (BusinessException e) {
            System.out.println(e.getMessage());
        }
    }
}
