package store.core;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import store.model.Item;
import store.model.entity.Product;
import store.model.entity.PromotionProduct;
import store.model.PromotionPurchaseQuantity;
import store.model.PurchasedItem;
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
        receipt.addUnDiscountedAmount(product.calcPayment(item.getQuantity()));
        purchase(item, item.getQuantity(), product);
    }

    private void processPromotionScan(Item item) {
        PromotionProduct product = (PromotionProduct) productQueue.poll();
        if (isPromotionDisabled(item, product)) {
            return;
        }
        PromotionPurchaseQuantity quantities = product.getPurchaseQuantityStatus(item.getQuantity());
        handlePromotionPurchase(item, quantities, product);
    }

    private void handlePromotionPurchase(Item item, PromotionPurchaseQuantity quantities, PromotionProduct product) {
        if (quantities.isExceeded()) {
            handleQuantityExceededPromotion(item, quantities, product);
            return;
        }
        handlePromotion(item, quantities, product);
    }

    private void handlePromotion(Item item, PromotionPurchaseQuantity quantities, PromotionProduct product) {
        if (isAdditionalPromotionItemNeeded(item, quantities, product)) {
            item.addOneMoreQuantity();
            purchase(item, quantities.purchase() + 1, product);
            addFreeItemToReceipt(item.getName(), quantities.free() + 1, product.getPrice());
            return;
        }

        purchase(item, quantities.purchase(), product);
        addFreeItemToReceipt(item.getName(), quantities.free(), product.getPrice());
    }

    private void handleQuantityExceededPromotion(Item item, PromotionPurchaseQuantity quantities, PromotionProduct product) {
        addFreeItemToReceipt(item.getName(), quantities.free(), product.getPrice());
        if (!continuePurchaseUndiscounted(item, quantities.unDiscounted())) {
            item.subtractUnDiscountedQuantity(quantities.unDiscounted());
            purchase(item, quantities.discounted(), product);
            return;
        }
        receipt.addUnDiscountedAmount(product.calcUnDiscountedAmount(quantities.discounted()));
        purchaseAllPromotion(item, quantities.purchase(), product);
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

    private boolean continuePurchaseUndiscounted(Item item, long unDiscountedQuantity) {
        return inputView.checkUnDiscountedPromotionPurchase(item.getName(), unDiscountedQuantity);
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
        ProductQuantityRepository.getInstance().update(item.getName(), buyQ);
        receipt.addPurchasedItem(
                new PurchasedItem(item.getName(), buyQ, product.getPrice()));
    }

    private void addFreeItemToReceipt(String productName, long freeQ, long productPrice) {
        receipt.addFreeItem(
                new PurchasedItem(productName, freeQ, productPrice));
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
