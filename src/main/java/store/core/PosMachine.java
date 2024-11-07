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
        productQ.clear();
    }

    private void buyDefault(Item item) {
        if (productQ.isEmpty() || item.getQuantity() == 0) {
            return;
        }
        buy(item, item.getQuantity(), productQ.poll());
    }

    private void buyPromotion(Item item) {
        PromotionProduct product = (PromotionProduct) productQ.poll();
        if (disablePromotion(item, product)) {
            return;
        }

        PromotionPurchaseStatus status = product.getPurchaseStatus(item.getQuantity());
        checkUnAppliedPurchase(item, status);
        long buyQ = checkNeedOneMore(item, status, product);

        buy(item, buyQ, product);
        initFreeItem(item, status.freeQ());
    }

    private boolean disablePromotion(Item item, PromotionProduct product) {
        if (product.isSoldOut()) {
            return true;
        }
        if (product.expiredPromotion()) {
            buy(item, item.getQuantity(), product);
            return true;
        }
        return false;
    }

    private void checkUnAppliedPurchase(Item item, PromotionPurchaseStatus status) {
        if (status.isOverQ()) {
            boolean keepGoing = inputView.checkUnAppliedPromotionPurchase(item, status.unAppliedQ());
            if (!keepGoing) {
                item.subtractUnPromotionQuantity(status.unAppliedQ());
            }
        }
    }

    private long checkNeedOneMore(Item item, PromotionPurchaseStatus status, PromotionProduct product) {
        long buyQ = status.buyQ();
        if (product.needMoreQuantity(status.unAppliedQ(), buyQ)) {
            boolean more = inputView.checkMoreQuantityPurchase(item);
            if (more) {
                item.addOneMoreQuantity();
                buyQ++;
            }
        }
        return buyQ;
    }

    public void buy(Item item, long buyQ, Product product) {
        product.buy(buyQ);
        item.pay(buyQ);
        ProductQuantityRepository.getInstance().update(product, buyQ);
        this.purchasedItems.add(new Item(item.getName(), buyQ));
    }

    private void initFreeItem(Item item, long freeQ) {
        this.freeItems.add(new Item(item.getName(), freeQ));
    }

    private boolean existPromotion() {
        return productQ.size() >= 2;
    }
}
