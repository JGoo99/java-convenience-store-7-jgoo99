package store.core;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import store.model.Item;
import store.model.Product;
import store.model.PromotionProduct;
import store.model.PromotionPurchaseStatus;
import store.model.PurchasedItem;
import store.model.Receipt;
import store.repository.ProductQuantityRepository;
import store.view.InputView;

public class PosMachine {

    private final Receipt receipt;
    private final InputView inputView;
    private final PriorityQueue<Product> productQ;

    public PosMachine() {
        this.receipt = new Receipt();
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
            processPromotionScan(item);
        }
        processDefaultScan(item);
        productQ.clear();
    }

    private void processDefaultScan(Item item) {
        if (productQ.isEmpty() || item.getQuantity() == 0) {
            return;
        }
        Product product = productQ.poll();
        receipt.addUnPromotionAmount(product.calcPayment(item.getQuantity()));
        purchase(item, item.getQuantity(), product);
    }

    private void processPromotionScan(Item item) {
        PromotionProduct product = (PromotionProduct) productQ.poll();
        if (isPromotionDisabled(item, product)) {
            return;
        }

        PromotionPurchaseStatus status = product.getPurchaseStatus(item.getQuantity());
        handlePurchaseProcess(item, status, product);
    }

    private void handlePurchaseProcess(Item item, PromotionPurchaseStatus status, PromotionProduct product) {
        if (status.isOverQ()) {
            if (!confirmUnPromotionPurchase(item, status)) {
                item.subtractUnPromotionQuantity(status.unAppliedQ());
                purchase(item, status.appliedQ(), product);
                addFreeItemToReceipt(item, status.freeQ());
                return;
            }
            receipt.addUnPromotionAmount(product.calcPayment(product.calcUnAppliedRemain(status.appliedQ())));
            purchaseAllPromotion(item, status.buyQ(), product);
            addFreeItemToReceipt(item, status.freeQ());
            return;
        }

        if (isAdditionalPromotionItemNeeded(item, status, product)) {
            item.addOneMoreQuantity();
            purchase(item, status.buyQ() + 1, product);
            addFreeItemToReceipt(item, status.freeQ() + 1);
            return;
        }

        purchase(item, status.buyQ(), product);
        addFreeItemToReceipt(item, status.freeQ());
    }

    private boolean isPromotionDisabled(Item item, PromotionProduct product) {
        if (product.isSoldOut()) {
            return true;
        }
        if (product.expiredPromotion()) {
            purchase(item, item.getQuantity(), product);
            return true;
        }
        return false;
    }

    private boolean confirmUnPromotionPurchase(Item item, PromotionPurchaseStatus status) {
        return inputView.checkUnAppliedPromotionPurchase(item, status.unAppliedQ());
    }

    private boolean isAdditionalPromotionItemNeeded(Item item, PromotionPurchaseStatus status, PromotionProduct product) {
        if (product.needMoreQuantity(status.unAppliedQ(), status.buyQ())) {
            return inputView.checkMoreQuantityPurchase(item);
        }
        return false;
    }

    public void purchase(Item item, long buyQ, Product product) {
        product.purchase(buyQ);
        pay(item, buyQ, product);
    }

    public void purchaseAllPromotion(Item item, long buyQ, PromotionProduct product) {
        product.clear();
        pay(item, buyQ, product);
    }

    private void pay(Item item, long buyQ, Product product) {
        item.pay(buyQ);
        ProductQuantityRepository.getInstance().update(product, buyQ);
        receipt.addPurchasedItem(
                PurchasedItem.create(item.getName(), buyQ, product.getPrice()));
    }

    private void addFreeItemToReceipt(Item item, long freeQ) {
        receipt.addFreeItem(
                PurchasedItem.createFree(item.getName(), freeQ));
    }

    private boolean existPromotion() {
        return productQ.size() >= 2;
    }

    public void membership() {
        boolean membership = inputView.checkMembership();
    }

    public Receipt getReceipt() {
        return receipt;
    }
}
