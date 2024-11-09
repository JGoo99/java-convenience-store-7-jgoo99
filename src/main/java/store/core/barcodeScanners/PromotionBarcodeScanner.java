package store.core.barcodeScanners;

import store.core.Receipt;
import store.model.Item;
import store.model.PromotionPurchaseQuantity;
import store.model.PurchasedItem;
import store.model.entity.Product;
import store.model.entity.PromotionProduct;
import store.view.InputView;

public class PromotionBarcodeScanner extends BarcodeScanner {

    private final PromotionProduct promotionProduct;
    private final PromotionPurchaseQuantity quantityStatus;

    public PromotionBarcodeScanner(Receipt receipt, Product promotionProduct, Item item) {
        super(receipt, promotionProduct, item);
        this.promotionProduct = (PromotionProduct) promotionProduct;
        this.quantityStatus = this.promotionProduct.getPurchaseQuantityStatus(item.getQuantity());
    }

    @Override
    public void scan() {
        if (promotionProduct.isSoldOut()) {
            return;
        }
        if (promotionProduct.expiredPromotion()) {
            super.scan();
            return;
        }
        registerToReceipt();
    }

    private void registerToReceipt() {
        if (quantityStatus.isExceeded()) {
            handleQuantityExceededPromotion();
            return;
        }
        handlePromotion();
    }

    private void handleQuantityExceededPromotion() {
        addFreeItemToReceipt(quantityStatus.free());
        if (!continuePurchaseUnDiscounted()) {
            item.subtractUnDiscountedQuantity(quantityStatus.unDiscounted());
            purchase(quantityStatus.discounted());
            return;
        }
        receipt.addUnDiscountedAmount(promotionProduct.calcUnDiscountedAmount(quantityStatus.discounted()));
        purchaseAllPromotion(quantityStatus.purchase());
    }

    private void handlePromotion() {
        if (needOneMoreForPromotion()) {
            item.addOneMoreQuantity();
            purchase(quantityStatus.purchase() + 1);
            addFreeItemToReceipt(quantityStatus.free() + 1);
            return;
        }
        purchase(quantityStatus.purchase());
        addFreeItemToReceipt(quantityStatus.free());
    }

    private void addFreeItemToReceipt(long freeQuantity) {
        receipt.addFreeItem(
                new PurchasedItem(item.getName(), freeQuantity, promotionProduct.getPrice()));
    }

    private boolean continuePurchaseUnDiscounted() {
        return InputView.checkUnDiscountedPromotionPurchase(item.getName(), quantityStatus.unDiscounted());
    }

    public void purchaseAllPromotion(long buyQuantity) {
        promotionProduct.purchaseAll();
        pay(buyQuantity);
    }

    private boolean needOneMoreForPromotion() {
        if (promotionProduct.needOneMoreForPromotion(quantityStatus.unDiscounted(), quantityStatus.purchase())) {
            return InputView.checkOneMoreForPromotion(item.getName());
        }
        return false;
    }
}
