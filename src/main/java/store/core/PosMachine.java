package store.core;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import store.model.Item;
import store.model.entity.Product;
import store.model.entity.PromotionProduct;
import store.view.InputView;

public class PosMachine {

    private final PriorityQueue<Product> productQueue;

    private Receipt receipt;

    public PosMachine() {
        this.receipt = new Receipt();
        this.productQueue = new PriorityQueue<>(
                Comparator.comparing((Product p) -> !(p instanceof PromotionProduct)));
    }

    public void scanBarcode(Item item, List<Product> targetProducts) {
        fetchStockStatusOfItem(targetProducts);
        if (isPromotionItem()) {
            registerPromotionToReceipt(item);
        }
        registerToReceipt(item);
        clearStockStatusOfItem();
    }

    private void fetchStockStatusOfItem(List<Product> targetProducts) {
        productQueue.addAll(targetProducts);
    }

    private boolean isPromotionItem() {
        return productQueue.size() >= 2;
    }

    private void registerPromotionToReceipt(Item item) {
        PromotionBarcodeScanner.read(receipt, productQueue.poll(), item).scan();
    }

    private void registerToReceipt(Item item) {
        if (isAllPurchased(item)) {
            return;
        }
        BarcodeScanner.read(receipt, productQueue.poll(), item).scan();
    }

    private void clearStockStatusOfItem() {
        productQueue.clear();
    }

    private boolean isAllPurchased(Item item) {
        return item.getQuantity() == 0;
    }

    public void askMembershipDiscount() {
        if (!InputView.checkMembershipDiscount()) {
            return;
        }
        receipt.membershipDiscount();
    }

    public Receipt printReceipt() {
        Receipt currentReceipt = this.receipt;
        this.receipt = new Receipt();
        return currentReceipt;
    }
}
