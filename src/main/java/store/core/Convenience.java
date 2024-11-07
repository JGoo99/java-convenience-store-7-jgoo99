package store.core;

import java.util.List;
import store.model.Item;
import store.model.PosMachine;
import store.model.Product;

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

    public void buy(List<Item> items) {
        items.forEach(item -> {
            List<Product> targetProducts = takeProducts(item);
//            pos.scanBarcode(item, targetProducts);
        });

        /*
        1. 프로모션 할인
        2. 멤버십 할인
         */
    }

    private List<Product> takeProducts(Item item) {
        return products.stream().filter(product -> product.isSameName(item.getName())).toList();
    }

    public String getProductsStatus() {
        StringBuilder sb = new StringBuilder();
        products.forEach(x -> sb.append("- ").append(x).append("\n"));
        return sb.toString();
    }
}
