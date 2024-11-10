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
        if (quantityStatus.requiredFullPriceForSome()) {
            handleRequiredFullPricePromotion();
            return;
        }
        handlePromotion();
    }

    private void handleRequiredFullPricePromotion() {
        if (quantityStatus.isExceed()) {
            addFreeItemToReceipt(quantityStatus.free());
            if (payFullPriceForSomeQuantities()) {
                addUnDiscountedAmount(quantityStatus.purchase() - quantityStatus.discounted());
                purchaseAllPromotion(quantityStatus.purchase());
                return;
            }
            itemDto.subtractQuantity(quantityStatus.unDiscounted());
            purchase(quantityStatus.discounted());
            return;
        }
        if (needOneMoreForPromotion()) {
            itemDto.addOneMoreQuantity();
            purchase(quantityStatus.purchase() + 1);
            addFreeItemToReceipt(quantityStatus.free() + 1);
            return;
        }
        if (payFullPriceForSomeQuantities()) {
            addUnDiscountedAmount(quantityStatus.unDiscounted());
            purchase(quantityStatus.purchase());
            return;
        }
        itemDto.subtractQuantity(itemDto.getQuantity());
    }

    private void handlePromotion() {
        addUnDiscountedAmount(quantityStatus.unDiscounted());
        purchase(quantityStatus.purchase());
        addFreeItemToReceipt(quantityStatus.free());
    }

    private void addFreeItemToReceipt(int freeQuantity) {
        receipt.addFreeItem(promotionProduct.parseOf(freeQuantity));
    }

    private boolean payFullPriceForSomeQuantities() {
        return input.checkPayFullPriceForSomeQuantities(itemDto.getName(), quantityStatus.unDiscounted());
    }

    private void purchaseAllPromotion(int purchaseQuantity) {
        promotionProduct.purchaseAll();
        pay(purchaseQuantity);
    }

    private boolean needOneMoreForPromotion() {
        if (promotionProduct.availableGetOneMoreForFree(quantityStatus.unDiscounted(), quantityStatus.purchase())) {
            return input.checkGetOneMoreForFree(itemDto.getName());
        }
        return false;
    }
}
