package store.core.utils;

import store.core.Receipt;
import store.model.ItemDto;
import store.model.entity.Product;
import store.repository.ProductQuantityRepository;

public class BarcodeScanner {

    protected final Receipt receipt;
    private final Product product;
    protected final ItemDto itemDto;

    protected BarcodeScanner(Receipt receipt, Product product, ItemDto itemDto) {
        this.receipt = receipt;
        this.product = product;
        this.itemDto = itemDto;
    }

    public static BarcodeScanner read(Receipt receipt, Product product, ItemDto itemDto) {
        return new BarcodeScanner(receipt, product, itemDto);
    }

    public void scan() {
        addUnDiscountedAmount(itemDto.getQuantity());
        purchase(itemDto.getQuantity());
    }

    protected void addUnDiscountedAmount(final int quantity) {
        receipt.addUnDiscountedAmount(product.calcPayment(quantity));
    }

    protected void purchase(final int purchaseQuantity) {
        product.purchase(purchaseQuantity);
        pay(purchaseQuantity);
    }

    protected void pay(final int purchaseQuantity) {
        itemDto.subtractQuantity(purchaseQuantity);
        ProductQuantityRepository.getInstance().update(itemDto.getName(), purchaseQuantity);
        receipt.addItem(product.parseOf(purchaseQuantity));
    }
}
