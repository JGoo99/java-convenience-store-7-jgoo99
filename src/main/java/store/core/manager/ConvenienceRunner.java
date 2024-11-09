package store.core.manager;

import java.util.List;
import store.core.Convenience;
import store.core.Receipt;
import store.exception.BusinessException;
import store.model.ItemDto;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceRunner {

    private final Convenience convenience;
    private final InputView input;
    private final OutputView out;

    private ConvenienceRunner(Convenience convenience) {
        this.convenience = convenience;
        this.input = new InputView();
        this.out = new OutputView();
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

    private void purchase(final List<ItemDto> itemDtos) {
        try {
            Receipt receipt = convenience.purchase(itemDtos);
            out.printReceipt(receipt);
        } catch (BusinessException e) {
            System.out.println(e.getMessage());
        }
    }
}
