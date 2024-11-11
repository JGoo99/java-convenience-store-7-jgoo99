package store.core.utils;

import store.core.Receipt;
import store.model.ItemDto;
import store.model.PromotionPurchaseQuantity;
import store.model.entity.Product;
import store.model.entity.PromotionProduct;
import store.view.InputView;

public class PromotionBarcodeScanner extends BarcodeScanner {

    private final PromotionProduct promotionProduct;
    private final PromotionPurchaseQuantity quantityStatus;
    private final InputView input = new InputView();

    private PromotionBarcodeScanner(Receipt receipt, Product product, ItemDto itemDto,
                                    PromotionProduct promotionProduct, PromotionPurchaseQuantity quantityStatus) {
        super(receipt, product, itemDto);
        this.promotionProduct = promotionProduct;
        this.quantityStatus = quantityStatus;
    }

    public static PromotionBarcodeScanner read(Receipt receipt, Product product, ItemDto itemDto) {
        PromotionProduct promotionProduct = (PromotionProduct) product;
        PromotionPurchaseQuantity quantityStatus = promotionProduct.getPurchaseQuantityStatus(itemDto.getQuantity());
        return new PromotionBarcodeScanner(receipt, product, itemDto, promotionProduct, quantityStatus);
    }

    @Override
    public void scan() {
        if (promotionProduct.isSoldOut()) {
            return;
        }
        if (promotionProduct.expiredPromotion()) {
            addUnDiscountedAmount(quantityStatus.purchase());
            purchase(quantityStatus.purchase());
            return;
        }
        registerToReceipt();
    }

    private void registerToReceipt() {
        if (quantityStatus.isExceed()) {
            handleExceededQuantityPromotion();
            return;
        }
        if (canGetOneMoreForFree()) {
            handleCanGetOneMoreFreePromotion();
            return;
        }
        purchase();
    }

    private void handleExceededQuantityPromotion() {
        addFreeItemToReceipt(quantityStatus.free());
        if (payFullPriceForSomeQuantities()) {
            addUnDiscountedAmount(quantityStatus.purchase() - quantityStatus.discounted());
            purchaseAllPromotion(quantityStatus.purchase());
            return;
        }
        purchaseOnlyDiscounted();
    }

    private void purchaseOnlyDiscounted() {
        itemDto.subtractQuantity(quantityStatus.unDiscounted());
        purchase(quantityStatus.discounted());
    }

    private void handleCanGetOneMoreFreePromotion() {
        if (needOneMoreForPromotion()) {
            purchaseWithOneMoreFree();
            return;
        }
        purchase(quantityStatus.purchase());
        addFreeItemToReceipt(quantityStatus.free());
    }

    private void purchaseWithOneMoreFree() {
        itemDto.addOneMoreQuantity();
        purchase(quantityStatus.purchase() + 1);
        addFreeItemToReceipt(quantityStatus.free() + 1);
    }

    private void purchase() {
        if (quantityStatus.free() > 0) {
            addUnDiscountedAmount(quantityStatus.unDiscounted());
            addFreeItemToReceipt(quantityStatus.free());
        }
        purchase(quantityStatus.purchase());
    }

    private void addFreeItemToReceipt(int freeQuantity) {
        receipt.addFreeItem(promotionProduct.parseOf(freeQuantity));
    }

    private void purchaseAllPromotion(int purchaseQuantity) {
        promotionProduct.purchaseAll();
        pay(purchaseQuantity);
    }

    private boolean canGetOneMoreForFree() {
        return promotionProduct.availableGetOneMoreForFree(quantityStatus.unDiscounted(), quantityStatus.purchase());
    }

    private boolean payFullPriceForSomeQuantities() {
        return input.checkPayFullPriceForSomeQuantities(itemDto.getName(), quantityStatus.unDiscounted());
    }

    private boolean needOneMoreForPromotion() {
        return input.checkGetOneMoreForFree(itemDto.getName());
    }
}
