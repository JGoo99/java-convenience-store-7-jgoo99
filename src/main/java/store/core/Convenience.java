package store.core;

import java.util.List;
import store.core.manager.ConvenienceCreator;
import store.model.Item;
import store.model.entity.Product;

public class Convenience {

    private final PosMachine pos;
    private final List<Product> products;

    private Convenience(List<Product> products) {
        this.pos = new PosMachine();
        this.products = products;
    }

    public static Convenience stockProduct(ConvenienceCreator creator) {
        return new Convenience(creator.getProducts());
    }

    public Receipt purchase(List<Item> items) {
        scanBarcodes(items);
        pos.askMembershipDiscount();
        return pos.printReceipt();
    }

    private void scanBarcodes(List<Item> items) {
        items.forEach(item -> {
            List<Product> targetProducts = searchTargetProducts(item);
            pos.scanBarcode(item, targetProducts);
        });
    }

    private List<Product> searchTargetProducts(Item item) {
        return products.stream().filter(product -> product.isSameName(item.getName())).toList();
    }

    public String getProductsStatus() {
        StringBuilder sb = new StringBuilder();
        products.forEach(x -> sb.append("- ").append(x).append("\n"));
        return sb.toString();
    }
}
