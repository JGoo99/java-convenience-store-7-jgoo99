package store.core;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import store.model.Item;
import store.model.entity.Product;
import store.model.entity.PromotionProduct;
import store.model.PromotionPurchaseQuantity;
import store.model.PurchasedItem;
import store.model.Receipt;
import store.repository.ProductQuantityRepository;
import store.view.InputView;

public class PosMachine {

    private final InputView inputView;
    private final PriorityQueue<Product> productQueue;

    private Receipt receipt;

    public PosMachine() {
        this.receipt = new Receipt();
        this.inputView = new InputView();
        this.productQueue = new PriorityQueue<>(
                Comparator.comparing((Product p) -> !(p instanceof PromotionProduct)));
    }

    public void scanBarcode(Item item, List<Product> targetProducts) {
        productQueue.addAll(targetProducts);
        if (existPromotion()) {
            processPromotionScan(item);
        }
        processDefaultScan(item);
        productQueue.clear();
    }

    private void processDefaultScan(Item item) {
        if (productQueue.isEmpty() || item.getQuantity() == 0) {
            return;
        }
        Product product = productQueue.poll();
        receipt.addUnPromotionAmount(product.calcPayment(item.getQuantity()));
        purchase(item, item.getQuantity(), product);
    }

    private void processPromotionScan(Item item) {
        PromotionProduct product = (PromotionProduct) productQueue.poll();
        if (isPromotionDisabled(item, product)) {
            return;
        }
        PromotionPurchaseQuantity status = product.getPurchaseStatus(item.getQuantity());
        handlePurchaseProcess(item, status, product);
    }

    private void handlePurchaseProcess(Item item, PromotionPurchaseQuantity status, PromotionProduct product) {
        if (status.isExceeded()) {
            if (!confirmUnPromotionPurchase(item, status)) {
                item.subtractUnPromotionQuantity(status.unDiscounted());
                purchase(item, status.discounted(), product);
                addFreeItemToReceipt(item, status.free(), product.getPrice());
                return;
            }
            receipt.addUnPromotionAmount(product.calcPayment(product.calcUnDiscountedQuantity(status.discounted())));
            purchaseAllPromotion(item, status.purchase(), product);
            addFreeItemToReceipt(item, status.free(), product.getPrice());
            return;
        }

        if (isAdditionalPromotionItemNeeded(item, status, product)) {
            item.addOneMoreQuantity();
            purchase(item, status.purchase() + 1, product);
            addFreeItemToReceipt(item, status.free() + 1, product.getPrice());
            return;
        }

        purchase(item, status.purchase(), product);
        addFreeItemToReceipt(item, status.free(), product.getPrice());
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

    private boolean confirmUnPromotionPurchase(Item item, PromotionPurchaseQuantity status) {
        return inputView.checkUnDiscountedPromotionPurchase(item, status.unDiscounted());
    }

    private boolean isAdditionalPromotionItemNeeded(Item item, PromotionPurchaseQuantity status, PromotionProduct product) {
        if (product.needOneMoreForPromotion(status.unDiscounted(), status.purchase())) {
            return inputView.checkOneMoreForPromotion(item);
        }
        return false;
    }

    public void purchase(Item item, long buyQ, Product product) {
        product.purchase(buyQ);
        pay(item, buyQ, product);
    }

    public void purchaseAllPromotion(Item item, long buyQ, PromotionProduct product) {
        product.purchaseAll();
        pay(item, buyQ, product);
    }

    private void pay(Item item, long buyQ, Product product) {
        item.pay(buyQ);
        ProductQuantityRepository.getInstance().update(product, buyQ);
        receipt.addPurchasedItem(
                new PurchasedItem(item.getName(), buyQ, product.getPrice()));
    }

    private void addFreeItemToReceipt(Item item, long freeQ, long productPrice) {
        receipt.addFreeItem(
                new PurchasedItem(item.getName(), freeQ, productPrice));
    }

    private boolean existPromotion() {
        return productQueue.size() >= 2;
    }

    public void aseMembershipDiscounts() {
        if (!inputView.checkMembershipDiscount()) {
            return;
        }
        receipt.membershipDiscount();
    }

    public Receipt getReceipt() {
        Receipt cur = this.receipt;
        this.receipt = new Receipt();
        return cur;
    }
}
