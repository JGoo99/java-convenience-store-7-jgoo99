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
    private final InputView input = new InputView();
    private final OutputView out = new OutputView();

    private ConvenienceRunner(Convenience convenience) {
        this.convenience = convenience;
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
        Receipt receipt = convenience.purchase(itemDtos);
        out.printReceipt(receipt);
    }
}
