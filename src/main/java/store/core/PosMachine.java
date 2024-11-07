package store.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import store.model.Item;
import store.model.Product;
import store.model.PromotionProduct;
import store.repository.ProductQuantityRepository;
import store.view.InputView;

public class PosMachine {

    private final List<Item> purchasedItems;
    private final List<Item> freeItems;

    private final InputView inputView;
    private final PriorityQueue<Product> productQ;

    public PosMachine() {
        this.purchasedItems = new ArrayList<>();
        this.freeItems = new ArrayList<>();
        this.inputView = new InputView();
        this.productQ = new PriorityQueue<>(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if (o1 instanceof PromotionProduct) {
                    return -1;
                }
                return 1;
            }
        });
    }

    public void scanBarcode(Item item, List<Product> targetProducts) {
        productQ.addAll(targetProducts);
        buyDefault(item);
    }

    private void buyDefault(Item item) {
        if (productQ.isEmpty() || item.getQuantity() == 0) {
            return;
        }
        buyDefault(item, productQ.poll());
    }

    public void buyDefault(Item item, Product product) {
        long buyQ = item.getQuantity();
        product.buy(buyQ);
        ProductQuantityRepository.getInstance().update(product, buyQ);

        this.purchasedItems.add(item);
    }
}
