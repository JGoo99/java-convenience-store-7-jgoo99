package store.core.utils;

import store.core.Receipt;
import store.model.Item;
import store.model.PurchasedItem;
import store.model.entity.Product;
import store.repository.ProductQuantityRepository;

public class BarcodeScanner {

    protected final Receipt receipt;
    private final Product product;
    protected final Item item;
    protected final ProductQuantityRepository repository;

    protected BarcodeScanner(Receipt receipt, Product product, Item item) {
        this.receipt = receipt;
        this.product = product;
        this.item = item;
        this.repository = ProductQuantityRepository.getInstance();
    }

    public static BarcodeScanner read(Receipt receipt, Product product, Item item) {
        return new BarcodeScanner(receipt, product, item);
    }

    public void scan() {
        receipt.addUnDiscountedAmount(product.calcPayment(item.getQuantity()));
        purchase(item.getQuantity());
    }

    protected void purchase(int purchaseQuantity) {
        product.purchase(purchaseQuantity);
        pay(purchaseQuantity);
    }

    protected void pay(int buyQuantity) {
        item.pay(buyQuantity);
        repository.update(item.getName(), buyQuantity);
        receipt.addPurchasedItem(
                new PurchasedItem(item.getName(), buyQuantity, product.getPrice()));
    }
}
