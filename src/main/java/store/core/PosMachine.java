package store.core;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import store.core.utils.BarcodeScanner;
import store.core.utils.PromotionBarcodeScanner;
import store.model.ItemDto;
import store.model.entity.Product;
import store.model.entity.PromotionProduct;
import store.view.InputView;

public class PosMachine {

    private final PriorityQueue<Product> productQueue;
    private final InputView input = new InputView();

    private Receipt receipt = new Receipt();

    public PosMachine() {
        this.productQueue = new PriorityQueue<>(
                Comparator.comparing((Product p) -> !(p instanceof PromotionProduct)));
    }

    public void scanBarcode(ItemDto itemDto, List<Product> targetProducts) {
        fetchStockStatusOfItem(targetProducts);
        if (isPromotionItem()) {
            registerPromotionToReceipt(itemDto);
        }
        registerToReceipt(itemDto);
        clearStockStatusOfItem();
    }

    private void fetchStockStatusOfItem(List<Product> targetProducts) {
        productQueue.addAll(targetProducts);
    }

    private boolean isPromotionItem() {
        return productQueue.size() >= 2 || (productQueue.peek() instanceof PromotionProduct);
    }

    private void registerPromotionToReceipt(ItemDto itemDto) {
        PromotionBarcodeScanner.read(receipt, productQueue.poll(), itemDto).scan();
    }

    private void registerToReceipt(ItemDto itemDto) {
        if (isAllPurchased(itemDto)) {
            return;
        }
        BarcodeScanner.read(receipt, productQueue.poll(), itemDto).scan();
    }

    private boolean isAllPurchased(ItemDto itemDto) {
        return itemDto.getQuantity() == 0;
    }

    private void clearStockStatusOfItem() {
        productQueue.clear();
    }

    public void askMembershipDiscount() {
        if (!receipt.isPurchased()) {
            return;
        }
        if (!input.checkMembershipDiscount()) {
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
