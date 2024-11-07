package store.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import store.model.Item;
import store.model.Product;
import store.model.PromotionProduct;
import store.model.PromotionPurchaseStatus;
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
        if (existPromotion()) {
            buyPromotion(item);
        }
        buyDefault(item);
    }

    private void buyDefault(Item item) {
        if (productQ.isEmpty() || item.getQuantity() == 0) {
            return;
        }
        buyDefault(item, productQ.poll());
        productQ.clear();
    }

    public void buyDefault(Item item, Product product) {
        long buyQ = item.getQuantity();
        product.buy(buyQ);
        item.pay(buyQ);
        ProductQuantityRepository.getInstance().update(product, buyQ);
        this.purchasedItems.add(item);
    }

    private void buyPromotion(Item item) {
        PromotionProduct product = (PromotionProduct) productQ.poll();
        if (product.isSoldOut()) {
            return;
        }
        if (product.expiredPromotion()) {
            buyDefault(item, product);
            return;
        }

        PromotionPurchaseStatus status = product.getPurchaseStatus(item.getQuantity());
        if (status.isOverQ()) {
            boolean keepGoing = inputView.checkUnAppliedPromotionPurchase(item, status.unAppliedQ());
            if (!keepGoing) {
                item.subtractUnPromotionQuantity(status.unAppliedQ());
            }
        }
    }

    private boolean existPromotion() {
        return productQ.size() >= 2;
    }
}
