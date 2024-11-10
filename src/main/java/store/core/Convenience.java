package store.core;

import java.util.List;
import store.core.manager.ConvenienceCreator;
import store.model.ItemDto;
import store.model.entity.Product;

public class Convenience {

    private final List<Product> products;
    private final PosMachine pos = new PosMachine();

    private Convenience(List<Product> products) {
        this.products = products;
    }

    public static Convenience stockProduct(ConvenienceCreator creator) {
        return new Convenience(creator.getProducts());
    }

    public Receipt purchase(List<ItemDto> itemDtos) {
        scanBarcodes(itemDtos);
        pos.askMembershipDiscount();
        return pos.printReceipt();
    }

    private void scanBarcodes(List<ItemDto> itemDtos) {
        itemDtos.forEach(item -> {
            List<Product> targetProducts = searchTargetProducts(item);
            pos.scanBarcode(item, targetProducts);
        });
    }

    private List<Product> searchTargetProducts(final ItemDto itemDto) {
        return products.stream().filter(product -> product.isSameName(itemDto.getName())).toList();
    }

    public String getProductsStatus() {
        StringBuilder sb = new StringBuilder();
        products.forEach(x -> sb.append("- ").append(x).append("\n"));
        return sb.toString();
    }
}
